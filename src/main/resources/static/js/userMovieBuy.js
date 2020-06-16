var selectedSeats = []
var scheduleId;
var order = {ticketId: [], couponId: 0};
var coupons = [];
var isVIP = false;
var useVIP = true;
var userId;
var discount;
var actualTotal;

$(document).ready(function () {
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);
    //已修改  获取用户ID
    userId=sessionStorage.getItem('id');
    // alert("scheduleId="+scheduleId+"userId="+userId);
    getInfo();

    function getInfo() {
        getRequest(
            '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,
            function (res) {
                if (res.success) {
                    renderSchedule(res.content.scheduleItem, res.content.seats);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});

function renderSchedule(schedule, seats) {
    $('#schedule-hall-name').text(schedule.hallName);
    $('#order-schedule-hall-name').text(schedule.hallName);
    $('#schedule-fare').text(schedule.fare.toFixed(2));
    $('#order-schedule-fare').text(schedule.fare.toFixed(2));
    $('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    $('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");

    var hallDomStr = "";
    var seat = "";
    for (var i = 0; i < seats.length; i++) {
        var temp = "";
        for (var j = 0; j < seats[i].length; j++) {
            var id = "seat" + i + j;

            if (seats[i][j] == 0) {
                // 未选
                temp += "<button class='cinema-hall-seat-choose' id='" + id + "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>";
            } else {
                // 已选中
                temp += "<button class='cinema-hall-seat-lock'></button>";
            }
        }
        seat += "<div>" + temp + "</div>";
    }
    var hallDom =
        "<div class='cinema-hall'>" +
        "<div>" +
        "<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
        "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";
    hallDomStr += hallDom;

    $('#hall-card').html(hallDomStr);
}

function seatClick(id, i, j) {
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");

        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    }

    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    let seatDetailStr = "";
    if (selectedSeats.length == 0) {
        seatDetailStr += "还未选择座位"
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        for (let seatLoc of selectedSeats) {
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}

function orderConfirmClick() {
    $('#seat-state').css("display", "none");
    $('#order-state').css("display", "");
    // TODO:这里是假数据，需要连接后端获取真数据，数据格式可以自行修改，但如果改了格式，别忘了修改renderOrder方法

    //已修改  添加锁座并生成订单
    var allSeats=[];
    for(let seat of selectedSeats){
        allSeats[allSeats.length]={columnIndex:seat[1],rowIndex:seat[0]};
    }
    var orderInfo={};
    postRequest(
        "/ticket/lockSeat",
        {userId:userId, scheduleId:scheduleId, seats:allSeats},
        function (res) {
            // alert("锁座成功");
            orderInfo={ticketVOList:res.content.ticketVOList, total:res.content.total, coupons:res.content.coupons, activities:res.content.activities};
            renderOrder(orderInfo);
        },
        function (error) {
            alert(error);
        }
    );

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

function checkPayAmount(){
    if(isVIP){
        $('#pay-amount').html("<div><b>实付金额：</b>" + getVIPAmount() + "</div>");
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
        $('#pay-amount').html("<div><b>金额：</b>" + actualTotal.toFixed(2) + "元</div>");
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}

function renderOrder(orderInfo) {
    var ticketStr = "<div>" + selectedSeats.length + "张</div>";
    for (let ticketInfo of orderInfo.ticketVOList) {
        ticketStr += "<div>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座</div>";
        order.ticketId.push(ticketInfo.id);
    }
    $('#order-tickets').html(ticketStr);

    var total = orderInfo.total.toFixed(2);
    $('#order-total').text(total);
    $('#order-footer-total').text("总金额： ¥" + total);


    var couponTicketStr = "<option>"+"不使用优惠券"+"</option>";
    if (orderInfo.coupons.length == 0) {
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + total);
        $('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
    } else {
        coupons = orderInfo.coupons;
        for (let coupon of coupons) {
            couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
        }
    }
    $('#order-coupons').html(couponTicketStr);
    changeCoupon(0);
}

function changeCoupon(couponIndex) {
    try{
        order.couponId = coupons[couponIndex-1].id;
        $('#order-discount').text("优惠金额： ¥" + coupons[couponIndex-1].discountAmount.toFixed(2));
        actualTotal = parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex-1].discountAmount);
        $('#order-actual-total').text(" ¥" + actualTotal);
        $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
    }catch (e) {
        actualTotal=parseFloat($('#order-total').text());
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + actualTotal);
        $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
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

// TODO:填空
//已修改  这是会员卡支付
function postVipPayRequest() {
    postRequest(
        "/ticket/vip/buy?ticketId="+order.ticketId+"&couponId="+order.couponId,
        null,
        function (res) {
            if(res.message==="余额不足"){
                alert("余额不足，请先充值后支付");
            }else{
                // alert("会员卡支付成功");
                $('#order-state').css("display", "none");
                $('#success-state').css("display", "");
                $('#buyModal').modal('hide')
            }
        },
        function (error) {
            alert(error);
        }
    );
}

//已修改  这是银行卡支付
function postPayRequest() {
    postRequest(
        "/ticket/buy?ticketId="+order.ticketId+"&couponId="+order.couponId,
        null,
        function (res) {
            // alert("银行卡支付成功");
            $('#order-state').css("display", "none");
            $('#success-state').css("display", "");
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