
$(document).ready(function () {

    $("#addAlbumImagesForm").submit(function (event) {
        event.preventDefault();

        let formData = new FormData($(this)[0]);
        $.ajax({
            method: "POST",
            url: window.location + "/add-images",
            enctype: "multipart/form-data",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Images successfully added to album");
                console.log(result);
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
})