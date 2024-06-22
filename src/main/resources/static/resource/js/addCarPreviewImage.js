
$(document).ready(function () {

    $("#addImageForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type : "POST",
            url : window.location + "/add-preview-image",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            header: $("#preview-image-csrf-token-for-adding").val(),
            success : function (result) {
                alert("Preview image added successfully");
                console.log(result);
                $("#divCancelledImage").remove();
                $("#divAcceptedImageMain").load(location.href + ' #divAcceptedImageMain', function (){
                    $.getScript("/resource/js/changeCarPreviewImage.js");
                    $.getScript("/resource/js/removeCarPreviewImage.js");
                });
                $("#divImageModalMain").load(location.href + ' #divImageModalMain', function () {
                    $.getScript("/resource/js/imageResource.js");
                });
                $("#divAlbumImg").remove();
                $("#divAlbumImages").load(location.href + ' #divAlbumImages', function () {
                    $.getScript("/resource/js/addCarAlbumImages.js");
                });
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        });
    });
});