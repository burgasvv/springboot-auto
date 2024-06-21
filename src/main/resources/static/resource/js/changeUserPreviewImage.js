
$(document).ready(function (){
    $("#changeImageForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type : "POST",
            url : window.location + "/change-image",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            header: $("#preview-image-csrf-token").val(),
            success : function (result) {
                alert("Your image changed successfully");
                console.log(result);
                $("#divImg").load(location.href + ' #divImg', function () {
                    $.getScript("/resource/js/imageResource.js");
                });
                $("#divBarUserImage").load(location.href + ' #divBarUserImage', function () {
                    $.getScript("/resource/js/selectChosen.js");
                });
            },
            error : function (e) {
                alert("error")
                console.log("ERROR: ", e);
            }
        });
    });
})