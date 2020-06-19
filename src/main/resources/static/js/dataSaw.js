var did;
var pname;

$(document).ready(function () {
    did = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    pname=window.location.href.split('?')[1].split('&')[1].split('=')[1];

    var attrNum=0;
    var labels=['','','','',''];
    var selectAids=[];
    var simpleAidAndName;
    var result;

    $("#big-title").text(pname+"--可视化分析");
    $("#left-title").text(pname+"属性");

    getAttrList();
    function getAttrList() {
            getRequest(
                '/dataSource/getAttrList?did='+did,
                function (res) {
                    simpleAidAndName=res.content;
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

    $("#saw-check-btn").click(function () {
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
            //alert("ppp");

            //alert("ggg");
            var form={
                dataId: did,
                aidList: str,
            };
            selectAids=str;
            postRequest(
                '/dataSource/getDatasByAid',
                form,
                function (res) {
                    result=res.content;
                    renderSawList(res.content);
                    renderAidList()
                },
                function (error) {
                    alert(error);
                });
        });

    function renderAidList() {
            $('#product-sec').empty();
            var productStr='<option value="0">请选择要筛选的属性</option>';
            //var rechargeDomStr = '';
            selectAids.forEach(function (log) {
                var na='';
                //alert(simpleAidAndName[0].aid);
                simpleAidAndName.forEach(function(hit){
                    if(hit.aid==log){
                        na=hit.aname;
                    }
                });
                productStr +=
                    "<option value='"+log+"'>"+na+"</option>";
            });
            $('#product-sec').html(productStr);
        }

    function renderSawList(list){
        $('#data-saw-list').empty();

        //var rechargeDomStr = '';
        if(list==null){
            return false;
        }
        var dataSawStr="<thead class='table-head'>"+"<tr>";
        var mid=list[0].dataForAidList;
        mid.forEach(function(hit){
            dataSawStr+="<th>"+hit.aname+"</th>";
        })

        dataSawStr+="<th>"+"timestamp"+"</th>";
        dataSawStr+="</tr>"+"</thead>";
        dataSawStr+="<tbody id='data-source-list'>";
        list.forEach(function (log) {
            dataSawStr+=
                "<tr data-datasource="+JSON.stringify(log)+">";
            var mid=log.dataForAidList;
            mid.forEach(function(hit){
                dataSawStr+="<th>"+hit.value+"</th>";
            })
            dataSawStr+="<th>"+log.time+"</th>"
            dataSawStr+="</tr>";
        });
        dataSawStr+="</tbody>";
        $('#data-saw-list').html(dataSawStr);
    }

    $("#data-func-form-btn").click(function (){
        var form={
            aid:document.getElementById('product-sec').value,
            func:document.getElementById('func-sec').value,
            num: document.getElementById('num-sec').value,
        };
        if(form.aid==0){
            alert("未选择属性");
            return false;
        }
        var mid=new Array();
        var i=0;
        result.forEach(function (log) {
            log.dataForAidList.forEach(function(hit){
                if(hit.aid==form.aid){
                    if((form.func==0&&Number(hit.value)==Number(form.num))||(form.func==1&&Number(hit.value)>Number(form.num))||(form.func==2&&Number(hit.value)<Number(form.num))||(form.func==3&&Number(hit.value)>=Number(form.num))||(form.func==4&&Number(hit.value)<=Number(form.num))){
                        mid[i]=log;
                        i++;
                    }
                }
            })
        });
        result=mid;
        renderSawList(result);
        $("#sawModal").modal('hide');

    });

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

});