var did;
var pname;

$(document).ready(function () {
    did = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    pname=window.location.href.split('?')[1].split('&')[1].split('=')[1];

    $("#big-title").text(pname+"--时序透视");



});