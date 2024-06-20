
$(document).ready(function (){
    $("#changeImageForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type : "POST",
            url : window.location + "/add-preview-image",
            enctype : "multipart/form-data",
            data : new FormData($(this)[0]),
            processData: false,
            contentType: false,
            cache: false,
            header: $("#preview-image-csrf-token").val(),
            success : function (result) {
                alert("success");
                console.log(result);
                $("#divImg").load(location.href + ' #divImg', function () {
                    const modal = document.getElementById('myModal');
                    const img = document.getElementById('myImg');
                    const modalImg = document.getElementById("img01");
                    const captionText = document.getElementById("caption");
                    img.onclick = function(){
                        modal.style.display = "block";
                        modalImg.src = this.src;
                        captionText.innerHTML = this.alt;
                    }
                    const span = document.getElementsByClassName("close")[0];
                    span.onclick = function() {
                        modal.style.display = "none";
                    }
                });
            },
            error : function (e) {
                alert("error")
                console.log("ERROR: ", e);
            }
        });
    });
})