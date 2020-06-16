$(document).ready(function () {

    $("#login-btn").click(function () {
        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            return;
        }

        postRequest(
            '/login',
            formData,
            function (res) {
                if (res.success) {
                    var accountContent=res.content;
                    sessionStorage.setItem('username', accountContent.username);
                    sessionStorage.setItem('id', accountContent.id);
                    sessionStorage.setItem('nickname',accountContent.nickname);
                    if(accountContent.password!=formData.password){
                        alert("用户名与密码不匹配");
                    }

                    if(accountContent.role == "1"){//员工
                        sessionStorage.setItem('role', 'admin');
                        window.location.href = "/admin/movie/manage"
                    } else if(accountContent.role == "2"){//经理，我翻译成了超管
                        sessionStorage.setItem('role', 'superAdmin');
                        window.location.href = "/super/admin/role/manage"
                    }else {//accountContent.role == "0" 用户
                        sessionStorage.setItem('role', 'user');
                        window.location.href = "/user/home";
                    }
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    });

    function getLoginForm() {
        return {
            username: $('#index-name').val(),
            password: $('#index-password').val()
        };
    }

    function validateLoginForm(data) {
        var isValidate = true;
        if (!data.username) {
            isValidate = false;
            $('#index-name').parent('.input-group').addClass('has-error');
            $('#index-name-error').css("visibility", "visible");
        }
        if (!data.password) {
            isValidate = false;
            $('#index-password').parent('.input-group').addClass('has-error');
            $('#index-password-error').css("visibility", "visible");
        }
        return isValidate;
    }
});

function change(){
    $("#login-title").toggle();
}