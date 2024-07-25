
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
            setTimeout(refreshMessages, 1000);
            setTimeout(scroll, 2000);
        });

        stompClient.subscribe('/user/topic/read-messages', function () {
            refreshMessages();
            setTimeout(scroll, 2000);
        });
    });
}

function sendPrivateMessage() {
    stompClient.send("/app/private-message", {},
        JSON.stringify(
            {
                'content': $("#private-message").val(),
                'receiver': $("#receiver").val(),
                'sender' : $("#sender").val(),
            }
        ));
    refreshTextarea();
    setTimeout(refreshMessages, 1000);
    setTimeout(scroll, 2000);
}

function readMessage () {
    stompClient.send('/app/read-message', {}, JSON.stringify(
        {
            'receiver' : $('#inputReceiverName').val(),
            'content': $('#inputMessageId').val()
        }
    ));
    refreshMessages();
    setTimeout(scroll, 2000);
}

function refreshMessages(){
    $('#divChatMessagesMain').load(location.href + ' #divChatMessagesMain');
}

function refreshTextarea() {
    $('#divMessageText').load(location.href + ' #divMessageText');
}

function scroll() {
    document.getElementById('divBottomScroll').scrollIntoView({behavior: "smooth"});
}
