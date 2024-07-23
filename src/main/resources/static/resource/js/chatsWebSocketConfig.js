
let stompClient = null;

$(document).ready(function() {
    connect();

    $("#send-private").click(function() {
        sendPrivateMessage();
    });
});

function connect() {
    let socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.subscribe('/user/topic/private-messages', function () {
            setTimeout(showMessage, 1000)
        });
    });
}

function showMessage() {
    $('#divChats').remove();
    $('#divChatsMain').load(location.href + ' #divChatsMain');
}

function sendPrivateMessage() {
    stompClient.send("/app/private-message", {},
        JSON.stringify(
            {
                'content': $("#private-message").val(),
                'receiver': $("#users").val(),
                'sender' : $("#sender").val(),
            }
        ));

    setTimeout(refreshMessages, 1000);
}

function refreshMessages(){
    $('#divChatsMain').load(location.href + ' #divChatsMain');
}