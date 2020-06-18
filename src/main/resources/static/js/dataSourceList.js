$(document).ready(function () {
    var page=1;
    getDataSourceList(page);
    getUnUsedProduct();

    function getDataSourceList(page) {
        getRequest(
            '/dataSource/getAllDataSource?page='+page,
            function (res) {
                renderDataSourceList(res.content);
            },
            function (error) {
                alert(error);
            });
    }
    function renderDataSourceList(list) {
        $('#data-source-list').empty();
        var dataSourceStr='';
        //var rechargeDomStr = '';
        list.forEach(function (log) {
            if(log.month<12){
                dataSourceStr +=
                     "<tr><th>"+log.pname+"</th>"
                     +"<th>"+log.month+"个月"+"</th>"
                     +"<th>"+log.startTime+"</th>"
                     +"<th>"+"<a class='link-func'>时序透视</a> | <a class='link-func'>可视化分析</a> | <a class='link-func'>SQL分析</a> | <a class='link-func'>修改周期</a> | <a class='link-func'>删除</a>"+"</th></tr>";
            }
            else{
                dataSourceStr +=
                     "<tr><th>"+log.pname+"</th>"
                     +"<th>"+log.month/12+"年"+"</th>"
                     +"<th>"+log.startTime+"</th>"
                     +"<th>"+"<a class='link-func'>时序透视</a> | <a class='link-func'>可视化分析</a> | <a class='link-func'>SQL分析</a> | <a class='link-func'>修改周期</a> | <a class='link-func'>删除</a>"+"</th></tr>";
            }
        });
        $('#data-source-list').html(dataSourceStr);
    }

    $("#next-page-btn").click(function () {
            page=page+1;
            getDataSourceList(page);
        });
    $("#last-page-btn").click(function () {
                if(page>1){
                    page=page-1;
                    getDataSourceList(page);
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

    $("#datasource-add-form-btn").click(function (){
            var form={
                pid:document.getElementById('product-sec').value,
                month:document.getElementById('month-sec').value,
            };
            if(validateDataSourceFrom(form)){
                postRequest(
                    '/dataSource/addDataSource',
                    form,
                    function (res) {
                        if(res.success){
                            page=1;
                            getDataSourceList(page);
                            $("#datasourceModal").modal('hide');
                            clearDataSourceAddForm();
                        } else{
                            alert(res.message);
                        }
                    },
                    function (error) {
                        alert(JSON.stringify(error));
                    });
            }
        });

    function validateDataSourceFrom(data){
            var isValidate = true;
            if(document.getElementById('product-sec').value==0){
                isValidate=false;
            }
            return isValidate;
        }

    function clearDataSourceAddForm(){
            $("#product-sec").val("");
            $("#month-sec").val("");
        }

    function getUnUsedProduct() {
        getRequest(
            '/product/unUsedProduct',
            function (res) {
                renderUnUsedProductList(res.content);
            },
            function (error) {
                alert(error);
            });
    }
    function renderUnUsedProductList(list) {
        $('#product-sec').empty();
        var productStr='<option value="0">请选择产品</option>';
        //var rechargeDomStr = '';
        list.forEach(function (log) {
            productStr +=
                "<option value='"+log.pid+"'>"+log.pname+"</option>";
        });
        $('#product-sec').html(productStr);
    }

});