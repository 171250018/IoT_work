$(document).ready(function () {

    var roleList = [];
    getAllRoles();

    renderRolesList();

    function getAllRoles() {
        getRequest(
            '/admin/get/all',
            function (res) {
                console.log("all roles",res.content);
                roleList = res.content;
                renderRolesList();
            },
            function (error) {
                alert(error);
            });
    }

    function renderRolesList() {
        $("#super-admin-role-list").empty();
        var domStr='';

        roleList.forEach(function (role) {
            var character="";
            if(role.role==1){
                character="影院员工";
            }
            domStr += "<tr class='super-admin-role-list-item' data-toggle='modal' data-target='#roleEditModal' data-role_id="+role.id+">"
            +"<td>"+role.username+"</td>"
            +"<td>"+role.password+"</td>"
            +"<td>"+character+"</td>"
            +"<td>"+role.nickname+"</td></tr>";

        });
        $("#super-admin-role-list").append(domStr);

    }


    $("#role-form-btn").click(function () {
        var roleNum = -1;
        if($("#role-type-input").val()=="影院员工"){
            roleNum = 1;
        }
       var form={
           username: $("#role-name-input").val(),
           password: $("#role-password-input").val(),
           role: roleNum,
           nickname: $("#role-nickname-input").val()
       };

       if(validateRoleAddForm(form)){

           postRequest(
               '/admin/add',
               form,
               function (res) {
                   if (res.success) {
                       getAllRoles();
                       $("#roleModal").modal('hide');
                       clearRoleAddForm();
                   } else {
                       alert(res.message);
                   }
               },
               function (error) {
                   alert(JSON.stringify(error));
               }
           );
       }

    });

    function validateRoleAddForm(data) {
        var isValidate = true;
        if(!data.username){
            isValidate=false;
            $("#role-name-input").parent().addClass('has-error');
        }else if($("#role-name-input").parent().hasClass('has-error')){
            $("#role-name-input").parent().removeClass('has-error')
        }

        if(!data.password){
            isValidate=false;
            $("#role-password-input").parent().addClass('has-error');
        }else if($("#role-password-input").parent().hasClass('has-error')){
            $("#role-password-input").parent().removeClass('has-error')
        }

        if(data.role != "1"){
            isValidate = false;
            alert("只能添加影院员工");
        }

        if(!data.nickname){
            isValidate=false;
            $("#role-nickname-input").parent().addClass('has-error');
        }else if($("#role-nickname-input").parent().hasClass('has-error')){
            $("#role-nickname-input").parent().removeClass('has-error')
        }
        return isValidate;
    }

    function clearRoleAddForm(){
        $("#role-name-input").val("");
        $("#role-password-input").val("");
        $("#role-nickname-input").val("")
    }


    $(document).on('click','td',function () {
        var roleId = Number($(this).parent()[0].dataset.role_id);
        $("#roleEditModal").attr("role_id",roleId);
        var roleInfo = {};
        roleList.forEach(function (role) {
            if(roleId == role.id){
                roleInfo = role;
            }
        });

        $("#role-name-edit").val(roleInfo.username);
        $("#role-password-edit").val(roleInfo.password);
        $("#role-nickname-edit").val(roleInfo.nickname);

    });

    $("#role-form-edit-btn").click(function () {

        var roleNum = -1;
        if($("#role-type-edit").val()=="影院员工"){
            roleNum = 1;
        }
        var form={
            id: Number($("#roleEditModal").attr("role_id")),
            username: $("#role-name-edit").val(),
            password: $("#role-password-edit").val(),
            role: roleNum,
            nickname: $("#role-nickname-edit").val()
        };
        console.log("edit-form提交按钮",form);

        if(validateRoleEditForm(form)){
            postRequest(
                '/admin/update',
                form,
                function (res) {
                    if (res.success) {
                        getAllRoles();
                        $("#roleEditModal").modal('hide');
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

    function validateRoleEditForm(data) {
        var isValidate = true;
        if(!data.username){
            isValidate=false;
            $("#role-name-edit").parent().addClass('has-error');
        }else if($("#role-name-edit").parent().hasClass('has-error')){
            $("#role-name-edit").parent().removeClass('has-error')
        }

        if(!data.password){
            isValidate=false;
            $("#role-password-edit").parent().addClass('has-error');
        }else if($("#role-password-edit").parent().hasClass('has-error')){
            $("#role-password-edit").parent().removeClass('has-error')
        }
        if(data.role != "1"){
            isValidate = false;
            alert("只能修改影院员工");
        }
        if(!data.nickname){
            isValidate=false;
            $("#role-nickname-edit").parent().addClass('has-error');
        }else if($("#role-nickname-edit").parent().hasClass('has-error')){
            $("#role-nickname-edit").parent().removeClass('has-error')
        }
        return isValidate;
    }

    $(document).on("click","#role-delete-btn",function () {

        var r=confirm("确认要删除该员工的账号吗");

        if (r) {
            postRequest(
                '/admin/delete',
                Number($("#roleEditModal").attr("role_id")),
                function (res) {
                    if(res.success){
                        getAllRoles();
                        $("#roleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });
});