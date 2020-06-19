var did;
var pname;

$(document).ready(function () {
    did = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    pname=window.location.href.split('?')[1].split('&')[1].split('=')[1];

    var attrNum=0;
    var labels=['','','','',''];

    var dataExact = [
                    [38, 3, 9, 8, 49, 27, 14, 46, 32, 4,
                     12, 6, 47, 15, 24, 39, 16, 48, 5, 6,
                     6, 43, 42, 2, 29, 37, 21, 28, 40, 17, 3,43,23,11,54,12],
                    [45, 24, 24, 29, 3, 19, 32, 45, 41, 8,
                     34, 17, 1, 45, 37, 47, 34, 30, 31, 10,
                     29, 17, 5, 23, 41, 49, 25, 34, 4, 13, 49,12,23,31,14,34],
                    [43, 23, 37, 12, 26, 11, 29, 29, 22, 27,
                     25, 5, 18, 34, 20, 3, 8, 16, 41, 19,
                     9, 5, 16, 30, 13, 44, 22, 29, 5, 23, 13,54,23,54,23,45]
                    ];
    var tickExact=[
                  [0,'05/01'],
                  [5,'05/05'],
                  [10,'05/09'],
                  [15,'05/15'],
                  [20,'05/20'],
                  [25,'05/25'],
                  [30,'05/30']
                  [35,'06/01']
                  ];
    //renderPlot();

    $("#big-title").text(pname+"--时序透视");
    $("#left-title").text(pname+"属性");

    getAttrList();
    function getAttrList() {
            getRequest(
                '/dataSource/getAttrList?did='+did,
                function (res) {
                    renderAttrList(res.content);
                },
                function (error) {
                    alert(error);
                });
        }
    function renderAttrList(list) {
            $('#attr-list').empty();
            //var dataSourceStr='';
            attrNum=list.length;
            var attrStr=''
            if(list==null){
                return false;
            }
            list.forEach(function (log) {
                attrStr += "<li class='active'><div>"+log.aname+"<input type='checkbox' name='attribute' class='check-what' value='"+log.aid+"' /></div></li>";
            });
            $('#attr-list').html(attrStr);
        }
    function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式
          var  aDate,  oDate1,  oDate2,  iDays  ;
          aDate  =  sDate1.split("-")  ;
          oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]); //转换为12-18-2006格式
          aDate  =  sDate2.split("-");
          oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);
          iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24); //把相差的毫秒数转换为天数
          return  iDays;
      }
    function addDay(datetime, days) {
         var old_time = new Date(datetime.replace(/-/g, "/")); //替换字符，js不认2013-01-31,只认2013/01/31
         var fd = new Date(old_time.valueOf() + days * 24 * 60 * 60 * 1000); //日期加上指定的天数
         var new_time = fd.getFullYear() + "-";
         var month = fd.getMonth() + 1;
         if (month >= 10) {
             new_time += month + "-";
         } else {
            //在小于10的月份上补0
            new_time += "0" + month + "-";
         }
         if (fd.getDate() >= 10) {
             new_time += fd.getDate();
         } else {
             //在小于10的日期上补0
             new_time += "0" + fd.getDate();
         }
         return new_time; //输出格式：2013-01-02
    }

    $("#time-check-btn").click(function () {
        var startTime=document.getElementById('start').value;
        var endTime=document.getElementById('end').value;
        var attrs=document.getElementsByName('attribute');
        var thisLength=attrs.length;
        var str=new Array();
        var x=0;
        for(var i=0;i<thisLength;i++){
            if(attrs[i].checked==true){
                str[x]=attrs[i].value;
                x=x+1;
            }
        }
        //alert(x);
        if(x<=0){
            alert("未选择属性");
            return false;
        }

        var form={
            dataId: did,
            attrlist: str,
            start: startTime,
            end: endTime,
        };

        postRequest(
            '/dataSource/getDatasByTime',
            form,
            function (res) {
                //alert("前面都没有问题");
                //alert(JSON.stringify(res));
                //renderAttrList(res.content);
                //alert(res.content.did);

                //构造data
                var dataList=res.content.attrDataVOList;
                var dataExample="["
                var j=0;
                dataList.forEach(function (log) {
                    //alert(JSON.stringify(log));
                    labels[j]=log.aname;
                    if(j!=0){
                        dataExample+=",";
                    }
                    var mid=log.values;
                    var i=0;
                    dataExample+="[";
                    mid.forEach(function (hit) {
                        //alert(JSON.stringify(log));
                        if(i!=0){
                            dataExample+=",";
                        }
                        dataExample+=hit.value;
                        i=i+1;
                    });
                    dataExample+=']';
                    j++;
                });
                dataExample+="]";

                //构造ticks
                var distance=endTime-startTime;
                //alert(distance);
                var dis=DateDiff(endTime,startTime);
                var dayList=new Array();
                var days=new Array();
                var d=0;
                if(dis<6){
                    for(var i=0;i<dis+1;i++){
                        dayList[i]=addDay(startTime,i);
                        days[i]=0;
                        d++;
                    }
                }
                else{
                    var inter=Math.floor(dis / 6);
                    for(var i=0;i<6;i++){
                        if(i==5){
                            dayList[i]=endTime;
                            days[i]=0;
                        }
                        else{
                            dayList[i]=addDay(startTime,inter*i);
                            days[i]=0;
                        }
                        d++;
                    }
                }
                dataList.forEach(function (log) {
                    //alert(JSON.stringify(log));
                    var mid=log.values;
                    mid.forEach(function (hit) {
                        //alert(JSON.stringify(log));
                        var tim=hit.dataTime;
                        for(var i=0;i<d;i++){
                            var tt=new Date(tim);
                            var dd=new Date(dayList[i]);
                            if(tt.getTime()<=dd.getTime()){
                                days[i]++;
                            }
                        }
                    });

                });
                tickExample="["
                for(var i=0;i<d;i++){
                    if(i!=0){
                        tickExample+=",";
                    }
                    tickExample+="["+days[i]+","+"'"+dayList[i]+"']";
                }
                tickExample+="]";
                //alert("kkk");
                //alert(dataExample);
                //alert(tickExample);
                dataExact=eval(dataExample);
                tickExact=eval(tickExample);
                renderPlot();

            },
            function (error) {
                alert(error);
            });
    });

    function renderPlot() {
        var plot = $.jqplot('chart1', dataExact, {
            title:{         // 标题属性
                text:'<div class="chart-title">时序透视图<div>',           // 标题文本
                show:true,              // 是否阴影
                fontFamily:'微软雅黑',  // 标题字体
                fontSize:14,            // 标题字体大小
                textAlign:'left',       // 标题对齐方式
                textColor:'#515151',    // 标题颜色（也可以写作属性color）
                escapeHtml:false        // 是否转义HTML字符，值为false时，可以在text属性中使用HTML代码
            },
            axesDefaults:{  // 默认坐标轴属性
                min:0,
                tickOptions:{
                    showMark:false
                }
            },
            axes:{          // 具体坐标轴属性
                xaxis:{
                    label:'日期',
                    ticks:tickExact
                },
                yaxis: {
                    label: '属性值'
                }
            },
            legend:{        // 图例属性
                show:true
            },
            grid:{          // 背景网格属性
                borderWidth:1,
                shadow:false
            },
            seriesDefaults:{// 默认数据序列属性
                lineWidth:1,
                markerOptions:{
                    show:true
                }
            },
            series:[        // 具体数据序列属性
                {
                    color:'#FF6666',
                    label:labels[0]
                },{
                    color:'#0066CC',
                    label:labels[1]
                },{
                    color:'#99CC66',
                    label:labels[2]
                },{
                    color:'#e911ec',
                    label:labels[3]
                },{
                    color: '#92610c',
                    label:labels[4]
                }
            ],
            highlighter:{
                show:true,
                tooltipAxes:'y',
                useAxesFormatters:false,
                tooltipFormatString:'投放量：%d'
            }
        });

    }





});