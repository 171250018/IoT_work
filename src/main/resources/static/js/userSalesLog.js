var list=[];

$(document).ready(function () {

    getHistory();

    function getHistory(){
        getRequest(
            '/ticket/get/history?userId='+sessionStorage.getItem('id'),
            function (res) {
                list=res.content;
                renderHistoryList();
            },
            function (error) {
                alert(error);
            });
    }

    function renderHistoryList() {
        $('#user-sales-log-list').empty();
        var str='';
        for(var i=0;i<list.length;i++){
            log=list[i];
            var method='';
            var type='';
            var sum=log.balance;
            if(log.payMethod===0){
                sum="*";
                method="银行卡";
            }else{
                method="会员卡";
            }
            if(log.state===1){
                type="消费"
            }else{
                type="退款";
            }

            str +=
                "<tr data-toggle='modal' data-target='#myModal' id='listDetail' onclick='listClick("+i+")'><td>"+formatDateAndTime(new Date(log.consumeDate))+"</td>"
                +"<td>"+type+"</td>"
                +"<td>"+method+"</td>"
                +"<td>"+log.cost+"</td>"
                +"<td>"+sum+"</td>"
                +"<td>"+log.ticketWithScheduleVOList.length+"</td></tr>";

        };
        $('#user-sales-log-list').html(str);
    }




});

function formatDateAndTime(date) {
    var res="";
    res=formatDate(date).trim()+" "+formatTime(date).trim()+":"+padLeftZero(date.getSeconds().toString().trim());

    return res;
}
function padLeftZero(str) {
    return ('00' + str).substr(str.length);
}

function listClick(i) {
    log=list[i];
    var method='';
    var type='';
    var sum=log.balance;
    if(log.payMethod===0){
        sum="*";
        method="银行卡";
    }else{
        method="会员卡";
    }
    if(log.state===1){
        type="消费"
    }else{
        type="退款";
    }
    var str='';
    str+="<ul class='list-group'>" +
        "  <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"完成时间"+"</span><span class='user-sales-log-detail-content'>"+new Date(log.consumeDate).toLocaleDateString()+"</span></li>" +
        "  <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"订单类型"+"</span><span class='user-sales-log-detail-content'>"+type+"</span></li>" +
        "  <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"付款方式"+"</span><span class='user-sales-log-detail-content'>"+method+"</span></li>" +
        "  <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"金额变动"+"</span><span class='user-sales-log-detail-content'>"+log.cost+"</span></li>" +
        "  <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"票数"+"</span><span class='user-sales-log-detail-content'>"+log.ticketWithScheduleVOList.length+"</span></li>" +
        "</ul>";
    for(var i=0;i<log.ticketWithScheduleVOList.length;i++){
        var ticket=log.ticketWithScheduleVOList[i];
        var state;
        switch (ticket.state) {
            case "0":
                state="未完成";
                break;
            case "1":
                state="已完成";
                break;
            case "2":
                state="已失效";
                break;
            case "3":
                state="已退票";
                break;
        }
        var ticketStr='';
        ticketStr+=
            "<div class='ticket-detail-container'>" +
            "  <button style='margin: 5px' type='button' class='btn btn-info' data-toggle='collapse' data-target='#demo"+i+"'>第"+(i+1)+"张票</button>" +
            "  <div id='demo"+i+"' class='collapse'>" +
            "    <ul class='list-group'>" +
            "      <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"电影名称"+"</span><span class='user-sales-log-detail-content'>"+ticket.schedule.movieName+"</span></li>" +
            "      <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"影厅名"+"</span><span class='user-sales-log-detail-content'>"+ticket.schedule.hallName+"</span></li>" +
            "      <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"座位"+"</span><span class='user-sales-log-detail-content'>"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座"+"</span></li>" +
            "      <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"放映时间"+"</span><span class='user-sales-log-detail-content'>"+formatDateAndTime(new Date(ticket.schedule.startTime))+"</span></li>" +
            "      <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"预计结束时间"+"</span><span class='user-sales-log-detail-content'>"+formatDateAndTime(new Date(ticket.schedule.endTime))+"</span></li>" +
            "      <li class='list-group-item'><span class='user-sales-log-detail-title'>"+"实时状态"+"</span><span class='user-sales-log-detail-content'>"+state+"</span></li>" +
            "    </ul>"+
            "  </div>" +
            "</div>";
        str+=ticketStr;
    }
    $('#user-log-list-detail').html(str);
}