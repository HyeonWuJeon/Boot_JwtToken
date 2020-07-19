$(document).ready(function () {
    $("#email").blur(function () {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/checkEmail",
            data: JSON.stringify({"user_email": $('#email').val()}),
            dataType: 'json',
            success: function (data) {
                console.log("1 = 중복이다 / 0 = 중복아니다 : " + data);
                if (data == 1) {
                    // 1 : 이메일이 중복되는 문구
                    $("#email_check").text("사용중인 이메일입니다. :p");
                    $("#email_check").css("color", "red");
                    $("#email").focus();
                } else { // 0
                    // 공백일 때 이메일 입력 문구
                    if ($('#email').val() == "") {
                        $('#email_check').text('이메일을 입력해주세요. :)');
                        $('#email_check').css('color', 'red');
                        $("#user_email").focus();
                        // 이메일 형식이 아닐 때('@' 또는 '.' 가 없는 경우)
                    } else if ($('#email').val().indexOf("@") == -1 ||
                        $('#email').val().indexOf(".") == -1) {
                        $("#email_check").text("이메일 형식이 아닙니다. :(");
                        $("#email_check").css("color", "red");
                        $("#user_email").focus();
                        // 이메일이 사용가능한 문구
                    } else {
                        $("#email_check").text("사용가능한 이메일입니다. :p");
                        $("#email_check").css("color", "blue");
                    }
                }
            },
            error: function () {
                console.log("실패");
            }
        });
    });
    // 비빌번호, 비빌번호 확인 라벨 비교 구문
    $("#user_password2").blur(function () {

        console.log("안들어오니??")
        const pw1 = $('#user_password').val();
        const pw2 = $('#user_password2').val();
        if (pw1 === pw2) {
            console.log("true")
            $("#pwd_check").text("패스워드가 일치합니다. :) ");
            $("#pwd_check").css("color", "blue");
        } else if (pw2 == null) {
            console.log("넑값 잡히니?")
        } else {
            $("#pwd_check").text("패스워드가 일치하지 않습니다. :p ");
            $("#pwd_check").css("color", "red");
            $("#user_password").focus();
        }
    });
});




//-----------------------------------
let main = {
    init: function () {
        let _this = this;


        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },

    save: function () {
        let data = {
            title: $('#title').val(),
            type: $('#type').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            window.location.href = '/';
            alert(error);
        });
    },
    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();