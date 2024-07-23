
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
    $('#divCommentText').load(location.href + ' #divCommentText');
    setTimeout(showComments, 1000);
}