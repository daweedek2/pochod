function submitStamp(playerId, stageId, pin) {
    var data = {
        playerId: playerId,
        stageId: stageId,
        pin: pin
    }
    token = $("meta[name='_csrf']").attr("content")
    header = $("meta[name='_csrf_header']").attr("content")

    $.ajax({
        url: "/api/submitStamp",
        type: 'post',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(data),
        beforeSend: function(xhr) {
            if (token && header) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function () {
            console.log("stamp submit is successful")
        },
        error: function () {
            console.log("stamp submit failed")
        }
    });
}
