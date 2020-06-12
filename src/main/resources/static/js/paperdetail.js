$(document).ready(function () {
    //获得pid
    var pid=parseInt(window.location.href.split('?')[1].split('=')[1]);
    $.ajax({
        async : false,
        url : '/getpaperdetail?pid='+pid,
        type : 'GET',
        contentType : 'application/json',
        success : function(res) {
            if (res.success){
                sessionStorage.clear();
                // alert(JSON.stringify(res.content));
                var item=JSON.parse(JSON.stringify(res.content));
                sessionStorage.setItem('pdflink',item.pdflink);
                //加载页面
                var str="";
                var title="";
                title+=item.doctitle;
                $('#paper-detail-title').html(title);
                //author
                if(item.aidList.length>1){
                    //不止一个
                    var authornames=item.authors.split(';');
                    str+=
                        "            <h5>\n" +
                        "                <span class=\"badge badge-purple\"> Author(s) </span> ";
                    for (let i=0;i<item.aidList.length-1;i++) {
                        str+=
                            "               <a href=\"/author?aid="+item.aidList[i]+"\">"+authornames[i]+";</a>\n";
                    }
                    str+=
                        "               <a href=\"/author?aid="+item.aidList[item.aidList.length-1]+"\">"+authornames[item.aidList.length-1]+"</a>\n";
                    str+=
                        "            </h5>\n";
                }
                else{
                    //只有一个
                    str+=
                        "            <h5>\n" +
                        "                <span class=\"badge badge-purple\"> Author(s) </span> <a href='/author?aid='"+item.aidList[0]+" >"+item.authors+"</a>\n" +
                        "            </h5>\n";
                }
                str+="<br>";
                //institution
                if(item.iidList.length>1){
                    //不止一个

                    var insnames=item.institutions.split(';');
                    str+=
                        "            <h5>\n" +
                        "                <span class=\"badge badge-purple\"> Institution(s) </span> ";
                    for (let i=0;i<item.iidList.length-1;i++) {
                        if(item.iidList[i]===1518){
                            continue;
                        }
                        str+=
                            "               <a href='/institution?iid="+item.iidList[i]+"'>"+insnames[i]+";</a>\n";
                    }
                    if(item.iidList[item.iidList.length-1]===1518){

                    }
                    else {
                        str +=
                            "               <a href='/institution?iid=" + item.iidList[item.iidList.length - 1] + "'>" + insnames[item.iidList.length - 1] + "</a>\n";
                    }
                    str+=
                        "            </h5>\n";
                }
                else{
                    //只有一个
                    str+=" <h5> <span class=\"badge badge-purple\"> Institution(s) </span>";
                    if(item.iidList[0]===1518){

                    }
                    else {
                    str+= "  <a href=\"/institution?iid="+item.iidList[0]+"\">"+item.institutions+"</a>\n";
                    }
                }
                str+="</h5>";
                str+="<br>";
                //conference
                str+=
                    "            <h5>\n" +
                    "                <span class=\"badge badge-purple\"> Conference </span> <a href='/conference?cid="+item.cid+"' >"+item.pubtitle+"</a>\n" +
                    "            </h5>\n";
                str+="<br>";
                //keywords
                if(item.kidList.length>1){
                    //不止一个
                    var keynames=item.keywords.split(/[,;]/);
                    str+=
                        "            <h5>\n" +
                        "                <span class=\"badge badge-purple\"> Keywords </span> ";
                    for (let i=0;i<item.kidList.length-1;i++) {
                        str+=
                            "               <a href=\"/keyword?kid="+item.kidList[i]+"\">"+keynames[i]+";</a>\n";
                    }
                    str+=
                        "               <a href=\"/keyword?kid="+item.kidList[item.kidList.length-1]+"\">"+keynames[item.kidList.length-1]+"</a>\n";
                    str+=
                        "            </h5>\n";
                }
                else{
                    //只有一个
                    str+=
                        "            <h5>\n" +
                        "                <span class=\"badge badge-purple\"> Keywords </span> <a href='/keyword?kid="+item.kidList[0]+"'>"+item.keywords+"</a>\n" +
                        "            </h5>\n";
                }
                str+="<br>";
                //abstract
                str+=
                    "            <h5>\n" +
                    "                <span class=\"badge badge-purple\"> Abstract </span>\n" +
                    "            </h5>\n" +
                    "\n" +
                    "            <p>"+item.abstracts+
                    "            </p>\n" +
                    "            <BR>\n" +
                    "            <BR>\n" +
                    "            <h5>\n" +
                    "                <span class=\"badge badge-purple\"> DOI </span> "+item.doi +
                    "            </h5>\n" +
                    "        </div>";
                $('#paper-details').html(str);

                str="";
                str+="        <div class=\" content px-4 py-2 bg-right h-100\">\n" +
                    "            <button  type=\"button\" onclick=\"downloadclick()\" class=\"btn btn-pdf btn-lg\" style=\"color: #E63946; font-weight: 700\" title=\"download DPF\">\n" +
                    "                <div>\n" +
                    "                    <i class=\"far fa-file-pdf fa-2x\"></i>\n" +
                    "                    <span style=\"font-size: 24px;\">  DOWNLOAD  </span>\n" +
                    "                </div>\n" +
                    "            </button>\n" +
                    "        </div>";
                //pdf download
                $('#download-button').html(str);
            }
            else{
                alert('Your search did not match any institution');
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
        },
    });
});
function downloadclick() {
var pdflink=sessionStorage.getItem('pdflink');
    window.location.href=pdflink;
}
