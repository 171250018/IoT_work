$(document).ready(function() {

    var canSeeDate = 0;

    getCanSeeDayNum();
    getCinemaHalls();

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = ""
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<div id='hall-"+hall.id+"' class='cinema-hall' data-hall='"+JSON.stringify(hall)+"'>" +
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +
                "<button type='button' class='btn btn-primary btn-xs' data-backdrop='static' data-toggle='modal' data-target='#hallEditModal' style='margin:0px 0px 5px 10px'>修改</button>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }

    $(document).on('click','.cinema-hall',function (e) {
        var hall = JSON.parse(this.dataset.hall);

        $("#hall-name-edit").val(hall.name);
        $("#hall-row-edit").val(hall.row);
        $("#hall-column-edit").val(hall.column);
        $('#hallEditModal')[0].dataset.hallId = hall.id;
        console.log(hall);
    });

    $("#hall-add-form-btn").click(function (){
        var form={
            name:$("#hall-name-input").val(),
            row:$("#hall-row-input").val(),
            column:$("#hall-column-input").val()
        };

        if(validateHallAddFrom(form)){
            postRequest(
                '/hall/add',
                form,
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        $("#hallAddModal").modal('hide');
                        clearHallAddForm();
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                });
        }

    });

    function clearHallAddForm(){
        $("#hall-name-input").val("");
        $("#hall-row-input").val("");
        $("#hall-column-input").val("");
    }

    $("#hall-edit-form-btn").click(function (){
        var form={
            id: Number($('#hallEditModal')[0].dataset.hallId),
            name:$("#hall-name-edit").val(),
            row:Number($("#hall-row-edit").val()),
            column:Number($("#hall-column-edit").val())
        };

        if(validateHallEditFrom(form)){
            postRequest(
                '/hall/update',
                form,
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        $("#hallEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                });
        }

    });

    function validateHallEditFrom(data){
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#hall-name-edit').parent().addClass('has-error');
        }else if($('#hall-name-edit').parent().hasClass('has-error')){
            $('#hall-name-edit').parent().removeClass('has-error');
        }

        if(!(/^[1-9]\d*$/.test(data.row))) {
            isValidate = false;
            $('#hall-row-edit').parent().addClass('has-error');
        }else if($('#hall-row-edit').parent().hasClass('has-error')){
            $('#hall-row-edit').parent().removeClass('has-error');
        }

        if(!(/^[1-9]\d*$/.test(data.column))) {
            isValidate = false;
            $('#hall-column-edit').parent().addClass('has-error');
        }else if($('#hall-column-edit').parent().hasClass('has-error')){
            $('#hall-column-edit').parent().removeClass('has-error');
        }
        return isValidate;
    }

    function validateHallAddFrom(data){
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#hall-name-input').parent().addClass('has-error');
        }else if($('#hall-name-input').parent().hasClass('has-error')){
            $('#hall-name-input').parent().removeClass('has-error');
        }

        if(!(/^[1-9]\d*$/.test(data.row))) {
            isValidate = false;
            $('#hall-row-input').parent().addClass('has-error');
        }else if($('#hall-row-input').parent().hasClass('has-error')){
            $('#hall-row-input').parent().removeClass('has-error');
        }

        if(!(/^[1-9]\d*$/.test(data.column))) {
            isValidate = false;
            $('#hall-column-input').parent().addClass('has-error');
        }else if($('#hall-column-input').parent().hasClass('has-error')){
            $('#hall-column-input').parent().removeClass('has-error');
        }
        return isValidate;
    }


    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        //已修改  这里在HTML输入类型已经限制为数字了，所以不用再验证了
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })
});