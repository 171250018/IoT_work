function uploadcsv() {
    // $("#form").ajaxForm(function(data){
    //
    //     console.log(data)
    //
    // });
    var fileName=$("#upload-file").val();

    $('#form').submit();

    var returnData;
    // var returnData=$('#submitcsv')["0"].contentDocument.body.textContent;
    returnData=$(window.frames['submitcsv'].document.body);
    var retMsg=$("#returnMsg");
    console.log(retMsg)
    // alert(retMsg)
    //  var retMsg=returnData["0"]
    // if(returnData==""){
    //     alert("uploaded failed")
    // }else{
    //
    //     if(returnData["success"]=="true"){
    //         alert("uploaded successfully")
    //     }else{
    //         alert("?????")
    //         alert(returnData.message)
    //     }
    // }

    console.log(returnData)

}

// $(function () {
//     // $('#form').ajaxForm(function (data) {
//     //     console.log(JSON.stringify(data))
//     //
//     // })
//
//     // var formData;
//     // alert("????????")
//     // $("#submitcsv").load(function () {
//     //     formData=$(this)[0].contentDocument.body.textContent;
//     //     alert(formData)
//     // })
// })

// $('#form').on('submit',function () {
//
// })
// function preventSubmit(event) {
//     csvPost()
//     this.preventDefault()
// }
//
// function csvPost() {
//     $.ajax({
//         type: "post",
//         url: "/uploadcsv",
//         data: $('#form').serialize(),
//     }).success(function(message) {
//         console.log(message)
//     }).fail(function(err){
//         console.log(err)
//     })
// }
