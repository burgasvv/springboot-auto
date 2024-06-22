
$(document).ready(function () {

    $("#deleteImageForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/remove-image",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Preview image deleted successfully");
                console.log(result);
                $("#divChangeImageBlock").remove();
                $("#divAddImageBlock").load(location.href + ' #divAddImageBlock', function () {
                    $.getScript("/resource/js/changeUserPreviewImage.js");
                });
                $("#divBarUserBlock").load(location.href + ' #divBarUserBlock', function () {
                    $.getScript("/resource/js/selectChosen.js");
                });
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        });
    });
});