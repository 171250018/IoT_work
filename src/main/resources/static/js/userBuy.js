$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
        //已修改  添加电影票内容
        $('#user-buy-ticket-list').empty();
        var movieDomStr = '';
        list.forEach(function (ticket) {
            var state;
            if(ticket.state==="1" || ticket.state==="2"){
                if(ticket.state==="2")
                    state="已失效";
                else if(ticket.state==="1")
                    state="已完成";

                movieDomStr +=
                    "<tr><td>"+ticket.schedule.movieName+"</td>"
                    +"<td>"+ticket.schedule.hallName+"</td>"
                    +"<td>"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座</td>"
                    +"<td>"+formatDateAndTime(new Date(ticket.schedule.startTime))+"</td>"
                    +"<td>"+formatDateAndTime(new Date(ticket.schedule.endTime))+"</td>"
                    +"<td>"+state+"</td></tr>";
            }
        });
        $('#user-buy-ticket-list').html(movieDomStr);
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