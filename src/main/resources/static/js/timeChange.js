var did;
var pname;

$(document).ready(function () {
    did = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    pname=window.location.href.split('?')[1].split('&')[1].split('=')[1];

    var attrNum=0;

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

    $("#time-check-btn").click(function () {
        var startTime=document.getElementById('start').value;
        //alert(startTime);
        var endTime=document.getElementById('end').value;
        //alert(endTime);
        var attrs=document.getElementsByName('attribute');
        var thisLength=attrs.length;
        //alert("ppp");
        //alert(thisLength);
        //alert(attrs);
        //alert("ggg");
        //alert(attrNum);
        var str=new Array(attrNum);
        var x=0;
        for(var i=0;i<thisLength;i++){
            if(attrs[i].checked==true){
                x=x+1;
                str[i]=attrs[i].value;
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
        }

        getRequest(
            '/dataSource/getDatasByTime',
            form,
            function (res) {
                alert("前面都没有问题");
                //renderAttrList(res.content);
            },
            function (error) {
                alert(error);
            });
    });


});