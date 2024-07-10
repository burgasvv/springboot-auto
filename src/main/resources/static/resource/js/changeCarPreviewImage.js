
$(document).ready(function (){

    $("#changeImageForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type : "POST",
            url : window.location + "/add-preview-image",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            header: $("#preview-image-csrf-token").val(),
            success : function (result) {
                alert("Preview image changed successfully");
                console.log(result);
                $("#divImageModal").load(location.href + ' #divImageModal', function () {
                    $.getScript("/resource/js/imageResource.js");
                });
                $("#divAlbumImg").remove();
                $("#divAlbumImages").load(location.href + ' #divAlbumImages');
            },
            error : function (e) {
                alert("Something went wrong");
                console.log("ERROR: ", e);
            }
        });
    });
})