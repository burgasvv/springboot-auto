
let stompClient = null;
$(document).ready(function () {
    connect();
});

function connect() {
    let socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);

        stompClient.subscribe("/user/topic/private-messages", function () {
            $('#divMyMessages').load(location.href + ' #divMyMessages');
        })
    })
}