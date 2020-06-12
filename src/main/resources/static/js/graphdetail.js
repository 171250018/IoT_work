var nodeList=[];
var edgeList=[];
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
    var i=url.indexOf("?");
    var params=url.substr(i+1).split(/[=&]/);
    var id=unescape(params[1]);
    var type=params[3];
    var formatData={
        "id":id,
        "type":type
    };
    var formatId;

    var paramType="";
    switch (type) {
        case "aid":
            paramType="author";
            formatId={"aid":id};
            getAuthorRLGraph(formatId);
            break;

        case "iid":
            paramType="institution";
            formatId={"iid":id};
            getInstitutionRLGraph(formatId);
            break;
        case "kid":
            paramType="keyword";
            formatId={"kid":id};
            getKeywordRLGraph(formatId);
            break;
    }

    var rlGraph=echarts.init(document.getElementById('graph-details'));

    var option={
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
            width: '90%',
            height: '90%',
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

    rlGraph.setOption(option);

    rlGraph.on('click' ,function (param) {
        console.log(param);
        if(param.dataType==="node"){
            $('#node-name').text(param.data.name);
            $('#node-paper-count').text("Paper:  "+param.data.paperCount);
            if(type!=='kid') {
                $('#node-paper-cited').text("Citation:  " + param.data.paperCited)
            }
            $('#node-activity').text("Activity:  "+param.data.activity);
            $('#node-link').attr('href','/'+paramType+'?'+type+'='+param.data.id)
        }
        else{
            $('#type').text("Type: "+param.data.type);
            $('#node-name').text("Main: "+param.data.sourceName);
            $('#other-item').text("Related: "+param.data.targetName);
            $('#relevance').text("Relevance:  "+param.data.value);
            $('#node-link').attr('href','javascript:void(0)')
        }
    });

    window.addEventListener("resize", function () {
        rlGraph.resize();
    });

});

function getAuthorRLGraph(formatId) {
    $.ajax({
        async: false,
        type:"GET",
        url:"/author/collaborate",
        data:formatId,
        success:function (res) {
            console.log(res.content);
            // alert(JSON.stringify(res.content));
            var collaborations=res.content.collaboration;
            var relevance=res.content.relevance;
            var relCnt=relevance.length;
            var authorName=res.content.author.aname;
            for(var i=0;i<relCnt;i++){
                var node={
                    name:collaborations[i].aname,
                    id:collaborations[i].aid.toString(),
                    symbolSize:collaborations[i].activity,
                    itemStyle: {
                        color: colorList[Math.floor(Math.random() * 6)]
                    },
                    paperCount:collaborations[i].paperCount,
                    paperCited:collaborations[i].paperCited,
                    activity:collaborations[i].activity,
                };
                var edge={
                    source:formatId.aid,
                    target:collaborations[i].aid.toString(),
                    value:relevance[i],
                    sourceName:authorName,
                    targetName:collaborations[i].aname,
                    type:"Author"
                };
                nodeList.push(node);
                edgeList.push(edge);
            }
            nodeList.push({
                name:authorName,
                id:formatId.aid.toString(),
                symbolSize:res.content.author.activity,
                itemStyle: {
                    color: colorList[Math.floor(Math.random() * 6)]
                },
                paperCount:res.content.author.paperCount,
                paperCited:res.content.author.paperCited,
                activity:res.content.author.activity
            })
        },
        error:function (error) {
            console.log(error);
        }
    });
}

function getInstitutionRLGraph(formatId) {
    $.ajax({
        async: false,
        type:"GET",
        url:"/institution/collaborate",
        data:formatId,
        success:function (res) {
            console.log(res.content);
            // alert(JSON.stringify(res.content));
            var collaborations=res.content.collaboration;
            var relevance=res.content.relevance;
            var relCnt=relevance.length;
            var insname=res.content.institution.iname;
            for(var i=0;i<relCnt;i++){
                var node={
                    name:collaborations[i].iname,
                    id:collaborations[i].iid.toString(),
                    // symbolSize:relevance[i]*10,
                    symbolSize:collaborations[i].activity,
                    itemStyle: {
                        color: colorList[Math.floor(Math.random() * 6)]
                    },
                    paperCount:collaborations[i].paperCount,
                    paperCited:collaborations[i].papercited,
                    activity:collaborations[i].activity,
                };
                var edge={
                    source:formatId.iid,
                    target:collaborations[i].iid.toString(),
                    value:relevance[i],
                    sourceName:insname,
                    targetName:collaborations[i].iname,
                    type:"Institution"
                };
                nodeList.push(node);
                edgeList.push(edge);
            }
            nodeList.push({
                name:insname,
                id:formatId.iid.toString(),
                symbolSize:res.content.institution.activity,
                itemStyle: {
                    color: colorList[Math.floor(Math.random() * 6)]
                },
                paperCount:res.content.institution.paperCount,
                paperCited:res.content.institution.paperCited,
                activity:res.content.institution.activity,
            })
        },
        error:function (error) {
            console.log(error);
        }
    });
}

function getKeywordRLGraph(formatId) {
    $.ajax({
        async: false,
        type:"GET",
        url:"/keyword/relation",
        data:formatId,
        success:function (res) {
            // console.log(res.content);
            console.log(JSON.stringify(res.content));
            var collaborations=res.content.collaboration;
            var relevance=res.content.relevance;
            var relCnt=relevance.length;
            var kname=res.content.keyword.kname;
            for(var i=0;i<relCnt;i++){
                var node={
                    name:collaborations[i].kname,
                    id:collaborations[i].kid.toString(),
                    symbolSize:collaborations[i].activity,
                    itemStyle: {
                        color: colorList[Math.floor(Math.random() * 6)]
                    },
                    paperCount:collaborations[i].paperCount,
                    paperCited:collaborations[i].paperCited,
                    activity:collaborations[i].activity,
                };
                var edge={
                    source:formatId.kid,
                    target:collaborations[i].kid.toString(),
                    value:relevance[i],
                    sourceName:kname,
                    targetName:collaborations[i].kname,
                    type:"Keyword"
                };
                nodeList.push(node);
                edgeList.push(edge);
            }
            nodeList.push({
                name:kname,
                id:formatId.kid.toString(),
                // symbolSize:50,
                symbolSize:res.content.keyword.activity,
                itemStyle: {
                    color: colorList[Math.floor(Math.random() * 6)]
                },
                paperCount:res.content.keyword.paperCount,
                paperCited:res.content.keyword.paperCited,
                activity:res.content.keyword.activity,
            })
        },
        error:function (error) {
            console.log(error);
        }
    });
}
