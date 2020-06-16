$(document).ready(function() {

    getBoxOffice();
    getPolularMovie(7, 5);
    getAllMovies();
    getUserLikeMovies();

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                renderMovie(movieList)
            },
            function (error) {
                alert(error);
            }
        );
    }

    function renderMovie(list){
        $('#user-home-hotshowing').empty();
        var str = '';
        list.forEach(function (item) {
            var hyperlink="https://baike.baidu.com/item/"+item.name;
            str +=
                "<li class='movie-item'>" +
                "<a href="+hyperlink+" target='_blank'>" +
                "<img class='movie-img' src="+item.posterUrl+">" +
                "</a>" +
                "<div class='movie-name'>"+item.name+"</div>" +
                "</li>"
        });
        $('#user-home-hotshowing').html(str);
    }

    function getBoxOffice() {

        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                var listData = data.map(function (item) {
                    return {name:item.name,  boxOffice:item.boxOffice}
                });
                renderBoxOffice(listData.slice(0,5));
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function renderBoxOffice(list){
        $('#user-home-boxoffice').empty();
        var str = "<div class='statistic-item' style='margin: 10px auto;font-weight: bold;font-size: 16px;'>票房前五名"+"(截止至"+new Date().toLocaleDateString()+")</div>";
        list.forEach(function (item) {
            str +=
                "<div class='statistic-item'>" +
                "<span>"+item.name+"</span>" +
                "<span class='error-text'>"+item.boxOffice+"</span>" +
                "</div>"
        });
        $('#user-home-boxoffice').html(str);
    }

    function getPolularMovie(days,movieNum) {
        getRequest(
            '/statistics/popular/movie?days='+days+'&movieNum='+movieNum,
            function (res) {
                var data = res.content || [];
                var listData = data.map(function (item) {
                    return {name:item.name,  boxOffice:item.boxOffice}
                });
                renderPolularMovie(listData);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function renderPolularMovie(list) {
        $('#user-home-popular').empty();
        var str = "<div class='statistic-item'  style='margin: 10px auto;font-weight: bold;font-size: 16px;'>" + "(近一周热榜前五)</div>";
        list.forEach(function (item) {
            str +=
                "<div class='statistic-item'>" +
                "<span>" + item.name + "</span>" +
                "<span class='error-text'>" + item.boxOffice + "</span>" +
                "</div>"
        });
        $('#user-home-popular').html(str);
    }
    
    function getUserLikeMovies() {
        getRequest(
            '/movie/like/user/get?userId='+sessionStorage.getItem('id'),
            function (res) {
                var data = res.content;
                renderUserLikeMovie(data);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function renderUserLikeMovie(list) {
        $('#user-home-movielike').empty();
        var str = '';
        list.forEach(function (item) {
            var hyperlink="https://baike.baidu.com/item/"+item.name;
            str +=
                "<li class='movie-item'>" +
                "<a href="+hyperlink+" target='_blank'>" +
                "<img class='movie-img' src="+item.posterUrl+">" +
                "</a>" +
                "<div class='movie-name'>" + item.name + "</div>" +
                "<div class='movie-status'style='color: "+(item.status ?"red":"green")+"'>" + (item.status ? '已下架' : (new Date(item.startDate)>=new Date()?'未上映':'热映中')) + "</div>" +
                "</li>"
        });
        $('#user-home-movielike').html(str);
    }
});

function isValidNickname(data){
    var isValidate=true;
    if(!data){
        isValidate=false;
        $("#new-nickname-input").parent().parent().addClass('has-error');
    }else if($('#new-nickname-input').parent().parent().hasClass('has-error')){
        $('#new-nickname-input').parent().parent().removeClass('has-error');
    }
    return isValidate;
}

function alterNickname() {
    var nickname=$("#new-nickname-input").val();
    if(isValidNickname(nickname)){
        $("#nicknameModal").modal("hide");
        $("#new-nickname-input").val("");
        postRequest(
            '/user/update/nickname',
            {id:sessionStorage.getItem('id'), nickname:nickname},
            function (res) {
                if (res.success) {
                    alert("昵称更新成功");
                    sessionStorage.setItem('nickname',nickname);
                    location.reload();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }
}

function isValidPassword(data){
    var isValidate=true;
    if(!data.origin){
        isValidate=false;
        $("#new-password-origin-input").parent().parent().addClass('has-error');
    }else if($('#new-password-origin-input').parent().parent().hasClass('has-error')){
        $('#new-password-origin-input').parent().parent().removeClass('has-error');
    }
    if(!data.current){
        isValidate=false;
        $("#new-password-current-input").parent().parent().addClass('has-error');
    }else if($('#new-password-current-input').parent().parent().hasClass('has-error')){
        $('#new-password-current-input').parent().parent().removeClass('has-error');
    }
    if(!data.check){
        isValidate=false;
        $("#new-password-check-input").parent().parent().addClass('has-error');
    }else if($('#new-password-check-input').parent().parent().hasClass('has-error')){
        $('#new-password-check-input').parent().parent().removeClass('has-error');
    }
    if(!isValidate){
        $("#new-password-prompt").html("请填写完整")
    }
    if(isValidate){
        if(data.current.length<6 || data.current.length>12){
            isValidate=false;
            $("#new-password-prompt").html("密码长度应该在6-12位之间");
        }else if(!(data.current===data.check)){
            isValidate=false;
            $("#new-password-prompt").html("两次输入的新密码不一致");
        }
        if(!isValidate){
            $("#new-password-current-input").parent().parent().addClass('has-error');
            $("#new-password-check-input").parent().parent().addClass('has-error');
        }else{
            if($('#new-password-current-input').parent().parent().hasClass('has-error')){
                $('#new-password-current-input').parent().parent().removeClass('has-error');
            }
            if($('#new-password-check-input').parent().parent().hasClass('has-error')){
                $('#new-password-check-input').parent().parent().removeClass('has-error');
            }
        }
    }
    return isValidate;
}

function alterPassword(){
    var data={
        origin:$("#new-password-origin-input").val(),
        current:$("#new-password-current-input").val(),
        check:$("#new-password-check-input").val()
    }
    if(isValidPassword(data)){
        getRequest(
            "/user/get/user?userId="+sessionStorage.getItem('id'),
            function (res) {
                if (res.success) {
                    if(!(res.content.password===data.origin)){
                        alert("原密码验证未通过，修改密码失败");
                        $("#new-password-prompt").html("原密码验证未通过");
                        $("#new-password-origin-input").parent().parent().addClass('has-error');
                    }else{
                        if($('#new-password-origin-input').parent().parent().hasClass('has-error')){
                            $('#new-password-origin-input').parent().parent().removeClass('has-error');
                        }
                        updatePassword(data.current);
                        $("#passwordModal").modal("hide");
                        $("#new-password-origin-input").val("");
                        $("#new-password-current-input").val("");
                        $("#new-password-check-input").val("");
                        $("#new-password-prompt").empty();
                    }
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }
}

function updatePassword(newPassword) {
    postRequest(
        '/user/update/password',
        {id:sessionStorage.getItem('id'), password:newPassword},
        function (res) {
            if (res.success) {
                alert("密码更新成功");
            } else {
                alert(res.message);
            }
        },
        function (error) {
            alert(error);
        });
}
