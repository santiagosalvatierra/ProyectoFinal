$(document).ready(function () {


    //=====================
    //Ajax Post Login modal
    //=====================
    $("#user-login").submit(function (e) {
        e.preventDefault()
        login();
    })

    function login() {
        var formData = {
            username: $('#username').val(),
            password: $('#password1').val()
        }

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            contentType: "aplication/json",
            url: "/login",
            data: JSON.stringify(formData),
            dataType: "JSON",
            success: function (result) {
                if (result.status == true) {
                    //Reloads the page if the login was successfully processed
                    location.reload()
                } else {
                    console.log(result);
                    $('#error2').html(result.error);
                }
            },
            error: function (e) {
                console.log(e);
            }
        })
    }

});