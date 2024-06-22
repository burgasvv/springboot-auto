
$(document).ready(function () {

    $("#shareEquipmentForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/share-equipment",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Equipment shared with user successfully");
                console.log(result);
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        })
    })
})