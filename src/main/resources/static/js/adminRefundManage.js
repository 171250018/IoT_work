$(document).ready(function () {

    var refundStrategies = [];

    getRefundStrategies();
    //////////////假数据测试
    // refundStrategies = [{"id":1, "strategyName":"普通青年退票策略", "description":"开场至开场前一小时退票，退款50%"},
    //     {"id":2, "strategyName":"文艺青年退票策略", "description":"开场至开场前一小时退票，退款50%;开场前一小时至开场前两小时退票，退款70%;开场前两小时至开场前三小时退票，退款90%"},
    //     {"id":3, "strategyName":"二逼青年退票策略", "description":"开场至开场前一小时退票，退款50%;开场前一小时至开场前两小时退票，退款70%;开场前两小时至开场前三小时退票，退款90%"}];
    renderRefundStrategies();
    //////////////

    function getRefundStrategies() {
        getRequest(
            '/refundStrategy/get',
            function (res) {
                refundStrategies = res.content;
                renderRefundStrategies();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderRefundStrategies() {
        $(".content-item").empty();
        var domStr = "";
        refundStrategies.forEach(function (refundStrategy) {
            var stategyName = refundStrategy.strategyName;
            var description = refundStrategy.description.split("@")[0];
            console.log(refundStrategy);
            var descriptionTextList = description.split(";");//这个使用sj后端发来的语言描述

            domStr += '<div class="refund-container" >'
                +'<div class="refund-card card">';

            descriptionTextList.forEach(function (descriptionItem) {
               domStr +='<div class="refund-line">'
                +'<span class="refund-description">'+descriptionItem+'</span>'
                +'</div>'
            });

             domStr += '</div><div class="refund-coupon primary-bg">'
                +'<span class="title">'+stategyName+'</span>'
                +'</div>'
                +'</div>'
                +'<div class="refund-operations" style="text-align: right;margin: 0px 0px 20px 0px">'
                +'<button type="button" class="btn btn-primary edit-refund-btn" data-backdrop="static" data-toggle="modal" data-target="#refundEditModal"' +
                 ' id='+refundStrategy.id+' data-refundStrategy='+JSON.stringify(refundStrategy)+'>'
                +'<span>修改</span>'
                +'</button>'
                +'<button type="button" style="margin-left: 5px" class="btn btn-danger delete-refund-btn" data-backdrop="static" data-toggle="modal" data-target="#refundDeleteModal">'
                +'<span>删除</span>'
                +'</button>'
                +'</div>'
        });

        $(".content-item").append(domStr);
    }

    // function description2TextList(description){
    //     //0:10;24:50;48:60
    //     var textList=[];
    //     var descList = description.split(";");
    //     if(descList.length==1){}
    //     for(var i=0; i<descList.length-1; i++){
    //         var tempStr ="";
    //     }
    //
    //
    //     return textList;
    // }

    //临时保存用户输入的区间，存到当前页面，刷新后消失
    // $("#region-add-form-btn").click(function () {
    //     var form = {
    //         startHour: $("#refund-region-input-start").val(),
    //         endHour: $("#refund-region-input-end").val(),
    //         percentage: $("#refund-region-input-percentage").val()
    //     };
    //
    //     if (typeof (Storage) == "undefined") {
    //         alert("您的浏览器不支持Web Storage");
    //     } else {
    //         window.sessionStorage.setItem("开场前" + form.endHour + "-" + form.startHour + "小时", form.percentage);
    //         $("#regionAddModal").modal("hide");
    //     }
    // });


    $(document).on("click", ".refund-add-line-insert-btn", function () {
        var lineStr = "<div class='form-group row new-row'>"
            + '<div class="form-inline">'
            + '<div class="col-sm-10 col-sm-offset-2 input-group" style="display: flex;">'
            + '<input type="number" class="form-control form-add" placeholder="开始时间">'
            + '<input type="number" class="form-control form-add" style="margin-left: 10px" placeholder="退款比例">'
            + '<button type="button" class="btn btn-warning refund-add-line-insert-btn">'
            + '<span>插入</span>'
            + '</button>'
            + '<button type="button" class="btn btn-danger refund-add-line-delete-btn">'
            + '<span>删除</span>'
            + '</button>'
            + '</div>'
            + '</div>'
            + '</div>';
        $(this).parent().parent().parent().after(lineStr);
        console.log($(this).parent().parent().parent().parent());
    });

    $(document).on("click", ".refund-edit-line-insert-btn", function () {
        var lineStr = "<div class='form-group row new-row'>"
            + '<div class="form-inline">'
            + '<div class="col-sm-10 col-sm-offset-2 input-group" style="display: flex;">'
            + '<input type="number" class="form-control form-edit" placeholder="开始时间">'
            + '<input type="number" class="form-control form-edit" style="margin-left: 10px" placeholder="退款比例">'
            + '<button type="button" class="btn btn-warning refund-edit-line-insert-btn">'
            + '<span>插入</span>'
            + '</button>'
            + '<button type="button" class="btn btn-danger refund-edit-line-delete-btn">'
            + '<span>删除</span>'
            + '</button>'
            + '</div>'
            + '</div>'
            + '</div>';
        $(this).parent().parent().parent().after(lineStr);
        console.log($(this).parent().parent().parent().parent());
    });

    $(document).on("click", ".refund-add-line-delete-btn", function () {
        $(this).closest(".new-row").remove();
    });

    $(document).on("click", ".refund-edit-line-delete-btn", function () {
        $(this).closest(".new-row").remove();
    });


    /**
     * 不给管理员提示了，他想修改的话就自己重新输一遍
     *
     */
    $(document).on("click",".edit-refund-btn",function () {
        var refundStrategy = JSON.parse(this.dataset.refundstrategy);
        var rawDescription = refundStrategy.description.split("@")[1];
        var rawDescriptionList = rawDescription.split(";");

        //直接填充第一行数据
        $("#refundEditModal").find('input:eq(2)').val(rawDescriptionList[0].split(":")[0]);

        if(rawDescriptionList.length==0){
            alert("空退票策略");
        }else if(rawDescriptionList.length==1){
            //只需要一行时什么也不做

        }else{//rawDescriptionList.length>=2
            for(var i=1; i<rawDescriptionList.length; i++){
                //把不够的行显示出来
                $("#refundEditModal").find('button')[1].click()
            }
            //填充数据
            for(i=1; i<rawDescriptionList.length; i++){
                let time = rawDescriptionList[i].split(":")[0];
                let percent = rawDescriptionList[i].split(":")[1];

                $("#refundEditModal").find('input:eq('+(2*i+1)+')').val(time);
                $("#refundEditModal").find('input:eq('+(2*i+2)+')').val(percent);
            }

        }

        $("#refund-name-edit").val(refundStrategy.strategyName);
        $("#refund-name-edit").parent().parent().parent().parent().attr("refund-id",refundStrategy.id);
        console.log("修改的信息",refundStrategy);
    });


    /**
     * edit表单退出后的初始化
     */
    $("#refundEditModal").on('hidden.bs.modal',function(){
        $("#refundEditModal").find('.new-row').remove()
    });

    function clearRefundAddForm() {
        $("#refund-name-input").val("");
        var listToClear = $("#refundAddModal").find(".row");
        for(var i=2; i<listToClear.length; i++){
            $("#refundAddModal").find(".row")[i].remove();
        }
        if(listToClear.length===1){
            //如果只有一行，就模拟一下点击增加一行的按钮
            $("#refundAddModal").find(".row").find("button")[0].click()
        }
        //清理列表中的数据，其中第一项不清理

        $("#refundAddModal").find(".form-add:gt(0)").val("")
    }

    $(document).on("click",".delete-refund-btn",function () {

        var r=confirm("确认要删除该退票策略吗");

        console.log({id:Number(JSON.parse($(this).prev()[0].dataset.refundstrategy).id)});
        if (r) {
            deleteRequest(
                '/refund/strategy/delete',
                {id:Number(JSON.parse($(this).prev()[0].dataset.refundstrategy).id)},
                function (res) {
                    if(res.success){
                        getRefundStrategies()
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

    $("#refund-strategy-edit-form-btn").click(function () {

        var form = {
            id:Number($(this).parent().prev().attr("refund-id")),
            strategyName: $("#refund-name-edit").val(),
            description: ""
        };
        var formList = $(".row").find(".form-edit");
        var descriptionList = [];

        for (var i = 0; i < formList.length; i = i + 2) {
            var startTime = formList.eq(i).val();
            var percentage = formList.eq(i + 1).val();
            descriptionList.push(startTime.toString() + ":" + percentage.toString());
        }
        console.log();

        form.description = descriptionList.join(";");

        if (validateRefundForm(form)) {
            postRequest(
                '/refund/strategy/adjust',
                form,
                function (res) {
                    if (res.success) {
                        getRefundStrategies();
                        $("#refundEditModal").modal('hide');
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

    $("#refund-strategy-add-form-btn").click(function () {
        var form = {
            strategyName: $("#refund-name-input").val(),
            description: ""
        };
        var formList = $(".row").find(".form-add");
        var descriptionList = [];

        for (var i = 0; i < formList.length; i = i + 2) {
            var startTime = formList.eq(i).val();
            var percentage = formList.eq(i + 1).val();
            descriptionList.push(startTime.toString() + ":" + percentage.toString());
        }
        console.log(descriptionList);

        form.description = descriptionList.join(";")

        if (validateRefundForm(form)) {
            postRequest(
                '/refund/strategy/add',
                form,
                function (res) {
                    if (res.success) {
                        getRefundStrategies();
                        $("#refundAddModal").modal('hide');
                        clearRefundAddForm();
                        renderRefundStrategies();
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

    function clearRefundAddForm() {
        $("#refund-name-input").val("");
        var listToClear = $("#refundAddModal").find(".row");
        for(var i=2; i<listToClear.length; i++){
            $("#refundAddModal").find(".row")[i].remove();
        }
        if(listToClear.length===1){
            //如果只有一行，就模拟一下点击增加一行的按钮
            $("#refundAddModal").find(".row").find("button")[0].click()
        }
        //清理列表中的数据，其中第一项不清理

        $("#refundAddModal").find(".form-add:gt(0)").val("")
    }


    function validateRefundForm(data) {
        var isValidate = true;
        if (!data.strategyName) {
            isValidate = false;
            $("#refund-name-input").parent().parent().addClass("has-error");
        } else if ($("#refund-name-input").parent().parent().hasClass("has-error")) {
            $("#refund-name-input").parent().parent().removeClass("has-error");
        }

        var descriptionList = data.description.split(";");
        for (var i = 0; i < descriptionList.length; i++) {
            var startTime = descriptionList[i].split(":")[0];
            var percentage = descriptionList[i].split(":")[1];
            if (startTime.length == 0 || percentage.length == 0) {
                alert("区间选择中，每一项输入均不能为空");
                isValidate = false;
                break;
            }
        }
        if(isValidate){//检验输入时间点的顺序
            for(i=0; i<descriptionList.length-1; i++){
                var num1 = Number(descriptionList[i].split(":")[0]);
                var num2 = Number(descriptionList[i+1].split(":")[0]);
                if(num2<=num1){
                    alert("开始时间必须按照从小到大的顺序输入，且不能重复");
                    isValidate=false;
                    break;
                }
            }
        }

        if(isValidate){//检验第一个开始时间是否为0
            if(descriptionList[0].split(":")[0]!=="0"){
                alert("首个开始时间项必须为0");
                isValidate=false;
            }
        }
        return isValidate;
    }
    
});