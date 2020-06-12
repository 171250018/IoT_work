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
            break;

        case "iid":
            paramType="institution";
            formatId={"iid":id};
            break;
        case "cid":
            paramType="conference";
            formatId={"cid":id};
            break
    }

    var wordCloud = echarts.init(document.getElementById('word-cloud-details'));
    var keywordList=[];
    $.ajax({
        async: false,
        type:'GET',
        url:'/'+paramType+'/keyword?'+type+'='+id,
        data:formatId,
        success:function (res) {
            console.log(res.content);
            var keywordVOList=res.content.keywordVOList;
            for(var i=0;i<keywordVOList.length;i++){
                var keyword={
                    id:keywordVOList[i].kid,
                    name:keywordVOList[i].kname,
                    value:keywordVOList[i].paperCount,
                    paperCount:keywordVOList[i].paperCount,
                    activity:keywordVOList[i].activity
                };
                keywordList.push(keyword)
            }
        },
        error:function (error) {
            console.log(error)
        }
    });
    var option={
        tooltip: {show: true},
        series:[{
            type: 'wordCloud',
            gridSize: 8,
            shape:'circle',
            sizeRange: [30, 120],
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

    wordCloud.setOption(option);

    wordCloud.on('click',function (param) {
        $('#keyword-name').text("keyword:  "+param.data.name);
        $('#paper-count').text("Paper:  "+param.data.paperCount);
        $('#activity').text("Activity:"+param.data.activity);
        $('#keyword-link').attr('href','/keyword?kid='+param.data.id)
    });
    window.addEventListener("resize", function () {
        wordCloud.resize();
    });
});
