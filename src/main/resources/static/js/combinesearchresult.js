$(document).ready(function () {
    //keywords tags
    var tags="";
    var ins=sessionStorage.getItem('institution');
    if(ins===""){

    }
    else {
        var inslist=[];
        inslist=ins.split(';');
        for (i=0;i<inslist.length;i++) {
            tags+="<a class=\"px-2 pt-1 badge badge-success\">"+ins+"</a>";
            tags+="&nbsp";
        }
    }
    var keyword=sessionStorage.getItem('keyword');
    if(keyword===""){

    }
    else {
        var keywordlist=[];
        keywordlist=keyword.split(';');
        for (i=0;i<keywordlist.length;i++) {
            tags+="<a class=\"px-2 pt-1 badge badge-secondary\">"+keyword+"</a>";
            tags+="&nbsp";
        }
    }
    var author=sessionStorage.getItem('author');
    if(author===""){

    }
    else {
        var authorlist=[];
        authorlist=author.split(';');
        for (i=0;i<authorlist.length;i++) {
            tags+="<a class=\"px-2 pt-1 badge badge-pink\">"+authorlist[i]+"</a>";
            tags+="&nbsp";
        }
    }
    var doi=sessionStorage.getItem('doi');
    if(doi===""){

    }
    else {
        tags+="<a class=\"px-2 pt-1 badge badge-info\">"+doi+"</a>";
        tags+="&nbsp";
    }
    var startyear=sessionStorage.getItem('startyear');
    if(startyear===""){

    }
    else {
        tags+="<a class=\"px-2 pt-1 badge badge-danger\">"+startyear+"</a>";
        tags+="&nbsp";
    }
    var endyear=sessionStorage.getItem('endyear');
    if(endyear===""){

    }
    else {
        tags+="<a class=\"px-2 pt-1 badge badge-warning\">"+endyear+"</a>";
        tags+="&nbsp";
    }
    var title=sessionStorage.getItem('title');
    if(title===""){

    }
    else {
        tags+="<a class=\"px-2 pt-1 badge badge-purple\">"+title+"</a>";
        tags+="&nbsp";
    }
    $('#keyword-tag-group').html(tags);

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
            "                            <div class=\"mb-1 \"><span class=\"badge badge-success\"> Author(s) </span> <a>"+item.authors+"</a></div>\n" +
            "                            <div class=\"mb-1 \"> <span class=\"badge badge-danger\"> Institution(s) </span> <a>"+institute+"</a></div>\n" +
            "                            <div class=\"mb-1 \"><span class=\"badge badge-light\"> Conference </span> <a>"+item.pubtitle+"</a></div>\n" +
            "                            <div class=\"mb-1 \"><span class=\"badge badge-warning\"> Keywords </span> <a>"+item.keywords+"</a></div>\n" +
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
        str="<a type=\"button\" style=\"color: #1D3557\"  title=\"Next page\"\">Next<span><i class=\"fas fa-angle-right\"></i></span></a>";
    }
    else{
        //多于10个,可以获得下一页
        str="<a type=\"button\" style=\"color: #1D3557\"  title=\"Next page\" onclick=\"nextclick()\">Next<span><i class=\"fas fa-angle-right\"></i></span></a>";
    }
    $('#next').html(str);
});

function trynext() {
    var formData = {
        institution:sessionStorage.getItem('institution'),
        keyword:sessionStorage.getItem('keyword'),
        author:sessionStorage.getItem('author'),
        doi:sessionStorage.getItem('doi'),
        endyear:sessionStorage.getItem('endyear'),
        startyear:sessionStorage.getItem('startyear'),
        title:sessionStorage.getItem('title'),
        page:(parseInt(sessionStorage.getItem('page'))+1)
    };
    $.ajax({
        async : false,
        url : '/cbsearch',
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
        institution:sessionStorage.getItem('institution'),
        keyword:sessionStorage.getItem('keyword'),
        author:sessionStorage.getItem('author'),
        doi:sessionStorage.getItem('doi'),
        endyear:sessionStorage.getItem('endyear'),
        startyear:sessionStorage.getItem('startyear'),
        title:sessionStorage.getItem('title'),
        page:(parseInt(sessionStorage.getItem('page'))-1)
    };
    $.ajax({
        async : false,
        url : '/cbsearch',
        type : 'GET',
        contentType : 'application/json',
        dataType:'json',
        data : formData,
        success : function(res) {
            sessionStorage.setItem('page',formData.page);
            sessionStorage.setItem('institution',formData.institution);
            sessionStorage.setItem('keyword',formData.keyword);
            sessionStorage.setItem('author',formData.author);
            sessionStorage.setItem('doi',formData.doi);
            sessionStorage.setItem('endyear',formData.endyear);
            sessionStorage.setItem('startyear',formData.startyear);
            sessionStorage.setItem('title',formData.title);
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
        institution:sessionStorage.getItem('institution'),
        keyword:sessionStorage.getItem('keyword'),
        author:sessionStorage.getItem('author'),
        doi:sessionStorage.getItem('doi'),
        endyear:sessionStorage.getItem('endyear'),
        startyear:sessionStorage.getItem('startyear'),
        title:sessionStorage.getItem('title'),
        page:(parseInt(sessionStorage.getItem('page'))+1)
    };
    $.ajax({
        async : false,
        url : '/cbsearch',
        type : 'GET',
        contentType : 'application/json',
        dataType:'json',
        data : formData,
        success : function(res) {
            sessionStorage.setItem('page',formData.page);
            sessionStorage.setItem('institution',formData.institution);
            sessionStorage.setItem('keyword',formData.keyword);
            sessionStorage.setItem('author',formData.author);
            sessionStorage.setItem('doi',formData.doi);
            sessionStorage.setItem('endyear',formData.endyear);
            sessionStorage.setItem('startyear',formData.startyear);
            sessionStorage.setItem('title',formData.title);
            sessionStorage.setItem('paperlist',JSON.stringify(res.content.resultlist));
            location.reload()
        },
        error:function(error){
            alert(error);
        },
    });
}
