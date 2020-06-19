$(document).ready(function () {
    var page=1;
    getDataSourceList(page);
    getUnUsedProduct();
    var searchcont='';
    var searchtime=0;

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
        if(list==null){
            return false;
        }
        list.forEach(function (log) {
            if(log.month<12){
                dataSourceStr +=
                     "<tr data-datasource="+JSON.stringify(log)+">"
                     +"<th>"+log.pname+"</th>"
                     +"<th>"+log.month+"个月"+"</th>"
                     +"<th>"+log.startTime+"</th>"
                     +"<th>"+"<a class='link-func' id='time-act' href='/datasource/time?did="+log.did+"&pname="+log.pname+"'>时序透视</a> | <a class='link-func' id='show-act'>可视化分析</a> | <a class='link-func' id='sql-act' href='/admin/movie/manage'>SQL分析</a> | <a class='link-func adjust-act'  data-backdrop='static' data-toggle='modal'  data-target='#datasourceModifyModal'>修改周期</a> | <a class='link-func delete-act'  data-backdrop='static' data-toggle='modal'  data-target='#datasourceOffBatchModal'>删除</a>"+"</th></tr>";
            }
            else{
                dataSourceStr +=
                     "<tr data-datasource="+JSON.stringify(log)+">"
                     +"<th>"+log.pname+"</th>"
                     +"<th>"+log.month/12+"年"+"</th>"
                     +"<th>"+log.startTime+"</th>"
                     +"<th>"+"<a class='link-func' id='time-act' href='/datasource/time?did="+log.did+"&pname="+log.pname+"'>时序透视</a> | <a class='link-func' id='show-act'>可视化分析</a> | <a class='link-func' id='sql-act' href='/admin/movie/manage'>SQL分析</a> | <a class='link-func adjust-act'  data-backdrop='static' data-toggle='modal' data-target='#datasourceModifyModal'>修改周期</a> | <a class='link-func delete-act'  data-backdrop='static' data-toggle='modal'  data-target='#datasourceOffBatchModal'>删除</a>"+"</th></tr>";
            }
        });
        $('#data-source-list').html(dataSourceStr);
    }

    $("#next-page-btn").click(function () {
            page=page+1;
            if(searchcont!=''||searchtime!=0){
                getSearchList(searchcont,searchtime,page);
            }
            else{
                getDataSourceList(page);
            }
        });
    $("#last-page-btn").click(function () {
            if(page>1){
                page=page-1;
                if(searchcont!=''||searchtime!=0){
                    getSearchList(searchcont,searchtime,page);
                }
                else{
                    getDataSourceList(page);
                }
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

    $(".adjust-act").click(function (){

            alert("chuxianlema")
            var datasource=JSON.parse($(this).parent().parent().dataset.datasource);
            console.log(datasource);

            $("#product-res").val(datasource.pname);
            $("#product-res").parent().parent().parent().parent().attr("datasource-id",Number(datasource.id));
        });

    $(document).on('click','.adjust-act',function () {
            var datasource=JSON.parse($(this).parent().parent()[0].dataset.datasource);
            console.log(datasource);

            $("#product-res").val(datasource.pname);
            $("#product-res").text(datasource.pname);
            $("#product-res").parent().parent().parent().parent().attr("datasource-id",Number(datasource.did));
        });

    $("#datasource-edit-form-btn").click(function (){
        var form={
            did: Number($('#datasource-edit-form-btn').parent().prev().attr("datasource-id")),
            month: $("#month-res").val(),
        };

        if(validateDataSourceEditFrom(form)){
            postRequest(
                '/dataSource/updateDataSource',
                form,
                function (res) {
                    if(res.success){
                        page=1;
                        getDataSourceList(page);
                        $("#datasourceModifyModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                });
        }

    });

    function validateDataSourceEditFrom(data){
        var isValidate = true;
        if(document.getElementById('month-res').value==0){
            isValidate=false;
        }
        return isValidate;
    }

    $(document).on('click','.delete-act',function () {
        var datasource=JSON.parse($(this).parent().parent()[0].dataset.datasource);
        console.log(datasource);

        $("#product-del").val(datasource.pname);
        $("#product-del").text(datasource.pname);
        $("#month-del").val(datasource.month);
        if(datasource.month<12){
            $("#month-del").text(datasource.month+"个月");
        }
        else{
            $("#month-del").text(datasource.month/12+"年");
        }
        $("#product-del").parent().parent().parent().parent().attr("datasource-id",Number(datasource.did));
    });

    $("#datasource-off-batch-btn").click(function (){
        var did=Number($('#datasource-off-batch-btn').parent().prev().attr("datasource-id"));
        postRequest(
            '/dataSource/deleteDataSource?did='+did,
            null,
            function (res) {
                if(res.success){
                    page=1;
                    getDataSourceList(page);
                    $("#datasourceOffBatchModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            });

    });

    function getSearchList(pname,time,page) {
        getRequest(
            '/dataSource/searchDataSource?pname='+pname+'&time='+time+'&page='+page,
            function (res) {
                renderDataSourceList(res.content);
            },
             function (error) {
            alert(error);
        });
    }

    $('#search-btn').click(function () {
        page=1;
        searchcont=$('#search-input').val();
        searchtime=$('#sec-time').val();
        getSearchList(searchcont,searchtime,page);
    })

    $('#sec-time').change(function () {
        page=1;
        searchcont=$('#search-input').val();
        searchtime=$('#sec-time').val();
        getSearchList(searchcont,searchtime,page);
    })
});