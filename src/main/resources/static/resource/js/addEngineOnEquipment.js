
$(document).ready(function () {

    $("#addEngineForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/add-engine",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Engine added on equipment successfully");
                console.log(result);
                $("#divEngineBlock").remove();
                $("#divEngineBlockMain").load(location.href + ' #divEngineBlockMain', function () {
                    $.getScript("/resource/js/selectChosen.js");
                    $.getScript("/resource/js/removeEngineFromEquipment.js");
                })
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        })
    })
})