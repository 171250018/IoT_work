var selected=[];

$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                var refundList = res.content.filter(function (ticket) {
                    return (ticket.state==="1")&&((new Date())<new Date(ticket.schedule.startTime));
                });
                listInfo = refundList;
                renderTicketList();
            },
            function (error) {
                alert(error);
            });
    }

    function renderTicketList() {
        $('#user-refund-ticket-list').empty();
        var str = '';

        for(var i=0;i<listInfo.length;i++) {
            var ticket=listInfo[i];
            var state="已完成，可退票";
            str +=
                "<tr>"
                +"<td>"+ticket.schedule.movieName+"</td>"
                +"<td>"+ticket.schedule.hallName+"</td>"
                +"<td>"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座</td>"
                +"<td>"+formatDateAndTime(new Date(ticket.schedule.startTime))+"</td>"
                +"<td>"+formatDateAndTime(new Date(ticket.schedule.endTime))+"</td>"
                +"<td>"+state+"</td>"
                +"<td><input class='user-refund-form-check-input' data-id='"+JSON.stringify(i)+"' type='checkbox' style='width: 25px;display: none'></td>" +
                +"</tr>";
        };
        $('#user-refund-ticket-list').html(str);
    }

    $(document).on('click','.user-refund-form-check-input',function () {
        selected=[];
        var checkBox=$(':checkbox:checked');
        $(checkBox).each(function () {
            var point=JSON.parse($(this).attr('data-id'));
            selected.push(listInfo[point].id);
        });

        if(selected.length==0){
            $('#refund-check-button').attr("disabled","disabled");
        }else{
            $('#refund-check-button').removeAttr("disabled");
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
    if($('.user-refund-form-check-input').css('display')==='none'){
        $('.user-refund-form-check-input').show();
        $("#batch-refund-button").text("取消多选");
        $("#refund-check-button").show();
    }else{
        $('.user-refund-form-check-input').hide();
        $("#batch-refund-button").text("批量操作");
        $("#refund-check-button").hide();
    }
}

function showRefundDetail() {
    postRequest(
        '/ticket/cancel',
        selected,
        function (res) {
            renderRefundDetail(res.content);
        },
        function (error) {
            alert(error);
        });
}

function renderRefundDetail(data) {
    $('#user-refund-ticket-detail-list').empty();
    $('#user-refund-ticket-detail-total').empty();
    var str = '';
    var listInfo=data.refundItemInfoVOS;
    for(var i=0;i<listInfo.length;i++) {
        var ticket=listInfo[i];
        var method;
        if(ticket.payMethod===0){
            method="银行卡";
        }else{
            method="会员卡";
        }
        str +=
            "<tr>"
            +"<td>"+ticket.ticketWithScheduleVO.schedule.movieName+"</td>"
            +"<td>"+ticket.ticketWithScheduleVO.schedule.hallName+"</td>"
            +"<td>"+(ticket.ticketWithScheduleVO.rowIndex+1)+"排"+(ticket.ticketWithScheduleVO.columnIndex+1)+"座</td>"
            +"<td>"+formatDateAndTime(new Date(ticket.ticketWithScheduleVO.schedule.startTime))+"场</td>"
            +"<td>"+ticket.cost.toFixed(2)+"</td>"
            +"<td>"+ticket.refundStrategy+"%</td>"
            +"<td>"+ticket.refundAmount.toFixed(2)+"</td>"
            +"<td>"+method+"</td>"
            +"</tr>";
    };
    var total="<div style='margin: 5px;'>银行卡退款总计："+data.totalForCredit.toFixed(2)+"</div>"
                +"<div style='margin: 5px'>会员卡退款总结："+data.totalForVIP.toFixed(2)+"</div>"
                +"<div style='font-size: larger;margin: 5px'>总退款："+data.total+"</div>";
    $('#user-refund-ticket-detail-list').html(str);
    $('#user-refund-ticket-detail-total').html(total);
}

function confrimRefund() {
    postRequest(
        '/ticket/confirm',
        null,
        function (res) {
            alert("退票成功");
            location.reload();
        },
        function (error) {
            alert(error);
        });
}