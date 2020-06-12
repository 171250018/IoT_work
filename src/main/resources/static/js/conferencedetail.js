$(document).ready(function () {
    var cid=parseInt(window.location.href.split('?')[1].split('=')[1]);
    var keywordList=[];
    $.ajax({
        async: false,
        type:"GET",
        url:"/conference/keyword?cid="+cid,
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

    var wordCloud = echarts.init(document.getElementById("conference-keyword-wc"));

    var option = {
        title:{
            text:"click the thumbnail for details",
            textStyle:{
                color:'#1D3557',
                fontWeight:700,
                fontSize:13
            }
        },
        tooltip: {show: true},
        series: [ {
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
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                }
            },
            data: keywordList
        } ]
    };

    wordCloud.setOption(option);

    $.ajax({
        async : false,
        url : '/conference/conferenceInfo?cid='+cid,
        type : 'GET',
        contentType : 'application/json',
        success : function(res) {
            if (res.success){
                sessionStorage.clear();
                // alert(JSON.stringify(res.content));
                var papercount=JSON.parse(JSON.stringify(res.content.paperCount));
                var cname=JSON.parse(JSON.stringify(res.content.cname));
                $('#conference-detail-paper-count').html(papercount);
                $('#conference-detail-cname').html(cname);
            }
            else{
                alert("failed to get conference info");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });

    $.ajax({
        async : false,
        url : '/conference/paperList?cid='+cid,
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
                $('#conference-detail-paper-list').html(str);
            }
            else{
                alert("failed to get conference paperlist");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });

    $.ajax({
        async : false,
        url : '/conference/authorList?cid='+cid,
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
                $('#conference-detail-author-list').html(str);
            }
            else{
                alert("failed to get conference authorlist");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });

    $.ajax({
        async : false,
        url : '/conference/institutionList?cid='+cid,
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
                $('#conference-detail-ins-list').html(str);
            }
            else{
                alert("failed to get conference inslist");
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });

    //点击事件
    wordCloud.on("click",function (param) {
        window.location.href='/wordcloud?id='+cid+'&type=cid'
    });
});

