
$(document).ready(function(){

    $("#makeAdminForm").submit(function(e){
        e.preventDefault();
        $.ajax({
            type: "POST",
            url: "/users/secure/make-admin",
            data: new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("The selected user is assigned as admin");
                console.log(result);
                $("#divMakeAdmin").remove();
                $("#divMakeAdminImages").load(location.href + ' #divMakeAdminImages', function () {
                    $.getScript("/resource/js/makeAdmin.js");
                });
            },
            error: function (result) {
                alert("Something went wrong")
                console.log("ERROR: ", result);
            }
        })
    })
})