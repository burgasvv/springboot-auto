
let stompClient = null
$(document).ready(function () {
    connect();

    $('#sendComment').click(function () {
        sendComment();
    })
})

function connect() {
    let socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe("/queue/car-comments", function () {
            setTimeout(showComments, 1000);
        })
    })
}

function showComments() {
    $('#divComments').remove();
    $('#divCommentsMain').load(location.href + ' #divCommentsMain');
}

function sendComment() {
    stompClient.send("/app/car-comment", {},JSON.stringify(
        {
            'content' : $('#content').val(),
            'author': $('#author').val(),
            'objectId' : $('#objectId').val()
        }
    ))

    setTimeout(showComments, 1000);
}