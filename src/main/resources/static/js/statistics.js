// var chart1 =  echarts.init(document.getElementById('s1'));
// var chart2 = echarts.init(document.getElementById('s2'));

// $(document).ready(function () {
//     $.ajax({
//         async : false,
//         url : '/statistics/paperrefrank',
//         type : 'GET',
//         contentType : 'application/json',
//         success : function(res) {
//             var domstr1="";
//             var titles=res.content.titlelist;
//             var refs=res.content.reflist;
//
//             for(var i=0;i<titles.length;i++){
//                 domstr1+="<li class=\"list-group-item\">\n" +
//                     titles[i]+
//                     "                        <span class=\"badge\">"+refs[i]+"</span>\n" +
//
//                     "                    </li>"
//
//             }
//             $("#statistics1").append(domstr1);
//             },
//         error:function(error){
//             alert(error);
//         },
//     });
//
//
//     $.ajax({
//         async : false,
//         url : '/statistics/institutionrank',
//         type : 'GET',
//         contentType : 'application/json',
//         success : function(res) {
//             var domstr2="";
//             var ins=res.content.institutionList;
//             var counts=res.content.countList;
//
//             for(var i=0;i<titles.length;i++){
//                 domstr2="<li class=\"list-group-item\">\n" +
//                     "                        <span class=\"badge\">"+counts[i]+"</span>\n" +
//                     ins[i].iname+
//                     "                    </li>"
//
//             }
//             $("#statistics2").append(domstr2);
//         },
//         error:function(error){
//             alert(error);
//         },
//     });
// })


getRequest(
    '/statistics/paperrefrank',
    function (res) {
        console.log(res.content)
        // option = {
        //     backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
        //         offset: 0,
        //         color: '#64646E'
        //     }, {
        //         offset: 1,
        //         color: '#1e1e21'
        //     }]),
        //     xAxis: {
        //         type: 'category',
        //         data: res.content.titlelist,
        //         axisLabel: {
        //             show: true,
        //             textStyle: {
        //                 color: '#ffffff',
        //             }
        //         },
        //     },
        //     yAxis: {
        //         type: 'value',
        //         axisLabel: {
        //             show: true,
        //             textStyle: {
        //                 color: '#ffffff',
        //             }
        //         },
        //     },
        //     title: {
        //         text: '被引用次数最多的文章？',
        //         textStyle:{
        //             color:'#ffffff'
        //         },
        //     },
        //     series: [{
        //         data: res.content.reflist,
        //         type: 'bar',
        //         emphasis: {
        //             label: {
        //                 show: true,
        //                 formatter: function (param) {
        //                     return param.data[3];
        //                 },
        //                 position: 'top',
        //                 textStyle: {
        //                     color: '#ffffff',
        //                 }
        //             }
        //         },
        //         itemStyle: {
        //             shadowBlur: 10,
        //             shadowColor: 'rgba(120, 36, 50, 0.5)',
        //             shadowOffsetY: 5,
        //             color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
        //                 offset: 0,
        //                 color: 'rgb(255, 101, 57)'
        //             }, {
        //                 offset: 1,
        //                 color: 'rgb(240, 46, 26)'
        //             }])
        //         }
        //     }]
        // };
        // chart1.setOption(option);
        var domstr1="";
        var titles=res.content.titlelist;
        var refs=res.content.reflist;

        for(var i=0;i<titles.length;i++){
            domstr1+="<li class=\"list-group-item\">\n" +
                titles[i]+
                "                        <span class=\"badge badge-purple\">"+refs[i]+"</span>\n" +

                "                    </li>"

        }
        $("#statistics1").append(domstr1);
    },
    function (error) {
        alert(error)
    }
)

getRequest(
    '/statistics/institutionrank',
    function (res) {
        console.log(res.content)
        // option = {
        //     backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
        //         offset: 0,
        //         color: '#64646E'
        //     }, {
        //         offset: 1,
        //         color: '#1e1e21'
        //     }]),
        //     xAxis: {
        //         type: 'category',
        //         data: res.content.institutionList,
        //         axisLabel: {
        //             show: true,
        //             textStyle: {
        //                 color: '#ffffff',
        //             }
        //         },
        //     },
        //     yAxis: {
        //         type: 'value',
        //         axisLabel: {
        //             show: true,
        //             textStyle: {
        //                 color: '#ffffff',
        //             }
        //         },
        //     },
        //     title: {
        //         text: '发表论文次数最多的机构？',
        //         textStyle:{
        //             color:'#ffffff'
        //         },
        //     },
        //     series: [{
        //         data: res.content.countList,
        //         type: 'bar',
        //         emphasis: {
        //             label: {
        //                 show: true,
        //                 formatter: function (param) {
        //                     return param.data[3];
        //                 },
        //                 position: 'top',
        //                 textStyle: {
        //                     color: '#ffffff',
        //                 }
        //             }
        //         },
        //         itemStyle: {
        //             shadowBlur: 10,
        //             shadowColor: 'rgba(120, 36, 50, 0.5)',
        //             shadowOffsetY: 5,
        //             color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
        //                 offset: 0,
        //                 color: 'rgb(255, 101, 57)'
        //             }, {
        //                 offset: 1,
        //                 color: 'rgb(240, 46, 26)'
        //             }])
        //         }
        //     }]
        // };
        // chart2.setOption(option);

        var domstr2="";
        var ins=res.content.institutionList;
        var counts=res.content.countList;

        for(var i=0;i<counts.length;i++){
            domstr2+="<li class=\"list-group-item\">\n" +
                ins[i].iname+
                "                        <span class=\"badge badge-purple\">"+counts[i]+"</span>\n" +

                "                    </li>"

        }
        $("#statistics2").append(domstr2);
    },
    function (error) {
        alert(error)
    }
)

// chart1.setOption(option);
// chart2.setOption(option);
// window.addEventListener("resize", function () {
//     chart1.resize();
//     chart2.resize();
//
// });
