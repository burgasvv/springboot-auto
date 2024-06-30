
$(document).ready(function(){

    $("#attachToCarForm").submit(function(event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/attach-to-car",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Complectation attached on car successfully");
                console.log(result);
                $("#divAttachToCar").remove();
                $("#divDetachFromCar").load(location.href + ' #divDetachFromCar', function () {
                    $.getScript("/resource/js/detachEquipment.js");
                })
                $("#divAttached").remove();
                $("#divAttachedMain").load(location.href + ' #divAttachedMain');
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        })
    })
})