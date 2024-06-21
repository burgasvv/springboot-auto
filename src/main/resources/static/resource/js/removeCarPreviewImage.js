
$(document).ready(function(){

    $("#removeImageForm").submit(function(event){
        event.preventDefault();
        $.ajax({
            type : "POST",
            url : window.location + "/remove-preview-image",
            header: $("#remove-preview-image-csrf-token").val(),
            success : function (result) {
                alert("Preview image removed successfully");
                console.log(result);
                $("#divEmptyImage").load(location.href + ' #divEmptyImage', function () {
                    $.getScript("/resource/js/imageResource.js");
                });
            },
            error : function (e) {
                alert("Something went wrong");
                console.log("ERROR: " + e);
            }
        })
    })
})