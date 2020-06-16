$(document).ready(function () {
    getRechargeLog();

    function getRechargeLog() {
        getRequest(
            '/vip/getHistory?userId=' + sessionStorage.getItem('id'),
            function (res) {
                renderRechargeLog(res.content);
            },
            function (error) {
                alert(error);
            });
    }
    function renderRechargeLog(list) {
        $('#user-recharge-log-list').empty();
        var rechargeDomStr = '';
        list.forEach(function (log) {
            rechargeDomStr +=
                "<tr><td>"+formatDateAndTime(new Date(log.rechargeDate))+"</td>"
                +"<td>"+log.cost+"</td>"
                +"<td>"+log.recharge+"</td>"
                +"<td>"+log.balance+"</td></tr>";
        });
        $('#user-recharge-log-list').html(rechargeDomStr);
    }

    function formatDateAndTime(date) {
        var res="";
        res=formatDate(date).trim()+" "+formatTime(date).trim()+":"+padLeftZero(date.getSeconds().toString().trim());

        return res;
    }
    function padLeftZero(str) {
        return ('00' + str).substr(str.length);
    }

});