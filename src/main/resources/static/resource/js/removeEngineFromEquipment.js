
$(document).ready(function () {

    $("#removeEngineForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/remove-engine",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Engine removed from equipment successfully");
                console.log(result);
                $("#divEngineBlock").remove();
                $("#divEngineBlockMain").load(location.href + ' #divEngineBlockMain', function () {
                    $.getScript("/resource/js/selectChosen.js");
                    $.getScript("/resource/js/addEngineOnEquipment.js");
                })
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        })
    })
})