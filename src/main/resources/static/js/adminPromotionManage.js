$(document).ready(function() {

    getAllMovies();

    getActivities();

    var activities = [];

    function getActivities() {
        getRequest(
            '/activity/get',
            function (res) {
                activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderActivities() {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            var movieDomStr = "";
            var movieNameList = [];
            var movieIdList = [];

            if(activity.movieList.length){
                activity.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>"+movie.name+"</li>";
                    movieNameList.push(movie.name);
                    movieIdList.push(movie.id);
                });
            }else{
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            var movieNameIdList = [];
            for(var i=0; i<movieNameList.length; i++){
                movieNameIdList.push({id:movieIdList[i],name:movieNameList[i]});
            }
            activity['movieList']=movieNameIdList;

            activitiesDomStr+=
                '<div class="activity-container" data-activityInfo='
                +JSON.stringify(activity.id)+
                '>' +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.name+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>优惠券："+activity.coupon.name+"</span>" +
                "        <span class='title'>满"+activity.coupon.targetAmount+"减<span class='error-text title'>"+activity.coupon.discountAmount+"</span></span>" +
                "        <span class='gray-text'>"+activity.coupon.description+"</span>" +
                "    </div>" +
                "</div>"+
                "<div class='promotion-operations' style='text-align: right;margin: 0px 0px 20px 0px'>" +
                "    <button type='button' class='btn btn-primary modify-btn' data-backdrop='static' data-toggle='modal' data-target='#activityEditModal'><span>修改</span></button>" +
                "    <button type='button' class='btn btn-danger delete-btn' data-backdrop='static' data-toggle='modal' data-target='#PromotionOffBatchModal'><span>删除</span></button>" +
                "</div>"
        });
        $(".content-activity").append(activitiesDomStr);
    }

    var movieListForEditFrom = [];
    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                movieListForEditFrom = movieList;
                $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                $('#activity-movie-edit').append("<option value="+ -1 +" id='select-all-movie-edit-item'>所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $('#activity-movie-edit').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }


    //保持那个下拉框里面的东西不动，但貌似没什么用
    // $("#activity-form-dismiss-btn").click(function () {
    //     $('#activity-movie-edit').empty();
    //     // $('#selected-movies-edit').empty();
    //     $('#activity-movie-edit').append("<option value="+ -1 +">所有电影</option>");
    //     movieListForEditFrom.forEach(function (movie) {
    //         $('#activity-movie-edit').append("<option value="+ movie.id +">"+movie.name+"</option>");
    //     })
    // });

    //用于点击修改时，页面上放好原先的内容，还要负责把优惠活动的id放在一个比较好的位置
    //关于空列表，我和他之前写的保持一致，让后台处理空列表，并告知sj
    //现在主要是去把加上的电影id放在一个好的位置
    //因此要额外放两个id，一个优惠活动的id，一个电影的id列表

    //由于电影id动态添加，利用全部电影来clear，不是点两次删除，所以还是要从renderSelectedMovies入手
    /////////////////////////////////////////////////////////////////////////////////////////
    $(document).on('click','.modify-btn',function () {
        var activityInfoId = JSON.parse($(this).parent().prev()[0].dataset.activityinfo);

        var activityInfo={};
        activities.forEach(function (activity) {
            if(activity.id===activityInfoId){
                activityInfo=activity;
            }
        });
        $('#activity-name-edit').parent().parent().parent().attr('activity_id',activityInfo.id);
        $('#activity-name-edit').parent().parent().parent().attr('activity_coupon_id',activityInfo.coupon.id);
        $("#activity-name-edit").val(activityInfo.name);
        $("#activity-description-edit").val(activityInfo.description);
        $("#activity-start-date-edit").val(activityInfo.startTime.substr(0,10));
        $("#activity-start-date-edit").attr("startTime",activityInfo.startTime);
        $("#activity-end-date-edit").val(activityInfo.endTime.substr(0,10));
        $("#activity-end-date-edit").attr("endTime",activityInfo.endTime);
        $("#coupon-name-edit").val(activityInfo.coupon.name);
        $("#coupon-description-edit").val(activityInfo.coupon.description);
        $("#coupon-target-edit").val(activityInfo.coupon.targetAmount);
        $("#coupon-discount-edit").val(activityInfo.coupon.discountAmount);
        $('#activity-movie-edit').children()[0].setAttribute('selected',true);
        $('#selected-movies-edit').empty();

        activityInfo.movieList.forEach(function (movie) {
            $('#selected-movies-edit').append("<span class='label label-primary' movie_id='"+movie.id+"'>"+movie.name+"</span>");
        });

    });

    function validateActivityEditForm(data){
        var isValidate = true;
        if (!data.name) {
            isValidate = false;
            $("#activity-name-edit").parent().parent().addClass("has-error");
        } else if ($("#activity-name-edit").parent().parent().hasClass("has-error")) {
            $("#activity-name-edit").parent().parent().removeClass("has-error");
        }

        if(!data.startTime){
            isValidate = false;
            $("#activity-start-date-edit").parent().parent().addClass("has-error");
        } else if ($("#activity-start-date-edit").parent().parent().hasClass("has-error")) {
            $("#activity-start-date-edit").parent().parent().removeClass("has-error");
        }

        if(!data.endTime) {
            isValidate = false;
            $("#activity-end-date-edit").parent().parent().addClass("has-error");
        }else if(new Date(data.startTime.replace(/-/g,"/"))>new Date(data.endTime.replace(/-/g,"/"))){
            if(!$("#activity-end-date-edit").parent().parent().hasClass("has-error")){
                $("#activity-end-date-edit").parent().parent().addClass("has-error");
            }
            alert("结束日期不能早于开始日期");
        }else if($("#activity-end-date-edit").parent().parent().hasClass("has-error")){
            $("#activity-end-date-edit").parent().parent().removeClass("has-error");
        }

        if(!data.coupon.name){
            isValidate = false;
            $("#coupon-name-edit").parent().parent().addClass("has-error");
        } else if ($("#coupon-name-edit").parent().parent().hasClass("has-error")) {
            $("#coupon-name-edit").parent().parent().removeClass("has-error");
        }

        if(!data.coupon.description){
            isValidate = false;
            $("#coupon-description-edit").parent().parent().addClass("has-error");
        } else if ($("#coupon-description-edit").parent().parent().hasClass("has-error")) {
            $("#coupon-description-edit").parent().parent().removeClass("has-error");
        }

        if(!data.coupon.targetAmount || Number(data.coupon.targetAmount)<0){
            isValidate = false;
            $("#coupon-target-edit").parent().parent().addClass("has-error");
        } else if ($("#coupon-target-edit").parent().parent().hasClass("has-error")) {
            $("#coupon-target-edit").parent().parent().removeClass("has-error");
        }

        if(!data.coupon.discountAmount || Number(data.coupon.discountAmount)<0){
            isValidate = false;
            $("#coupon-discount-edit").parent().parent().addClass("has-error");
        } else if ($("#coupon-discount-edit").parent().parent().hasClass("has-error")) {
            $("#coupon-discount-edit").parent().parent().removeClass("has-error");
        }

        return isValidate;
    }

    function validateActivityAddForm(data){
        var isValidate = true;
        if (!data.name) {
            isValidate = false;
            $("#activity-name-input").parent().parent().addClass("has-error");
        } else if ($("#activity-name-input").parent().parent().hasClass("has-error")) {
            $("#activity-name-input").parent().parent().removeClass("has-error");
        }

        if(!data.startTime){
            isValidate = false;
            $("#activity-start-date-input").parent().parent().addClass("has-error");
        } else if ($("#activity-start-date-input").parent().parent().hasClass("has-error")) {
            $("#activity-start-date-input").parent().parent().removeClass("has-error");
        }

        if(!data.endTime) {
            isValidate = false;
            $("#activity-end-date-input").parent().parent().addClass("has-error");
        }else if(new Date(data.startTime.replace(/-/g,"/"))>new Date(data.endTime.replace(/-/g,"/"))){
            if(!$("#activity-end-date-input").parent().parent().hasClass("has-error")){
                $("#activity-end-date-input").parent().parent().addClass("has-error");
            }
            alert("结束日期不能早于开始日期");
        }else if($("#activity-end-date-input").parent().parent().hasClass("has-error")){
            $("#activity-end-date-input").parent().parent().removeClass("has-error");
        }

        if(!data.couponForm.name){
            isValidate = false;
            $("#coupon-name-input").parent().parent().addClass("has-error");
        } else if ($("#coupon-name-input").parent().parent().hasClass("has-error")) {
            $("#coupon-name-input").parent().parent().removeClass("has-error");
        }

        if(!data.couponForm.description){
            isValidate = false;
            $("#coupon-description-input").parent().parent().addClass("has-error");
        } else if ($("#coupon-description-input").parent().parent().hasClass("has-error")) {
            $("#coupon-description-input").parent().parent().removeClass("has-error");
        }

        if(!data.couponForm.targetAmount || Number(data.couponForm.targetAmount)<0){
            isValidate = false;
            $("#coupon-target-input").parent().parent().addClass("has-error");
        } else if ($("#coupon-target-input").parent().parent().hasClass("has-error")) {
            $("#coupon-target-input").parent().parent().removeClass("has-error");
        }

        if(!data.couponForm.discountAmount || Number(data.couponForm.discountAmount)<0){
            isValidate = false;
            $("#coupon-discount-input").parent().parent().addClass("has-error");
        } else if ($("#coupon-discount-input").parent().parent().hasClass("has-error")) {
            $("#coupon-discount-input").parent().parent().removeClass("has-error");
        }

        return isValidate;
    }

    /**
     * 点击确认发送修改优惠活动表单
     *
     * 关键问题还是在movieList
     */
    $("#activity-form-edit-btn").click(function () {
        var tempMovieList = [];
        var addedMovieNodes = $("#selected-movies-edit").children();

        for(var i=0; i<addedMovieNodes.length; i++){
            tempMovieList.push(addedMovieNodes[i].getAttribute('movie_id'));
        }

        var form = {
            id: Number($('#activity-name-edit').parent().parent().parent().attr('activity_id')),
            name: $("#activity-name-edit").val(),
            description: $("#activity-description-edit").val(),
            startTime: $("#activity-start-date-edit").val(),
            endTime: $("#activity-end-date-edit").val(),
            movieList: tempMovieList,
            coupon: {
                id: Number($('#activity-name-edit').parent().parent().parent().attr('activity_coupon_id')),
                name: $("#coupon-name-edit").val(),
                description: $("#coupon-description-edit").val(),
                targetAmount: Number($("#coupon-target-edit").val()),
                discountAmount: Number($("#coupon-discount-edit").val()),
                startTime: $("#activity-start-date-edit").val(),
                endTime: $("#activity-end-date-edit").val()
            }
        };

        if(form.movieList.length===0){
            // form.movieList = movieListForEditFrom;
            for(var i=0; i<movieListForEditFrom.length; i++){
                form.movieList.push(movieListForEditFrom[i].id.toString());
            }
        }
            console.log("活动修改的表单",form);

        //好了，form填充完成，现在是检验然后发送
        //但是还有个问题，修改过后，元素没显示出来，下面的onchange有问题，我暂时先不管

        if(validateActivityEditForm(form)){

            let startTime = new Date().toJSON(form.startTime);
            let endTime = new Date().toJSON(form.endTime);
            let coupon = form.coupon;

            form[startTime]=startTime;
            form[endTime]=endTime;
            
            postRequest(
                '/activity/edit',
                form,
                function (res) {
                    if (res.success) {
                        getActivities();
                        $("#activityEditModal").modal('hide');
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );

        }
    });

    $("#activity-form-btn").click(function () {
        var form = {
            name: $("#activity-name-input").val(),
            description: $("#activity-description-input").val(),
            startTime: $("#activity-start-date-input").val(),
            endTime: $("#activity-end-date-input").val(),

            //这里只传了movie的id ,去console截取到movieList:["11", "10", "15"]
            movieList: [...selectedMovieIds],
            couponForm: {
                name: $("#coupon-name-input").val(),
                description: $("#coupon-description-input").val(),
                targetAmount: $("#coupon-target-input").val(),
                discountAmount: $("#coupon-discount-input").val(),
                startTime: $("#activity-start-date-input").val(),
                endTime: $("#activity-end-date-input").val()
        }
    };

        console.log("活动添加的表单",form);
        if (validateActivityAddForm(form)) {
            postRequest(
                '/activity/publish',
                form,
                function (res) {
                    if (res.success) {
                        getActivities();
                        $("#activityModal").modal('hide');
                        clearActivityForm()
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

    function clearActivityForm() {
        $("#activity-name-input").val("");
        $("#activity-description-input").val("");
        $("#activity-start-date-input").val("");
        $("#activity-end-date-input").val("");
        $("#coupon-name-input").val("");
        $("#coupon-description-input").val("");
        $("#coupon-target-input").val("");
        $("#coupon-discount-input").val("");
        $("#selected-movies").empty();
        if($("#activity-movie-input").children()[0]){
            $("#activity-movie-input").children()[0].setAttribute("selected",true);
        }
    }
    
    
    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });


    function getDict(key,value) {
        var newDict = {};
        newDict[key] = value;
        return newDict;
    }

    var selectedEditMovieIdAndName = new Set();
    // var selectedEditMovieIdAndName = [];
    $('#activity-movie-edit').change(function () {
        var movieId = $('#activity-movie-edit').val();
        var movieName = $('#activity-movie-edit').children('option:selected').text();
        if(movieId==-1){
            selectedEditMovieIdAndName.clear();
        } else {
            var hasIt = false;
            selectedEditMovieIdAndName.forEach(function (item) {
                if(Object.keys(item)[0] == movieId){
                    hasIt = true;
                }
            });
            if(!hasIt){
                selectedEditMovieIdAndName.add(getDict(movieId, movieName));
            }
        }
        renderEditSelectedMovies();
    });
    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }
    function renderEditSelectedMovies() {
        $('#selected-movies-edit').empty();
        var moviesDomStr = "";
        for(var item of selectedEditMovieIdAndName){
            var key = Number(Object.keys(item)[0]);
            moviesDomStr += "<span class='label label-primary' movie_id="+key+">"+item[key]+"</span>";
        }
        $('#selected-movies-edit').append(moviesDomStr);
    }

    $(document).on("click",".delete-btn",function () {

        var r=confirm("确认要删除该优惠活动吗");

        // console.log({id:Number(JSON.parse($(this).parent().prev()[0].dataset.activityinfo))});
        if (r) {
            deleteRequest(
                '/activity/delete?id='+JSON.parse($(this).parent().prev()[0].dataset.activityinfo),
                null,
                function (res) {
                    if(res.success){
                        getActivities();
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });
});