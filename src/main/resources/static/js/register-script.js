
function sendEmail(data) {
    $.ajax({
        url: "https://pochod.valasskapolanka.cz/register-pop.php",
        // url: "https://daweedek.wz.cz/register-pop.php",
        type: 'post',
        data: data,
        beforeSend: function(xhr) {
            if (token && header) {
                xhr.setRequestHeader(header, token);
            }
        },
        success: function () {
            console.log("email should be sent")
        },
        error: function () {
            console.log("something went wrong with sending request to php script")
        }
    });
}

function registerMe() {
    var nickName = document.getElementById("username").value.replace(/\s+/g, ' ').trim()
    var email = document.getElementById("email").value
    var phone = document.getElementById("phone").value
    var data = {
        nickName: nickName,
        email: email,
        phone: phone,
        pin: Math.floor(1000 + Math.random() * 9000)
    }
    token = $("meta[name='_csrf']").attr("content")
    header = $("meta[name='_csrf_header']").attr("content")

    $.ajax({
        url: "/api/register",
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
            console.log("registration completed, sending email")
            sendEmail(data);
            alert("Registrace dokončena, během několika minut očekávejte email s přihlašovacími údaji.")
            location.replace("/login");
        },
        error: function () {
            alert("Registrace se nezdařila. Jméno se již používá.")
        }
    });
}
