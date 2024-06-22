
$(document).ready(function () {

    $("#removeEquipmentFromCarForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/remove-equipment-from-car",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                return $.ajaxSettings.xhr();
            },
            success: function (result) {
                alert("Complectation removed from car successfully");
                console.log(result);
                $("#allEquipmentsContent").remove();
                $("#allEquipmentsContentMain").load(location.href + ' #allEquipmentsContent', function () {
                    $.getScript("/resource/js/removeEquipmentFromCar.js");
                    $.getScript("/resource/js/attachEquipmentOnCar.js");
                    $.getScript("/resource/js/selectChosen.js");
                });
            },
            error : function (e) {
                alert("Something went wrong")
                console.log("ERROR: ", e);
            }
        });
    });
});