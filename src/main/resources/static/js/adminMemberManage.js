$(document).ready(function () {

    ///////////////测试用
    // var vipCards = [
    //     {"id":3, "name":"金卡", "minAmount": 5000, "reach":100, "present":20, "discount":"0.9"},
    //     {"id":2, "name":"银卡", "minAmount": 2000, "reach":100, "present":15, "discount":"0.95"},
    //     {"id":1, "name":"铜卡", "minAmount": 0, "reach":100, "present":10, "discount":"1"}
    // ];

    // renderVipCards();
    //////////////////
    getVipCards();
    function getVipCards() {
        getRequest(
            '/vip/card/get',
            function (res) {
                vipCards = res.content;
                renderVipCards();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderVipCards() {

        $(".content-item").empty();
        var domStr = "";
        vipCards.forEach(function (vipCard) {

            domStr += '<div class="vip-card-container" data-vipCard='+JSON.stringify(vipCard)+'>'
                +'<div class="vip-card card">'
                +'<div class="vip-line">'
                +'<span class="vip-card-description">最低总充值金额：'+vipCard.minAmount+'元</span>'
                +'</div>'
                +'<div class="vip-line">'
                +'<span class="vip-card-description">充值优惠：满'+vipCard.reach+'送'+vipCard.present+'</span>'
                +'</div>'
                +'<div class="vip-line">'
                +'<span class="vip-card-description">购票优惠：最终应付金额的'+vipCard.discount*100+'%</span>'
                +'</div>'
                +'</div>'
                +'<div class="vip-card-name-style primary-bg">'
                +'<span class="title">'+vipCard.name+'</span>'
                +'</div>'
                +'</div>'
                +'<div class="vip-card-operations" style="text-align: right;margin: 0px 0px 20px 0px">'
                +'<button type="button" class="btn btn-primary vip-card-edit-btn" data-backdrop="static" data-toggle="modal" data-target="#vipCardEditModal">'
                +'<span>修改</span>'
                +'</button>'
                +'<button type="button" class="btn btn-danger vip-card-delete-btn" data-backdrop="static" data-toggle="modal" style="margin-left: 5px">'
                +'<span>删除</span>'
                +'</button>'
                +'</div>';
        });
        $(".content-item").append(domStr);

    }

    $("#vip-card-add-form-btn").click(function () {

        var form = {
            name : $("#vip-card-name-input").val(),
            minAmount : $("#min-recharge-input").val(),
            reach : $("#reach-amount").val(),
            present : $("#present-amount").val(),
            discount : $("#ticket-discount-input").val()
        };

        //因为discount是一个字符串，所以对应的检验严格一点，方便后台转成double(js没有parseDouble())
        if(validateVipCardAddForm(form)){
            postRequest(
                '/vip/card/add',
                form,
                function (res) {
                    if (res.success) {
                        getVipCards();
                        $("#vipCardAddModal").modal('hide');
                        clearVipCardAddForm();
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

    function clearVipCardAddForm() {

        $("#vip-card-name-input").val("");
        $("#min-recharge-input").val("");
        $("#reach-amount").val("");
        $("#present-amount").val("");
        $("#ticket-discount-input").val("")

    }


    function validateVipCardAddForm(data){
        var isValidate = true;
        if(!data.name){
            isValidate = false;
            $("#vip-card-name-input").parent().addClass("has-error")
        }else if($("#vip-card-name-input").parent().hasClass("has-error")){
            $("#vip-card-name-input").parent().removeClass("has-error")
        }

        if((!data.minAmount) && (!(data.minAmount===0))){
            isValidate = false;
            $("#min-recharge-input").parent().addClass("has-error")
        }else if($("#min-recharge-input").parent().hasClass("has-error")){
            $("#min-recharge-input").parent().removeClass("has-error")
        }

        if((!data.reach)&&(!(data.reach===0))){
            isValidate = false;
            $("#reach-amount").parent().addClass("has-error")
        }else if($("#reach-amount").parent().hasClass("has-error")){
            $("#reach-amount").parent().removeClass("has-error")
        }

        if((!data.present)&&(!(data.present===0))){
            isValidate = false;
            $("#present-amount").parent().addClass("has-error")
        }else if($("#present-amount").parent().hasClass("has-error")){
            $("#present-amount").parent().removeClass("has-error")
        }

        if(!data.discount || !data.discount.match(/^\d*\.?\d{1,2}$/) || parseFloat(data.discount)>1 || parseFloat(data.discount)<0 ){
            isValidate = false;
            $("#ticket-discount-input").parent().addClass("has-error")
        }else if($("#ticket-discount-input").parent().hasClass("has-error")){
            $("#ticket-discount-input").parent().removeClass("has-error")
        }

        return isValidate;
    }


    $("#vip-card-edit-form-btn").click(function () {

        var form={
            id: Number($("#vip-card-edit-form-btn").parent().prev().attr("vip-card-id")),
            name : $("#vip-card-name-edit").val(),
            minAmount : Number($("#min-recharge-edit").val()),
            reach : Number($("#reach-amount-edit").val()),
            present : Number($("#present-amount-edit").val()),
            discount : $("#ticket-discount-edit").val()
        };
        console.log(form);

        if(validateVipCardEditForm(form)){
            postRequest(
                '/vip/card/edit',
                form,
                function (res) {
                    if (res.success) {
                        getVipCards();
                        $("#vipCardEditModal").modal('hide');
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

    function validateVipCardEditForm(data){
        var isValidate = true;
        if(!data.name){
            isValidate = false;
            $("#vip-card-name-edit").parent().addClass("has-error")
        }else if($("#vip-card-name-edit").parent().hasClass("has-error")){
            $("#vip-card-name-edit").parent().removeClass("has-error")
        }

        if((!data.minAmount) && (!(data.minAmount===0))){
            isValidate = false;
            $("#min-recharge-edit").parent().addClass("has-error")
        }else if($("#min-recharge-edit").parent().hasClass("has-error")){
            $("#min-recharge-edit").parent().removeClass("has-error")
        }

        if((!data.reach)&&(!(data.reach===0))){
            isValidate = false;
            $("#reach-amount-edit").parent().addClass("has-error")
        }else if($("#reach-amount-edit").parent().hasClass("has-error")){
            $("#reach-amount-edit").parent().removeClass("has-error")
        }

        if((!data.present)&&(!(data.present===0))){
            isValidate = false;
            $("#present-amount-edit").parent().addClass("has-error")
        }else if($("#present-amount-edit").parent().hasClass("has-error")){
            $("#present-amount-edit").parent().removeClass("has-error")
        }

        if(!data.discount || !data.discount.match(/^\d*\.?\d{1,2}$/) || parseFloat(data.discount)>1 || parseFloat(data.discount)<0 ){
            isValidate = false;
            $("#ticket-discount-edit").parent().addClass("has-error")
        }else if($("#ticket-discount-edit").parent().hasClass("has-error")){
            $("#ticket-discount-edit").parent().removeClass("has-error")
        }

        return isValidate;
    }

    $(document).on('click','.vip-card-edit-btn',function () {
        var vipCard = JSON.parse($(this).parent().prev()[0].dataset.vipcard);
        console.log(vipCard);

        $("#vip-card-name-edit").val(vipCard.name);
        $("#min-recharge-edit").val(Number(vipCard.minAmount));
        if(vipCard.minAmount==0){$("#min-recharge-edit").attr("readonly","readonly");}
        $("#reach-amount-edit").val(Number(vipCard.reach));
        $("#present-amount-edit").val(Number(vipCard.present));
        $("#ticket-discount-edit").val(Number(vipCard.discount));
        $("#ticket-discount-edit").parent().parent().parent().parent().attr("vip-card-id",Number(vipCard.id));
    });

    $(document).on("click",".vip-card-delete-btn",function () {

        var r=confirm("确认要删除该vip卡吗");

        console.log({id:Number(JSON.parse($(this).parent().prev()[0].dataset.vipcard).id)});
        if (r) {
            deleteRequest(
                '/vip/card/delete',
                {id:Number(JSON.parse($(this).parent().prev()[0].dataset.vipcard).id)},
                function (res) {
                    if(res.success){
                        getVipCards();
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