
$(document).ready(function () {

    $("#attachEquipmentOnCarForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: window.location + "/attach-equipment",
            enctype : "multipart/form-data",
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