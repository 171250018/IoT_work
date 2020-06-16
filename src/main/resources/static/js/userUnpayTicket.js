var listInfo;
var selected=[];
var selectedSeq=[];
var sum=0.0;
var isVIP = false;
var useVIP = true;
var discount;
var actualTotal;

$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                var completeList = res.content.filter(function (ticket) {
                    return ticket.state==="0";
                });
                listInfo = completeList;
                renderTicketList();
            },
            function (error) {
                alert(error);
            });
    }

    function renderTicketList() {
        $('#user-unpay-ticket-list').empty();
        var movieDomStr = '';

        for(var i=0;i<listInfo.length;i++) {
            var ticket=listInfo[i];
            var state="未完成";
            var time=((new Date(ticket.time))-(new Date()))/1000/60+15;
            if(time>=3){
                time=Math.ceil(time)+"分钟";
            }else{
                time="即将失效";
            }
            movieDomStr +=
                "<tr>"
                +"<td>"+ticket.schedule.movieName+"</td>"
                +"<td>"+ticket.schedule.hallName+"</td>"
                +"<td>"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座</td>"
                +"<td>"+formatDateAndTime(new Date(ticket.schedule.startTime))+"</td>"
                +"<td>"+formatDateAndTime(new Date(ticket.schedule.endTime))+"</td>"
                +"<td>"+ticket.schedule.fare.toFixed(2)+"</td>"
                +"<td>"+state+"</td>"
                +"<td>"+time+"</td>"
                +"<td><input class='user-unpay-form-check-input' data-id='"+JSON.stringify(i)+"' type='checkbox' style='width: 25px;display: none'></td>" +
                +"</tr>";
        };
        $('#user-unpay-ticket-list').html(movieDomStr);
    }

    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            isVIP = res.success;
            useVIP = res.success;
            if (isVIP) {
                discount=res.content.vipStrategy.discount;
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.vipCard.balance.toFixed(2) + "元</div>");
            } else {
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });


    $(document).on('click','.user-unpay-form-check-input',function () {
        selected=[];
        selectedSeq=[];
        sum=0.0;
        var checkBox=$(':checkbox:checked');
        $(checkBox).each(function () {
            var point=JSON.parse($(this).attr('data-id'));
            selected.push(listInfo[point].id);
            selectedSeq.push(point);
            sum+=listInfo[point].schedule.fare;
        });
        $('#unpay-ticket-total').html(sum.toFixed(2))
        actualTotal=sum;

        if(selected.length==0){
            $('#user-unpay-confirm-button').attr("disabled","disabled");
            $('#user-unpay-delete-button').attr("disabled","disabled");
        }else{
            $('#user-unpay-confirm-button').removeAttr("disabled");
            $('#user-unpay-delete-button').removeAttr("disabled");
        }
    })
});

function formatDateAndTime(date) {
    var res="";
    res=formatDate(date).trim()+" "+formatTime(date).trim()+":"+padLeftZero(date.getSeconds().toString().trim());

    return res;
}

function padLeftZero(str) {
    return ('00' + str).substr(str.length);
}

function batchRefund() {
    if($('.user-unpay-form-check-input').css('display')==='none'){
        $('.user-unpay-form-check-input').show();
        $("#batch-refund-button").text("取消多选");
        $("#user-umpay-ticket-footer").show();
        $("#unpay-ticket-total-container").show();
    }else{
        $('.user-unpay-form-check-input').hide();
        $("#batch-refund-button").text("批量操作");
        $("#user-umpay-ticket-footer").hide();
        $("#unpay-ticket-total-container").hide();
    }

}

function getVIPAmount(){
    var s='';
    if(discount==1){
        s="<span>"+actualTotal.toFixed(2)+"元</span>";
    }else{
        s+="<span style='text-decoration: line-through red'>"+actualTotal.toFixed(2)+"元</span>"+
            "<span style='color: red;margin: 5px'>"+(actualTotal*discount).toFixed(2)+"元</span>";
    }
    return s;
}

function pay() {
    if(isVIP){
        $('#pay-amount').html("<div><b>实付金额：</b>" + getVIPAmount() + "</div>");
    }else{
        $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
    }
}

function switchPay(type) {
    useVIP = (type == 0);
    if (type == 0) {
        $('#pay-amount').html("<div><b>实付金额：</b>" + getVIPAmount() + "</div>");
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
    } else {
        $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}

function payConfirmClick() {
    if (useVIP) {
        postVipPayRequest();
    } else {
        if (validateForm()) {
            if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
                postPayRequest();
            } else {
                alert("银行卡号或密码错误");
            }
        }
    }
}

function postVipPayRequest() {
    postRequest(
        "/ticket/vip/buy?ticketId="+selected+"&couponId="+0,
        null,
        function (res) {
            if(res.message==="余额不足"){
                alert("余额不足，请先充值后支付");
            }else{
                // alert("会员卡支付成功");
                $('#unpay-ticket-container').css("display", "none");
                $('#unpay-finish-container').css("display", "");
                $('#buyModal').modal('hide')
            }
        },
        function (error) {
            alert(error);
        }
    );
}

function postPayRequest() {
    postRequest(
        "/ticket/buy?ticketId="+selected+"&couponId="+0,
        null,
        function (res) {
            // alert("银行卡支付成功");
            $('#unpay-ticket-container').css("display", "none");
            $('#unpay-finish-container').css("display", "");
            $('#buyModal').modal('hide')
        },
        function (error) {
            alert(error);
        }
    );
}

function validateForm() {
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}


function deleteTickets() {
    postRequest(
        "/ticket/delete",
        selected,
        function (res) {
            alert("删除成功");
            location.reload();
        },
        function (error) {
            alert(error);
        }
    )
}

function showConfirmDetail() {
    $("#user-unpay-ticket-delete-confrim").empty();
    var str='';
    for(var i=0;i<selectedSeq.length;i++){
        var ticket=listInfo[i];
        str+=
            "<tr>"
            +"<td>"+ticket.schedule.movieName+"</td>"
            +"<td>"+ticket.schedule.hallName+"</td>"
            +"<td>"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座</td>"
            +"<td>"+formatDateAndTime(new Date(ticket.schedule.startTime))+"</td>"
            +"<td>"+formatDateAndTime(new Date(ticket.schedule.endTime))+"</td>"
            +"</tr>";
    }
    $("#user-unpay-ticket-delete-confrim").html(str);
}