$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;
    var data;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                //已修改  把data变为全局变量
                data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有
    $("#modify-btn").click(function () {
       // alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
        //已修改  导入原来的值
        $("#movie-name-edit").attr("value",data.name);
        $("#movie-date-edit").attr("value",getDateyyyyMMdd(new Date(data.startDate)));//
        $("#movie-img-edit").attr("value",data.posterUrl);
        $("#movie-description-edit").text(data.description)
        $("#movie-description-edit").attr("value", data.description)
        $("#movie-type-edit").attr("value",data.type);
        $("#movie-length-edit").attr("value",data.length);
        $("#movie-country-edit").attr("value",data.country);
        $("#movie-language-edit").attr("value",data.language);
        $("#movie-director-edit").attr("value",data.director);
        $("#movie-star-edit").attr("value",data.starring);
        $("#movie-writer-edit").attr("value",data.screenWriter);
    });


    $("#delete-btn").click(function () {
        // alert('交给你们啦，下架别忘记需要一个确认提示框，也别忘记下架之后要对用户有所提示哦');
        //已修改  进行确认下架
        $("#movie-off-batch-btn").click(function () {
            postRequest(
                '/movie/off/batch',
                {movieIdList: [data.id]},
                function (res) {
                    if(res.success){
                        $("#MovieOffBatchModal").modal("hide");
                        alert("下架成功");
                    }else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        })
    });

    //已修改  确认修改的操作
    $("#movie-edit-form-btn").click(function () {
        var form = {
            id : data.id,
            name: $("#movie-name-edit").val(),
            posterUrl: $("#movie-img-edit").val(),
            director: $("#movie-director-edit").val(),
            screenWriter: $("#movie-writer-edit").val(),
            starring: $("#movie-star-edit").val(),
            type: $("#movie-type-edit").val(),
            country: $("#movie-country-edit").val(),
            language: $("#movie-language-edit").val(),
            startDate: $("#movie-date-edit").val(),
            length: $("#movie-length-edit").val(),
            description: $("#movie-description-edit").val(),
            status: data.status,
            islike: data.islike,
            likeCount: data.likeCount
        }

        if(validateMovieForm(form)){
            postRequest(
                '/movie/update',
                form,
                function (res) {
                    if(res.success){
                        getMovie();
                        $("#MovieModifyModal").modal('hide');
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

    function getDateyyyyMMdd(d){
        var curr_date = d.getDate();
        var curr_month = d.getMonth() + 1;
        var curr_year = d.getFullYear();
        String(curr_month).length < 2 ? (curr_month = "0" + curr_month): curr_month;
        String(curr_date).length < 2 ? (curr_date = "0" + curr_date): curr_date;
        var yyyyMMdd = curr_year + "-" + curr_month +"-"+ curr_date;
        return yyyyMMdd;
    }

    //已修改  表单验证
    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-edit').parent().parent().addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-edit').parent().parent().addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-edit').parent().parent().addClass('has-error');
        }
        if(!(/^[1-9]\d*$/.test(data.length))){
            isValidate = false;
            $('#movie-length-edit').parent().parent().addClass('has-error');
        }else if( $('#movie-length-edit').parent().parent().hasClass('has-error')){
            $('#movie-length-edit').parent().parent().removeClass('has-error')
        }
        return isValidate;
    }
});