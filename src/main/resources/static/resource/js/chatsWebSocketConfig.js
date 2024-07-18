
let stompClient = null;
let notificationCount = 0;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send-private").click(function() {
        sendPrivateMessage();
    });

    $("#notifications").click(function() {
        resetNotificationCount();
    });
});

function connect() {
    let socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);
        updateNotificationDisplay();

        stompClient.subscribe('/user/topic/private-messages', function () {
            setTimeout(showMessage, 1000)
        });

        stompClient.subscribe('/user/topic/private-notifications', function () {
            notificationCount = notificationCount + 1;
            setTimeout(updateNotificationDisplay, 1000)
        });
    });
}

function showMessage() {
    $('#divChats').remove();
    $('#divChatsMain').load(location.href + ' #divChatsMain');
}

function sendPrivateMessage() {
    console.log("sending private message");
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
    $('#divChats').remove();
    $('#divChatsMain').load(location.href + ' #divChatsMain');
}

function updateNotificationDisplay() {
    let notifications = $('#notifications');
    if (notificationCount === 0) {
        notifications.hide();
    } else {
        notifications.show();
        notifications.text(notificationCount);
    }
}

function resetNotificationCount() {
    notificationCount = 0;
    updateNotificationDisplay();
}