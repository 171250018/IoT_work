$(document).ready(function () {
    $("#author-table").css("display","none");
    $("#ins-table").css("display","");
});

function switchclick(index) {
if(index===0){
    //choose author
    $('#button-ins').addClass("active");
    $('#button-author').removeClass("active");
    $("#author-table").css("display","none");
    $("#ins-table").css("display","");
}
else if(index===1){
    //choose ins
    $('#button-ins').removeClass("active");
    $('#button-author').addClass("active");
    $("#author-table").css("display","");
    $("#ins-table").css("display","none");
}
}
