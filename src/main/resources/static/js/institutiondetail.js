var colorList = [
    "#3fb1e3",
    "#6be6c1",
    "#626c91",
    "#a0a7e6",
    "#c4ebad",
    "#96dee8"
];
$(document).ready(function () {
    var url=location.href;
    var i=url.indexOf("=");
    var iid=unescape(url.substring(i+1));
    var formatIid={iid:iid};

    /**********institutionInfo************/
    $.ajax({
        type:'GET',
        url:'/institution/institutionInfo',
        data:formatIid,
        success:function (res) {
            // alert(JSON.stringify(res.content));
            var insName=res.content.iname;
            var paperNum=res.content.paperCount;
            var paperCited=res.content.papercited;

            //Author
            $('#ins-detail-iname').append(insName);
            $('#ins-detail-act').append(res.content.activity);

            $('#ins-detail-paper').append(paperNum);

            $('#ins-detail-cite').append(paperCited);
        },
        error:function (error) {
            console.log(error)
        }
    });


    /**********TopAuthors************/
    $.ajax({
        type:'GET',
        url:'/institution/authorList',
        data:formatIid,
        success:function (res) {
            var authorlist=JSON.parse(JSON.stringify(res.content.authorVOList));
            var str="";
            var length=authorlist.length;
            if(authorlist.length>10){
                length=10;
            }
            for (let i=0;i<length;i++) {
                var title=authorlist[i].aname;
                if(title.length>40){
                    title=title.substr(0,40)+"...";
                }
                str+="<a href=\"/author?aid="+authorlist[i].aid+"\" class=\"text-center\">"+title+"</a>";
                str+="<hr>";
            }
            for (let i=0;i<10-length;i++) {
                str+="<a>&nbsp</a>";
                str+="<hr>";
            }
            $('#ins-detail-author-list').html(str);
        },
        error:function (error) {
            console.log(error)
        }
    });

    /**********TopPapers************/
    $.ajax({
        type:'GET',
        url:'/institution/paperList',
        data:formatIid,
        success:function (res) {
            var paperlist=JSON.parse(JSON.stringify(res.content.paperVOList));
            var str="";
            var length=paperlist.length;
            if(paperlist.length>10){
                length=10;
            }
            for (let i=0;i<length;i++) {
                var title=paperlist[i].doctitle;
                if(title.length>40){
                    title=title.substr(0,40)+"...";
                }
                str+="<a href=\"/paper?pid="+paperlist[i].pid+"\" class=\"text-center\">"+title+"</a>";
                str+="<hr>";
            }
            for (let i=0;i<10-length;i++) {
                str+="<a>&nbsp</a>";
                str+="<hr>";
            }
            $('#ins-detail-paper-list').html(str);
        },
        error:function (error) {
            console.log(error)
        }
    });

    /**********keyword cloud************/
    var wordCloud = echarts.init(document.getElementById('institutionKeywordCloud'));
    var keywordList=[];
    $.ajax({
        async: false,
        type:'GET',
        url:'/institution/keyword',
        data:formatIid,
        success:function (res) {
            console.log(res.content);
            var keywordVOList=res.content.keywordVOList;
            for(var i=0;i<keywordVOList.length;i++){
                var keyword={
                    name:keywordVOList[i].kname,
                    value:keywordVOList[i].paperCount
                };
                keywordList.push(keyword)
            }
        },
        error:function (error) {
            console.log(error)
        }
    });
    var option1={
        title:{
            text:"click the thumbnail for details",
            textStyle:{
                color:'#1D3557',
                fontWeight:700,
                fontSize:13
            }
        },
        tooltip: {show: true},
        series:[{
            type: 'wordCloud',
            gridSize: 8,
            shape:'circle',
            sizeRange: [9, 48],
            rotationRange: [-90, 90],
            rotationStep: 12,
            left: 'center',
            top: 'center',
            right: null,
            bottom: null,
            width: '85%',
            height: '85%',
            drawOutOfBound: false,
            textStyle: {
                normal: {
                    color: function () {
                        return 'rgb(' +
                            Math.round(Math.random() * 255) +
                            ', ' + Math.round(Math.random() * 255) +
                            ', ' + Math.round(Math.random() * 255) + ')'
                    }
                }
            },
            data:keywordList
        }]
    };

    /**********institution relationship************/
    var institutionRlGraph=echarts.init(document.getElementById('institutionRelationshipGraph'));
    var nodeList=[];
    var edgeList=[];
    $.ajax({
        async: false,
        type:'GET',
        url:'/institution/collaborate',
        data:formatIid,
        success:function (res) {
            console.log(res.content);
            var collaborations=res.content.collaboration;
            var relevance=res.content.relevance;
            var relCnt=relevance.length;
            var insName=res.content.institution.iname;
            for(var i=0;i<relCnt;i++){
                var node={
                    name:collaborations[i].iname,
                    id:collaborations[i].iname,
                    symbolSize:collaborations[i].activity,
                    itemStyle: {
                        color: colorList[Math.floor(Math.random() * 6)]
                    },
                    activity:collaborations[i].activity,
                };
                var edge={
                    source:formatIid.iid,
                    target:collaborations[i].iid.toString(),
                    value:relevance[i],
                    sourceName:insName,
                    targetName:collaborations[i].iname,
                };
                nodeList.push(node);
                edgeList.push(edge);
            }
            nodeList.push({
                name:insName,
                id:insName,
                itemStyle: {
                    color: colorList[Math.floor(Math.random() * 6)]
                },
                symbolSize:res.content.institution.activity,
                activity:res.content.institution.activity,
            })
        },
        error:function (error) {
            console.log(error)
        }
    });
    var option2={
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
        series:[{
            type: 'graph',
            layout: 'force',
            nodes:nodeList,
            edges:edgeList,
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
        }]
    };


    wordCloud.setOption(option1);
    institutionRlGraph.setOption(option2);

    //点击事件
    wordCloud.on("click",function (param) {
        window.location.href='/wordcloud?id='+iid+'&type=iid'
    });

    institutionRlGraph.on('click' ,function (param) {
        window.location.href='/graph?id='+iid+'&type=iid'

    });

    window.addEventListener("resize", function () {
        wordCloud.resize();
        institutionRlGraph.resize();
    });

});
