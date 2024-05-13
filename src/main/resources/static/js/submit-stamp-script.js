function submitStamp(playerId, pin) {
    var data = {
        playerId: playerId,
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
            alert('Razítko bylo zaregistrováno.')
            location.replace("/pop/progress2/2024");
        },
        error: function () {
            console.log("stamp submit failed")
            alert('Skenování se nezdařilo. QR kód nebyl rozpoznán.')
            location.replace("/pop");
        }
    });
}
