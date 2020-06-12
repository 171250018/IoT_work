var colorList = [
    "#3fb1e3",
    "#6be6c1",
    "#626c91",
    "#a0a7e6",
    "#c4ebad",
    "#96dee8"
];
$(document).ready(function () {
    // var aid=2748;   //临时数据
    var url=location.href;
    var i=url.indexOf("=");
    var aid=unescape(url.substr(i+1));
    var formatAid={"aid":aid};


    /*********************searchAuthorInfo************************/
    $.ajax({
        type:"GET",
        url:"/author/authorInfo",
        async:false,
        data:formatAid,
        success:function (res) {
            // console.log(res)
            // alert(JSON.stringify(res.content));
            var authorName=res.content.aname;
            var paperNum=res.content.paperCount;
            var paperCited=res.content.paperCited;
            var institutions=res.content.institutions;
            var iidList=res.content.iidList;
            //activity
            $('#author-detail-act').append(res.content.activity);
            //Author
            $('#author-detail-aname').append(authorName);

            //institution
            var str="";
            str+= "<span class=\"badge badge-info\"> Institution(s)</span>";
            str+="&nbsp ";
            if(institutions.length>1){
                //不止一个
                for (let i=0;i<institutions.length-1;i++) {
                    if(iidList[i]===1518){
                        continue;
                    }
                    str+=
                        "<a href='/institution?iid="+iidList[i]+"'>"+institutions[i]+";</a>\n";
                }
                if(iidList[institutions.length-1]===1518){

                }
                else {
                    str+= "               <a href='/institution?iid="+iidList[institutions.length-1]+"'>"+institutions[institutions.length-1]+"</a>\n";
                }
                str+=
                    "            </h5>\n";
            }
            else{
                //只有一个
                if(iidList[0]===1518){

                }
                else {
                    str+=
                        "<a href='/institution?iid="+iidList[0]+"'>"+institutions[0]+"</a>\n";
                }
            }
            $('#author-detail-ins').append(str);
            $('#author-detail-paper').append(paperNum);

            $('#author-detail-cite').append(paperCited);
        },
        error:function (error) {
            alert(error)
        }
    });

    /***************************getConferences*****************************/
    $.ajax({
        type:"GET",
        url:"/author/conferenceList",
        data:formatAid,
        success:function (res) {
            var conferencelist=JSON.parse(JSON.stringify(res.content.conferenceVOList));
            var str="";
            var length=conferencelist.length;
            if(conferencelist.length>10){
                length=10;
            }
            for (let i=0;i<length;i++) {
                var title=conferencelist[i].cname;
                if(title.length>40){
                    title=title.substr(0,40)+"...";
                }
                str+="<a href=\"/conference?cid="+conferencelist[i].cid+"\" class=\"text-center\">"+title+"</a>";
                str+="<hr>";
            }
            for (let i=0;i<10-length;i++) {
                str+="<a>&nbsp</a>";
                str+="<hr>";
            }
            $('#author-detail-conference-list').html(str);
        },
        error:function (error) {
            console.log(error)
        }
    });

    /*************************searchAuthorPapers*****************************/
    $.ajax({
        type:'GET',
        url:'/author/paperList',
        data:formatAid,
        success:function (res) {
            // console.log(res.content);
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
            $('#author-detail-paper-list').html(str);
        },
        error:function (error) {
            console.log(error)
        }
    });

    /*************************Wordcloud&authorRelationship*********************/
    var wordCloud = echarts.init(document.getElementById('author-keyword-cloud'));
    var authorRlGraph=echarts.init(document.getElementById('author-relationship-graph'));
    var keywordList=[];
    var nodeList=[];
    var edgeList=[];
    $.ajax({
        async: false,
        type:"GET",
        url:"/author/keyword",
        data:formatAid,
        success:function (res) {
            // alert(JSON.stringify(res.content));
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
    $.ajax({
        async: false,
        type:"GET",
        url:"/author/collaborate",
        data:formatAid,
        success:function (res) {
            // console.log(res.content);
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
                    activity:collaborations[i].activity,
                };
                var edge={
                    source:aid,
                    target:collaborations[i].aid.toString(),
                    value:relevance[i],
                    sourceName:authorName,
                    targetName:collaborations[i].aname,
                };
                nodeList.push(node);
                edgeList.push(edge);
            }
            nodeList.push({
                name:authorName,
                id:aid.toString(),
                // symbolSize:50,
                itemStyle: {
                    color: colorList[Math.floor(Math.random() * 6)]
                },
                symbolSize:res.content.author.activity,
                activity:res.content.author.activity,
            })
        },
        error:function (error) {
            console.log(error);
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
    authorRlGraph.setOption(option2);

    //点击事件
    wordCloud.on("click",function (param) {
        window.location.href='/wordcloud?id='+aid+'&type=aid'
    });

    authorRlGraph.on('click' ,function (param) {
        window.location.href='/graph?id='+aid+'&type=aid'

    });

    window.addEventListener("resize", function () {
        wordCloud.resize();
        authorRlGraph.resize();
    });


});


