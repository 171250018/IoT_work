var colors = [
    '#FF6666',
    '#3399FF',
    '#FF9933',
    '#66cccc',
    '#FFCCCC',
    '#9966FF',
    'steelblue'
];

$(document).ready(function() {
    var hallId,
        scheduleDate = formatDate(new Date()),
        schedules = [],
        refundStrategies = [];

    initSelectAndDate();

    function getSchedules() {

        getRequest(
            '/schedule/search?hallId='+hallId+'&startDate='+scheduleDate.replace(/-/g,'/'),
            function (res) {
                schedules = res.content;
                renderScheduleTable(schedules);
             },
            function (error) {
                alert(JSON.stringify(error));
             }
        );
    }

    function renderScheduleTable(schedules){
        $('.schedule-date-container').empty();
        $(".schedule-time-line").siblings().remove();
        schedules.forEach(function (scheduleOfDate) {
            $('.schedule-date-container').append("<div class='schedule-date'>"+formatDate(new Date(scheduleOfDate.date))+"</div>");
            var scheduleDateDom = $(" <ul class='schedule-item-line'></ul>");
            $(".schedule-container").append(scheduleDateDom);
            scheduleOfDate.scheduleItemList.forEach(function (schedule,index) {
                var scheduleStyle = mapScheduleStyle(schedule);
                var scheduleItemDom =$(
                    "<li id='schedule-"+ schedule.id +"' class='schedule-item' data-schedule='"+JSON.stringify(schedule)+"' style='background:"+scheduleStyle.color+";top:"+scheduleStyle.top+";height:"+scheduleStyle.height+"'>"+
                    "<span>"+schedule.movieName+"</span>"+
                    "<span class='error-text'>¥"+schedule.fare+"</span>"+
                    "<span>"+formatTime(new Date(schedule.startTime))+"-"+formatTime(new Date(schedule.endTime))+"</span>"+
                    "</li>");
                scheduleDateDom.append(scheduleItemDom);
            });
        })
    }

    function mapScheduleStyle(schedule) {
        var start = new Date(schedule.startTime).getHours()+new Date(schedule.startTime).getMinutes()/60,
            end = new Date(schedule.endTime).getHours()+new Date(schedule.endTime).getMinutes()/60 ;
        return {
            color: colors[schedule.movieId%colors.length],
            top: 40*start+'px',
            height: 40*(end-start)+'px'
        }
    }

    function initSelectAndDate() {
        $('#schedule-date-input').val(scheduleDate);
        getCinemaHalls();
        getAllMovies();
        getAllRefundStrategies();
        $('#hall-select').change (function () {
            hallId=$(this).children('option:selected').val();
            getSchedules();
        });
        $('#schedule-date-input').change(function () {
            scheduleDate = $('#schedule-date-input').val();
            getSchedules();
        });
    }

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getSchedules();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    //获取所有的退票策略，并加载到下拉框里面
    function getAllRefundStrategies(){

        getRequest(
            '/refundStrategy/get',
            function (res) {
                refundStrategies = res.content;
                $("#schedule-refund-input").empty();
                $("#schedule-edit-refund-input").empty();
                refundStrategies.forEach(function (refundStrategy) {
                   $("#schedule-refund-input").append("<option value="+refundStrategy.id+">"+refundStrategy.strategyName+"</option>option>");
                   $("#schedule-edit-refund-input").append("<option value="+refundStrategy.id+">"+refundStrategy.strategyName+"</option>option>");
                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                movieList.forEach(function (movie) {
                    $('#schedule-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $("#schedule-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    //已修改 添加对于“新增排片”的判断方法
    function validateScheduleFrom(data){
        var isValidate = true;
        if(!data.startTime) {
            isValidate = false;
            $('#schedule-start-date-input').parent().parent().addClass('has-error');
        }else if($('#schedule-start-date-input').parent().parent().hasClass('has-error')){
            $('#schedule-start-date-input').parent().parent().removeClass('has-error');
        }

        if(!data.endTime) {
            isValidate = false;
            $('#schedule-end-date-input').parent().parent().addClass('has-error');
        }else if($('#schedule-end-date-input').parent().parent().hasClass('has-error')){
            $('#schedule-end-date-input').parent().parent().removeClass('has-error');
        }

        if(!data.fare || !(/\d+/.test(data.fare))) {
            isValidate = false;
            $('#schedule-price-input').parent().parent().addClass('has-error');

        }else if($('#schedule-price-input').parent().parent().hasClass('has-error')){
            $('#schedule-price-input').parent().parent().removeClass('has-error');
        }
        return isValidate;
    }

    //已修改 添加对于“修改排片”的判断方法
    function validateScheduleEditFrom(data){
        var isValidate = true;
        if(!data.startTime) {
            isValidate = false;
            $('#schedule-edit-start-date-input').parent().parent().addClass('has-error');
        }else if($('#schedule-edit-start-date-input').parent().parent().hasClass('has-error')){
            $('#schedule-edit-start-date-input').parent().parent().removeClass('has-error');
        }

        if(!data.endTime) {
            isValidate = false;
            $('#schedule-edit-end-date-input').parent().parent().addClass('has-error');
        }else if($('#schedule-edit-end-date-input').parent().parent().hasClass('has-error')){
            $('#schedule-edit-end-date-input').parent().parent().removeClass('has-error');
        }

        if(!data.fare || !(/\d+/.test(data.fare))) {
            isValidate = false;
            $('#schedule-edit-price-input').parent().parent().addClass('has-error');

        }else if($('#schedule-edit-price-input').parent().parent().hasClass('has-error')){
            $('#schedule-edit-price-input').parent().parent().removeClass('has-error');
        }
        return isValidate;
    }

    $(document).on('click','.schedule-item',function (e) {
        var schedule = JSON.parse(e.target.dataset.schedule);
        $("#schedule-edit-hall-input").children('option[value='+schedule.hallId+']').attr('selected',true);
        $("#schedule-edit-movie-input").children('option[value='+schedule.movieId+']').attr('selected',true);
        $("#schedule-edit-start-date-input").val(schedule.startTime.slice(0,16));
        $("#schedule-edit-end-date-input").val(schedule.endTime.slice(0,16));
        $("#schedule-edit-price-input").val(schedule.fare);
        $('#scheduleEditModal').modal('show');
        $('#scheduleEditModal')[0].dataset.scheduleId = schedule.id;
    });
    
    $('#schedule-form-btn').click(function () {
        var form = {
            hallId: $("#schedule-hall-input").children('option:selected').val(),
            movieId : $("#schedule-movie-input").children('option:selected').val(),
            startTime: $("#schedule-start-date-input").val(),
            endTime: $("#schedule-end-date-input").val(),
            fare: $("#schedule-price-input").val(),
            strategyId : Number($("#schedule-refund-input").children('option:selected').val())
        };
        console.log("添加排片",form);
        //todo 需要做一下表单验证？
        //已修改 加入表单验证
        if (validateScheduleFrom(form)){
            postRequest(
                '/schedule/add',
                form,
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleModal").modal('hide');
                        clearScheduleForm();
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

    function clearScheduleForm(){
        $("#schedule-start-date-input").val(""),
        $("#schedule-end-date-input").val(""),
        $("#schedule-price-input").val("")
    }

    $('#schedule-edit-form-btn').click(function () {
        var form = {
            id: Number($('#scheduleEditModal')[0].dataset.scheduleId),
            hallId: $("#schedule-edit-hall-input").children('option:selected').val(),
            movieId : $("#schedule-edit-movie-input").children('option:selected').val(),
            startTime: $("#schedule-edit-start-date-input").val(),
            endTime: $("#schedule-edit-end-date-input").val(),
            fare: $("#schedule-edit-price-input").val(),
            strategyId : Number($("#schedule-edit-refund-input").children('option:selected').val())
        };
        //todo 需要做一下表单验证？
        //已修改 加入表单验证
        console.log("修改排片",form);
        if (validateScheduleEditFrom(form)){
            postRequest(
                '/schedule/update',
                form,
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
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

    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该排片信息吗")
        if (r) {
            deleteRequest(
                '/schedule/delete/batch',
                {scheduleIdList:[Number($('#scheduleEditModal')[0].dataset.scheduleId)]},
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

});

