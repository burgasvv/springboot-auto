
$(document).ready(function(){

    $("#detachFromCarForm").submit(function(event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/detach-from-car",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Complectation detached from car successfully");
                console.log(result);
                $("#divDetachFromCar").remove();
                $("#divAttachToCar").load(location.href + ' #divAttachToCar', function () {
                    $.getScript("/resource/js/attachEquipment.js");
                    $.getScript("/resource/js/selectChosen.js");
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