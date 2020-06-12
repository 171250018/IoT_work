$(document).ready(function() {
    $("#add-author").click(function () {
        $("#author-list").append("<div class=\"row form-group author-item\">\n" +
            "                    <label class=\"nav flex-column justify-content-center col-lg-4 combinesearch-text text-right\">and</label>\n" +
            "                    <input class=\"form-control input-new\" name=\"author\">\n" +
            "                    <div class=\"col-lg-4 text-left\">\n" +
            "                        <button class=\"btn btn-primary remove-author\" type=\"button\" >x</button>\n" +
            "                    </div>\n" +
            "                </div>");
    });

    $("#author-list").on('click','.remove-author',function () {
        $(this).closest(".author-item").remove();
    })

    /*********************************************************************/
    $("#add-institute").click(function () {
        $("#institute-list").append("<div class=\"row form-group institute-item\">\n" +
            "                    <label class=\"nav flex-column justify-content-center col-lg-4 combinesearch-text text-right\">and</label>\n" +
            "                    <input class=\"form-control input-new\" name=\"institute\">\n" +
            "                    <div class=\"col-lg-4 text-left\">\n" +
            "                        <button class=\"btn btn-primary remove-institute\" type=\"button\" >x</button>\n" +
            "                    </div>\n" +
            "                </div>");
    });

    $("#institute-list").on('click','.remove-institute',function () {
        $(this).closest(".institute-item").remove();
    })

    /*********************************************************************/
    // $("#add-conference").click(function () {
    //     $("#conference-list").append("<div class=\"row form-group conference-item\">\n" +
    //         "                    <label class=\"nav flex-column justify-content-center col-lg-4 combinesearch-text text-right\">and</label>\n" +
    //         "                    <input class=\"btn btn-light btn-lg col-lg-4\">\n" +
    //         "                    <div class=\"col-lg-4 text-left\">\n" +
    //         "                        <button class=\"btn btn-primary remove-conference\" type=\"button\" >x</button>\n" +
    //         "                    </div>\n" +
    //         "                </div>");
    // });

    // $("#conference-list").on('click','.remove-conference',function () {
    //     $(this).closest(".conference-item").remove();
    // })
    /*********************************************************************/

    $("#add-keyword").click(function () {
        $("#keyword-list").append("<div class=\"row form-group keyword-item\">\n" +
            "                    <label class=\"nav flex-column justify-content-center col-lg-4 combinesearch-text text-right\">and</label>\n" +
            "                    <input class=\"form-control input-new\" name=\"keyword\">\n" +
            "                    <div class=\"col-lg-4 text-left\">\n" +
            "                        <button class=\"btn btn-primary remove-keyword\" type=\"button\" >x</button>\n" +
            "                    </div>\n" +
            "                </div>");
    });

    $("#keyword-list").on('click','.remove-keyword',function () {
        $(this).closest(".keyword-item").remove();
    })
});

function hideAlert() {
    var ui=document.getElementById("alert");
    ui.style.visibility= "hidden";
}
function advancedsearch() {
    var formData=getSearchForm();
    if(formData.startyear==""
        &&formData.endyear==""
        &&formData.pubtitle==""
        &&(formData.author==""||formData.author==";")
        &&(formData.institution==""||formData.institution==";")
        &&(formData.keyword==""||formData.keyword==";"))
    {
        // var ui=document.getElementById("alert");
        // ui.style.visibility= "visable";
        // return;
        $("#alert-content").append(
            " <div role=\"alert\" class=\"alert alert-dismissible alert-warning \"  id=\"alert\">\n" +
            "                <button type=\"button\" class=\"close\" data-dismiss=\"alert\" onclick=\"hideAlert()\">&times;</button>\n" +
            "                  <strong>Search conditions cannot be empty.</strong>\n" +
            "            </div>"
        )
        return
    }

    $.ajax({
        async : false,
        url : '/cbsearch',
        type : 'GET',
        contentType : 'application/json',
        dataType:'json',
        data : formData,
        success : function(res) {
            if (res.success){
                sessionStorage.clear();
                sessionStorage.setItem('page',1);
                sessionStorage.setItem('title',formData.title);
                sessionStorage.setItem('startyear',formData.startyear);
                sessionStorage.setItem('endyear',formData.endyear);
                sessionStorage.setItem('doi',formData.doi);
                sessionStorage.setItem('institution',formData.institution);
                sessionStorage.setItem('keyword',formData.keyword);
                sessionStorage.setItem('author',formData.author);
                sessionStorage.setItem('paperlist',JSON.stringify(res.content.resultlist));
                window.location.href='/cbresult'
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
    var authorlist = [];
    var institutelist = [];
    var keywordlist = [];

    var startyear=$('#start-year').val();
    var endyear=$('#end-year').val();
    var conference=$('#conference').val();

    $("input[name='author']").each(function(){
        authorlist.push($(this).val());
    });
    $("input[name='keyword']").each(function(){
        keywordlist.push($(this).val());
    });
    $("input[name='institute']").each(function(){
        institutelist.push($(this).val());
    });
    $("input[name='conference']").each(function(){
        conferencelist.push($(this).val());
    });

    var authorString=authorlist.join(';');
    var keywords=keywordlist.join(';');
    var instituteString=institutelist.join(';');

    return {
        title:'',
        startyear:startyear,
        endyear:endyear,
        doi:'',
        pubtitle:conference,
        author:authorString,
        keyword:keywords,
        institution:instituteString,
        page:1
    };
}
