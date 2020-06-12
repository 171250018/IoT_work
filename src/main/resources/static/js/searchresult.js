$(document).ready(function () {
    //sidetyperesult
    var typetitle="";
    if(sessionStorage.getItem('type')==="1"){
        typetitle+="Author";
        //sidetyperesultlist
        var resultlist="";
        var name=sessionStorage.getItem('content');
        var data={name:name};
        $.ajax({
            async : false,
            url : '/author/searchByName',
            type : 'GET',
            contentType : 'application/json',
            dataType:'json',
            data : data,
            success : function(res) {
                if (res.success){
                    // alert(JSON.stringify(res.content.authorVOList));
                    var authorlist=JSON.parse(JSON.stringify(res.content.authorVOList));
                    for (let i=0;i<authorlist.length;i++) {
                        var name=authorlist[i].aname;
                        var papercount=authorlist[i].paperCount;
                        var id=authorlist[i].aid;
                        resultlist+="<div>";
                        resultlist+="<a class=\"type-list text-center\"  href=\"/author?aid="+id+"\">"+name+"&nbsp(<span>"+papercount+"</span>)</a>";
                        resultlist+="</div>";
                        resultlist+="<hr>";
                    }
                }
                else{
                    alert('Your search did not match any author');
                }
            },
            error:function(error){
                alert(JSON.stringify(error));
            },
        });
        $('#search-result-tag-list').html(resultlist);
    }
    else if(sessionStorage.getItem('type')==="2"){
        typetitle+="Institution";
        //sidetyperesultlist
        var name=sessionStorage.getItem('content');
        var data={name:name};
        var resultlist="";
        $.ajax({
            async : false,
            url : '/institution/searchByName',
            type : 'GET',
            contentType : 'application/json',
            dataType:'json',
            data : data,
            success : function(res) {
                if (res.success){
                    // alert(JSON.stringify(res.content.institutionVOList));
                    var inslist=JSON.parse(JSON.stringify(res.content.institutionVOList));
                    for (let i=0;i<inslist.length;i++) {
                        var name=inslist[i].iname;
                        var papercount=inslist[i].paperCount;
                        var id=inslist[i].iid;
                        resultlist+="<div>";
                        resultlist+="<a class=\"type-list text-center\"  href=\"/institution?iid="+id+"\">"+name+"&nbsp(<span>"+papercount+"</span>)</a>";
                        resultlist+="</div>";
                        resultlist+="<hr>";
                    }
                }
                else{
                    alert('Your search did not match any institution');
                }
            },
            error:function(error){
                alert(error);
            },
        });
        $('#search-result-tag-list').html(resultlist);
    }
    else if(sessionStorage.getItem('type')==="3"){
        typetitle+="Conference";
        //sidetyperesultlist
        var resultlist="";
        var name=sessionStorage.getItem('content');
        var data={name:name};
        $.ajax({
            async : false,
            url : '/conference/searchByName',
            type : 'GET',
            contentType : 'application/json',
            dataType:'json',
            data : data,
            success : function(res) {
                if (res.success){
                    // alert(JSON.stringify(res.content.conferenceVOList));
                    var conferencelist=JSON.parse(JSON.stringify(res.content.conferenceVOList));
                    for (let i=0;i<conferencelist.length;i++) {
                        var name=conferencelist[i].cname;
                        var id=conferencelist[i].cid;
                        var papercount=conferencelist[i].paperCount;
                        resultlist+="<div>";
                        resultlist+="<a class=\"type-list text-center\" href=\"/conference?cid="+id+"\">"+name+"&nbsp(<span>"+papercount+"</span>)</a>";
                        resultlist+="</div>";
                        resultlist+="<hr>";
                    }
                }
                else{
                    alert('Your search did not match any conference');
                }
            },
            error:function(error){
                alert(error);
            },
        });
        $('#search-result-tag-list').html(resultlist);
    }
    $('#search-result-type-title').html(typetitle);

    //keywords tags
    var content_tag=sessionStorage.getItem('content');
    $('#search-content').html(content_tag);

    //paper cards
    var temp=sessionStorage.getItem('paperlist');
    var paperlist=JSON.parse(temp);
    var str="";
    for (i=0;i<paperlist.length;i++) {
        item=paperlist[i];
        var institute="";
        if(item.institutions.length>127){
            institute=item.institutions.substring(0,127);
            institute+="...";
        }
        else institute=item.institutions;

        str=str+"<div class=\"col-md-12\">\n" +
            "                    <div class=\"card my-card row no-gutters border rounded overflow-hidden flex-md-row h-md-250 position-relative\">\n" +
            "                        <div class=\"col p-4 d-flex flex-column position-static\">\n" +
            "                            <h3 class=\"mb-0\">"+item.doctitle+"</h3>\n" +
            "                            <hr>\n" +
            "                            <div class=\"mb-1 \"><span class=\"badge badge-info\"> Author(s) </span> <a>"+item.authors+"</a></div>\n" +
            "                            <div class=\"mb-1 \"> <span class=\"badge badge-info\"> Institution(s) </span> <a>"+institute+"</a></div>\n" +
            "                            <div class=\"mb-1 \"><span class=\"badge badge-info\"> Conference </span> <a>"+item.pubtitle+"</a></div>\n" +
            "                            <div class=\"mb-1 \"><span class=\"badge badge-info\"> Keywords </span> <a>"+item.keywords+"</a></div>\n" +
            "                            <hr>\n" +
            "                            <a href=\"/paper?pid="+item.pid+"\" ><i class=\"fas fa-angle-up\"></i>  Details...</a>" +
            "                        </div>\n" +
            "                        <div class=\"col-auto d-none d-lg-block\">\n" +
            "                            <svg class=\"bd-placeholder-img\" width=\"200\" height=\"100%\" xmlns=\"http://www.w3.org/2000/svg\" preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" aria-label=\"Placeholder: Thumbnail\">\n" +
            "                                <title>Placeholder</title>\n" +
            "                                <rect width=\"100%\" height=\"100%\" fill=\"#A8DADC\"></rect>\n" +
            "                            </svg>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>";
    }
    $('#paper-list').html(str);

    //previous
    str="";
    if(parseInt(sessionStorage.getItem('page'))===1){
        str="<a type=\"button\" style=\"color: #1D3557\" title=\"Previous page\"><i class=\"fas fa-angle-left\"></i><span>Previous</span></a>";
    }
    else{
        str="<a type=\"button\" style=\"color: #1D3557\" title=\"Previous page\" onclick=\"previousclick()\"><i class=\"fas fa-angle-left\"></i><span>Previous</span></a>";
    }
    $('#previous').html(str);

    //next
    str="";
    var TN=trynext();
    if(paperlist.length<10||paperlist.length===10&& TN===false){
        //少于10个，只能获得本页
        str="<a type=\"button\" style=\"color: #1D3557\"  title=\"Next page\" \">Next<span><i class=\"fas fa-angle-right\"></i></span></a>";
    }
    else{
        //多于10个,可以获得下一页
        str="<a type=\"button\" style=\"color: #1D3557\"  title=\"Next page\" onclick=\"nextclick()\">Next<span><i class=\"fas fa-angle-right\"></i></span></a>";
    }
    $('#next').html(str);
});

function searchclick() {
    var formData = getSearchForm();
    $.ajax({
        async : false,
        url : '/search',
        type : 'GET',
        contentType : 'application/json',
        dataType:'json',
        data : formData,
        success : function(res) {
            if (res.success){
                sessionStorage.clear()
                sessionStorage.setItem('page',1);
                sessionStorage.setItem('type',formData.type);
                sessionStorage.setItem('content',formData.content);
                sessionStorage.setItem('paperlist',JSON.stringify(res.content.resultlist));
                location.reload()
            }
            else{
                alert('Your search did not match any papers');
            }
        },
        error:function(error){
            alert(error);
        },
    });
}
function getSearchForm() {
    var type=$(".selector").val();
    return {
        type:type,
        content: $('#search-input').val(),
        page:1
    };
}
function trynext() {
    var formData = {
        type:sessionStorage.getItem('type'),
        content:sessionStorage.getItem('content'),
        page:(parseInt(sessionStorage.getItem('page'))+1)
    };
    $.ajax({
        async : false,
        url : '/search',
        type : 'GET',
        contentType : 'application/json',
        dataType:'json',
        data : formData,
        success : function(res) {
            return true;
        },
        error:function(error){
            return false;
        },
    });
}
function previousclick() {
    var formData = {
        type:sessionStorage.getItem('type'),
        content:sessionStorage.getItem('content'),
        page:(parseInt(sessionStorage.getItem('page'))-1)
    };
    $.ajax({
        async : false,
        url : '/search',
        type : 'GET',
        contentType : 'application/json',
        dataType:'json',
        data : formData,
        success : function(res) {
            sessionStorage.setItem('page',formData.page);
            sessionStorage.setItem('type',formData.type);
            sessionStorage.setItem('content',formData.content);
            sessionStorage.setItem('paperlist',JSON.stringify(res.content.resultlist));
            location.reload()
        },
        error:function(error){
            alert(error);
        },
    });
}
function nextclick() {
    var formData = {
        type:sessionStorage.getItem('type'),
        content:sessionStorage.getItem('content'),
        page:(parseInt(sessionStorage.getItem('page'))+1)
    };
    $.ajax({
        async : false,
        url : '/search',
        type : 'GET',
        contentType : 'application/json',
        dataType:'json',
        data : formData,
        success : function(res) {
            sessionStorage.setItem('page',formData.page);
            sessionStorage.setItem('type',formData.type);
            sessionStorage.setItem('content',formData.content);
            sessionStorage.setItem('paperlist',JSON.stringify(res.content.resultlist));
            location.reload()
        },
        error:function(error){
            alert(error);
        },
    });
}
