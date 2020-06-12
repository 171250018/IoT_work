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
                sessionStorage.clear();
                sessionStorage.setItem('page',1);
                sessionStorage.setItem('type',formData.type);
                sessionStorage.setItem('content',formData.content);
                sessionStorage.setItem('paperlist',JSON.stringify(res.content.resultlist));
                window.location.href='/result'
            }
            else{
                alert('Your search did not match any papers');
            }
        },
        error:function(error){
            alert(JSON.stringify(error));
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
