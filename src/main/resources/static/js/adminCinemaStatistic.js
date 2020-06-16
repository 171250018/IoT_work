$(document).ready(function() {


    getScheduleRate();
    
    getBoxOffice();

    getAudiencePrice();

    //已修改  上座率统计图可以通过按钮更新
    $('#place-rate-date-btn').click(function(){
        var date=$('#place-rate-date-input').val();
        if(date){
            if($('#place-rate-date-input').parent().parent().hasClass('has-error')){
                $('#place-rate-date-input').parent().parent().removeClass('has-error')
            }
            getPlacingRate(new Date(date))
        }else{
            $('#place-rate-date-input').parent().parent().addClass('has-error');
        }

    });

    //已修改  最受欢迎电影统计图可以通过按钮更新
    $('#popular-movie-btn').click(function(){
        var days=$('#popular-movie-days-input').val();
        var movieNum=$('#popular-movie-movieNum-input').val();
        if(validatePopularMovieFrom(days,movieNum)) {
            getPolularMovie(days, movieNum)
        }
    });

    //已修改  对最受欢迎电影的输入格式进行验证
    function validatePopularMovieFrom(days,movieNum){
        var isvalidate=true;
        if(!days){
            isvalidate=false;
            $('#popular-movie-days-input').parent().addClass('has-error');
        }else{
            if($('#popular-movie-days-input').parent().hasClass('has-error')){
                $('#popular-movie-days-input').parent().removeClass('has-error')
            }
        }
        if(!movieNum){
            isvalidate=false;
            $('#popular-movie-movieNum-input').parent().addClass('has-error');
        }else{
            if($('#popular-movie-movieNum-input').parent().hasClass('has-error')){
                $('#popular-movie-movieNum-input').parent().removeClass('has-error')
            }
        }
        return isvalidate;
    }

    function getScheduleRate() {

        getRequest(
            '/statistics/scheduleRate',
            function (res) {
                var data = res.content||[];
                var tableData = data.map(function (item) {
                   return {
                       value: item.time,
                       name: item.name
                   };
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '今日排片率',
                        subtext: new Date().toLocaleDateString(),
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{b} : {c}场 ({d}%)"
                    },
                    legend: {
                        x : 'center',
                        y : 'bottom',
                        data:nameList
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            type:'pie',
                            radius : [30, 110],
                            center : ['50%', '50%'],
                            roseType : 'area',
                            data:tableData
                        }
                    ]
                };
                var scheduleRateChart = echarts.init($("#schedule-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getBoxOffice() {

        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '所有电影票房',
                        subtext: '截止至'+new Date().toLocaleDateString(),
                        x:'center'
                    },
                    xAxis: {
                        type: 'value'
                    },
                    yAxis: {
                        type: 'category',
                        data: nameList,
                        // axisLabel:{interval:0, rotate:90, margin:10}
                    },
                    series: [{
                        name: '总票房',
                        data: tableData,
                        type: 'bar',
                        barWidth:'50%'
                    }],
                    tooltip: {trigger:'axis',axisPointer:{type:'shadow'}},
                    grid:{left:'2%',right:'2%',bottom:'10%',containLabel: true}
                };
                var scheduleRateChart = echarts.init($("#box-office-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getAudiencePrice() {
        getRequest(
            '/statistics/audience/price',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.price;
                });
                var nameList = data.map(function (item) {
                    return formatDate(new Date(item.date));
                });
                var option = {
                    title : {
                        text: '每日客单价',
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: '客单价',
                        data: tableData,
                        type: 'line'
                    }],
                    tooltip: {trigger:'axis',axisPointer:{type:'shadow'}}
                };
                var scheduleRateChart = echarts.init($("#audience-price-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getPlacingRate(targetDate) {
        // todo
        //已修改 显示上座率的条形统计图
        var dateString = formatDate(targetDate);
        getRequest(
            '/statistics/PlacingRate?targetDate=' + dateString.replace(/-/g, '/'),
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.placingRate;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title: {
                        text: '上座率',
                        subtext: '时间：' + targetDate.toLocaleDateString(),
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar',
                        barWidth: '50%'
                    }],
                    tooltip: {trigger:'axis',axisPointer:{type:'shadow'}}
                };
                var scheduleRateChart = echarts.init($("#place-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getPolularMovie(days,movieNum) {
        // todo
        //已修改 显示最受欢迎电影的折线统计图
        getRequest(
            '/statistics/popular/movie?days='+days+'&movieNum='+movieNum,
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '最受欢迎电影',
                        subtext: '在近'+days+'天内的'+movieNum+"部最受欢迎的电影（按热度从高到低排序）",
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: '期间票房',
                        data: tableData,
                        type: 'bar'
                    }],
                    tooltip: {trigger:'axis',axisPointer:{type:'shadow'}}
                };
                var scheduleRateChart = echarts.init($("#popular-movie-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }
});
