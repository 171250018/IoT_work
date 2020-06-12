var colorList = [
    "#3fb1e3",
    "#6be6c1",
    "#626c91",
    "#a0a7e6",
    "#c4ebad",
    "#96dee8"
];
$(document).ready(function () {
    var kid=parseInt(window.location.href.split('?')[1].split('=')[1]);
    var keywordRlGraph = echarts.init(document.getElementById("keyword-relationship-graph"));
    var nodeList=[];
    var edgeList=[];
    $.ajax({
        async: false,
        type:"GET",
        url:"/keyword/relation?kid="+kid,
        success:function (res) {
            var collaborations=res.content.collaboration;
            var relevance=res.content.relevance;
            var relCnt=relevance.length;
            var kName=res.content.keyword.kname;
            for(var i=0;i<relCnt;i++){
                var node={
                    name:collaborations[i].kname,
                    id:collaborations[i].kname,
                    symbolSize:collaborations[i].activity,
                    itemStyle: {
                        color: colorList[Math.floor(Math.random() * 6)]
                    },
                    activity:collaborations[i].activity,
                };
                var edge={
                    source:kid,
                    target:collaborations[i].kid.toString(),
                    value:relevance[i],
                    sourceName:kName,
                    targetName:collaborations[i].kname,
                };
                nodeList.push(node);
                edgeList.push(edge);
            }
            nodeList.push({
                name:kName,
                id:kName,
                itemStyle: {
                    color: colorList[Math.floor(Math.random() * 6)]
                },
                symbolSize:res.content.keyword.activity,
                activity:res.content.keyword.activity,
            })
        },
        error:function (error) {
            console.log(error);
        }
    });

    var option={
        title:{
            text:"click the thumbnail for details",
            textStyle:{
                color:'#1D3557',
                fontWeight:700,
                fontSize:13
            }
        },
        tooltip: {
            show: true,
            formatter: function (params) {
                // console.log(params);
                var showHtm="";
                if (params.dataType==='node'){
                    showHtm+="NAME:";
                    showHtm+=params.data.name;
                    showHtm+="  ";
                    showHtm+="ACTIVITY:";
                    showHtm+=params.data.activity;
                }
                else{
                    showHtm+=params.data.sourceName+">"+params.data.targetName+" ";
                    showHtm+="RELEVANCE:";
                    showHtm+=params.data.value;
                }
                return showHtm;
            }
        },
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series: [ {
            type: 'graph',
            layout:'force',
            left: 'center',
            top: 'center',
            right: null,
            bottom: null,
            width: '80%',
            height: '80%',
            drawOutOfBound: false,
            emphasis: {
                label: {
                    position: 'right',
                    show: true
                }
            },
            roam:  true,
            focusNodeAdjacency: true,
            lineStyle: {
                width: 0.5,
                color: 'target',
                curveness: 0.3,
                opacity: 0.5
            },
            nodes:nodeList,
            edges:edgeList,
        } ]

    };

    keywordRlGraph.setOption(option);
    keywordRlGraph.resize();

    $.ajax({
        async : false,
        url : '/keyword/keywordInfo?kid='+kid,
        type : 'GET',
        contentType : 'application/json',
        success : function(res) {
            if (res.success){
                sessionStorage.clear();
                // alert(JSON.stringify(res.content));
                var kname=JSON.parse(JSON.stringify(res.content.kname));
                $('#keyword-detail-act').html(res.content.activity);
                $('#keyword-detail-kname').html(kname);
            }
            else{
                alert("failed to get keyword info");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });
    $.ajax({
        async : false,
        url : '/keyword/paperList?kid='+kid,
        type : 'GET',
        contentType : 'application/json',
        success : function(res) {
            if (res.success){
                sessionStorage.clear();
                // alert(JSON.stringify(res.content));
                var paperlist=JSON.parse(JSON.stringify(res.content.paperVOList));
                var str="";
                var length=paperlist.length;
                if(paperlist.length>10){
                    length=10;
                }
                for (let i=0;i<length;i++) {
                    var title=paperlist[i].doctitle;
                    if(title.length>80){
                        title=title.substr(0,80)+"...";
                    }
                    str+="<a href=\"/paper?pid="+paperlist[i].pid+"\" class=\"text-center\">"+title+"</a>";
                    str+="<hr>";
                }
                for (let i=0;i<10-length;i++) {
                    str+="<a>&nbsp</a>";
                    str+="<hr>";
                }
                $('#keyword-detail-paper-list').html(str);
            }
            else{
                alert("failed to get keyword paperlist");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });

    $.ajax({
        async : false,
        url : '/keyword/authorList?kid='+kid,
        type : 'GET',
        contentType : 'application/json',
        success : function(res) {
            if (res.success){
                sessionStorage.clear();
                // alert(JSON.stringify(res.content));
                var authorlist=JSON.parse(JSON.stringify(res.content.authorVOList));
                var str="";
                var length=authorlist.length;
                if(authorlist.length>10){
                    length=10;
                }
                for (let i=0;i<length;i++) {
                    var name=authorlist[i].aname;
                    if(name.length>40){
                        name=name.substr(0,40)+"...";
                    }
                    str+="<a href=\"/author?aid="+authorlist[i].aid+"\" class=\"text-center\">"+name+"</a>";
                    str+="<hr>";
                }
                for (let i=0;i<10-length;i++) {
                    str+="<a>&nbsp</a>";
                    str+="<hr>";
                }
                $('#keyword-detail-author-list').html(str);
            }
            else{
                alert("failed to get keyword authorlist");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });

    $.ajax({
        async : false,
        url : '/keyword/institutionList?kid='+kid,
        type : 'GET',
        contentType : 'application/json',
        success : function(res) {
            if (res.success){
                sessionStorage.clear();
                // alert(JSON.stringify(res.content));
                var inslist=JSON.parse(JSON.stringify(res.content.institutionVOList));
                var str="";
                var length=inslist.length;
                if(inslist.length>10){
                    length=10;
                }
                for (let i=0;i<length;i++) {
                    var name=inslist[i].iname;
                    if(name.length>40){
                        name=name.substr(0,40)+"...";
                    }
                    str+="<a href=\"/institution?iid="+inslist[i].iid+"\" class=\"text-center\">"+name+"</a>";
                    str+="<hr>";
                }
                for (let i=0;i<10-length;i++) {
                    str+="<a>&nbsp</a>";
                    str+="<hr>";
                }
                $('#keyword-detail-ins-list').html(str);
            }
            else{
                alert("failed to get keyword inslist");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });


    keywordRlGraph.on('click' ,function (param) {
        window.location.href='/graph?id='+kid+'&type=kid'
    });
});

