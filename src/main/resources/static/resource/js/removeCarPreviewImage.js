
$(document).ready(function(){

    $("#removeImageForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/remove-preview-image",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Preview image removed successfully");
                console.log(result);
                $("#divImageModal").remove();
                $("#divAcceptedImage").remove();
                $("#divCancelledImage").load(location.href + ' #divCancelledImage', function () {
                    $.getScript("/resource/js/addCarPreviewImage.js");
                });
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        });
    });
})