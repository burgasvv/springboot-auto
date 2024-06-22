
$(document).ready(function () {

    $("#removeTransmissionForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/remove-transmission",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Transmission removed from equipment successfully");
                console.log(result);
                $("#divTransmissionBlock").remove();
                $("#divTransmissionBlockMain").load(location.href + ' #divTransmissionBlockMain', function () {
                    $.getScript("/resource/js/selectChosen.js");
                    $.getScript("/resource/js/addTransmissionOnEquipment.js");
                })
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        })
    })
})