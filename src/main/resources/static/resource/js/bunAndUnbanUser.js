
$(document).ready(function () {

    $("#banUserForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: location.href + "/ban",
            data: new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("The selected user was banned successfully");
                console.log(result);
                $("#divBanAndUnban").remove();
                $("#divBanAndUnbanMain").load(location.href + ' #divBanAndUnbanMain', function () {
                    $.getScript("/resource/js/bunAndUnbanUser.js")
                })
            },
            error: function (result) {
                alert("Something went wrong");
                console.log(result);
            }
        })
    })

    $("#unbanUserForm").submit(function (event){
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: location.href + "/unban",
            data: new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result){
                alert("The selected user was unbanned successfully");
                console.log(result);
                $("#divBanAndUnban").remove();
                $("#divBanAndUnbanMain").load(location.href + ' #divBanAndUnbanMain', function () {
                    $.getScript("/resource/js/bunAndUnbanUser.js")
                })
            },
            error: function (result) {
                alert("Something went wrong");
                console.log(result);
            }
        })
    })
})