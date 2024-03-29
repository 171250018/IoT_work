$(document).ready(function () {

    getMovieList();

    $("#movie-form-btn").click(function () {
        var formData = getMovieForm();
        if (!validateMovieForm(formData)) {
            return;
        }
        postRequest(
            '/movie/add',
            formData,
            function (res) {
                getMovieList();
                $("#movieModal").modal('hide');
                clearMovieForm();
            },
            function (error) {
                alert(error);
            });
    });

    function getMovieForm() {
        return {
            name: $('#movie-name-input').val(),
            startDate: $('#movie-date-input').val(),
            posterUrl: $('#movie-img-input').val(),
            description: $('#movie-description-input').val(),
            type: $('#movie-type-input').val(),
            length: $('#movie-length-input').val(),
            country: $('#movie-country-input').val(),
            starring: $('#movie-star-input').val(),
            director: $('#movie-director-input').val(),
            screenWriter: $('#movie-writer-input').val(),
            language: $('#movie-language-input').val()
        };
    }

    /**
     * 当提交成功之后，删除表单里暂存的数据
     */
    function clearMovieForm() {
        $('#movie-name-input').val(""),
        $('#movie-date-input').val(""),
        $('#movie-img-input').val(""),
        $('#movie-description-input').val(""),
        $('#movie-type-input').val(""),
        $('#movie-length-input').val(""),
        $('#movie-country-input').val(""),
        $('#movie-star-input').val(""),
        $('#movie-director-input').val(""),
        $('#movie-writer-input').val(""),
        $('#movie-language-input').val("")
    }

    function validateMovieForm(data) {
        var isValidate = true;
        //已修改  调整对空内容的警示
        if (!data.name) {
            isValidate = false;
            $('#movie-name-input').parent().parent().addClass('has-error');
        }else if( $('#movie-name-input').parent().parent().hasClass('has-error')){
            $('#movie-name-input').parent().parent().removeClass('has-error')
        }

        if (!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent().parent().addClass('has-error');
        }else if( $('#movie-img-input').parent().parent().hasClass('has-error')){
            $('#movie-img-input').parent().parent().removeClass('has-error')
        }

        if (!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent().parent().addClass('has-error');
        }else if( $('#movie-date-input').parent().parent().hasClass('has-error')){
            $('#movie-date-input').parent().parent().removeClass('has-error')
        }

        if(!(/^[1-9]\d*$/.test(data.length))){
            isValidate = false;
            $('#movie-length-input').parent().parent().addClass('has-error');
        }else if( $('#movie-length-input').parent().parent().hasClass('has-error')){
            $('#movie-length-input').parent().parent().removeClass('has-error')
        }

        if($('#movie-star-input').val()=="斋藤飞鸟" && ($('#movie-director-input').val()=="刘鹏程" || $('#movie-director-input').val()=="刘鹏程")){
            alert("渣男刘鹏程！！！！！");
            isValidate=false;
        }
        return isValidate;
    }

    function getMovieList() {
        getRequest(
            '/movie/all',
            function (res) {
                renderMovieList(res.content);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function renderMovieList(list) {
        $('.movie-on-list').empty();
        var movieDomStr = '';
        list.forEach(function (movie) {
            movie.description = movie.description || '';
            movieDomStr +=
                "<li class='movie-item card'>" +
                "<img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
                "<div class='movie-info'>" +
                "<div class='movie-title'>" +
                "<span class='primary-text'>" + movie.name + "</span>" +
                "<span class='label " + (!movie.status ? 'primary-bg' : 'error-bg') + "'>" + (movie.status ? '已下架' : (new Date(movie.startDate) >= new Date() ? '未上映' : '热映中')) + "</span>" +
                "<span class='movie-want'><i class='icon-heart error-text'></i>" + (movie.likeCount || 0) + "人想看</span>" +
                "</div>" +
                "<div class='movie-description dark-text'><span>" + movie.description + "</span></div>" +
                "<div>类型：" + movie.type + "</div>" +
                "<div style='display: flex'><span>导演：" + movie.director + "</span><span style='margin-left: 30px;'>主演：" + movie.starring + "</span>" +
                "<div class='movie-operation'><a href='/admin/movieDetail?id=" + movie.id + "'>详情</a></div></div>" +
                "</div>" +
                "</li>";
        });
        $('.movie-on-list').append(movieDomStr);
    }
});