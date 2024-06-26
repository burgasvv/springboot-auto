
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
                $("#divBarUserImage").load(location.href + ' #divBarUserImage', function () {
                    $.getScript("/resource/js/selectChosen.js");
                });
                $("#divImg").load(location.href + ' #divImg', function () {
                    $.getScript("/resource/js/imageResource.js");
                });
            },
            error : function (e) {
                alert("error")
                console.log("ERROR: ", e);
            }
        });
    });

    $("#addImageForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type : "POST",
            url : window.location + "/add-image",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            header: $("#preview-image-csrf-token-for-adding").val(),
            success : function (result) {
                alert("Your image added successfully");
                console.log(result);
                $("#divAddImageBlock").remove();
                $("#divChangeImageBlockMain").load(location.href + ' #divChangeImageBlockMain', function () {
                    $.getScript("/resource/js/imageResource.js");
                    $.getScript("/resource/js/changeUserPreviewImage.js");
                    $.getScript("/resource/js/deleteUserPreviewImage.js");
                });
                $("#divBarUserBlock").load(location.href + ' #divBarUserBlock', function () {
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