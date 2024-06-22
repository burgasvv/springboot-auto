
$(document).ready(function () {

    $("#attachTagForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/attach-tag",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Hash tag attached successfully");
                console.log(result);
                $("#divTags").remove();
                $("#divTagsMain").load(location.href + ' #divTagsMain');
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        })
    })
});