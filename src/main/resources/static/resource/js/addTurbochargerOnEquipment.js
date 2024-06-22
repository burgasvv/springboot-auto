
$(document).ready(function () {

    $("#addTurbochargerForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/add-turbocharger",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Turbocharger added on equipment successfully");
                console.log(result);
                $("#divTurbochargerBlock").remove();
                $("#divTurbochargerBlockMain").load(location.href + ' #divTurbochargerBlockMain', function () {
                    $.getScript("/resource/js/selectChosen.js");
                    $.getScript("/resource/js/removeTurbochargerFromEquipment.js");
                })
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        })
    })
})