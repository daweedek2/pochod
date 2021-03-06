<?php
header('Access-Control-Allow-Origin: *');
/*if(!isset($_POST['submit']))
{
	//This page should not be accessed directly. Need to submit the form.
	echo "Chyba; je potreba potvrdit dotaznik!";
}*/
$nickName = $_POST['nickName'];
$visitor_email = $_POST['email'];
$pin = $_POST['pin'];

//Validate first
if(empty($nickName)||empty($visitor_email)||empty($pin))
{
    echo "Chybejici data!";
    exit;
}

if(IsInjected($visitor_email))
{
    echo "Nespravny email!";
    exit;
}

$email_from = 'pochod@valasskapolanka.cz';//<== update the email address
$email_subject = 'Pochod okolo Polanky - Registrace';
$email_body = "Děkujeme za registraci pro Pochod okolo Polanky. Pro přihlášení do aplikace použijte následující údaje:\r\n\r\n".
    "URL: https://pochod.herokuapp.com\r\n".
    "Jméno: $nickName\r\n".
    "Heslo: $pin\r\n".
    "\r\n".
    "Na email prosím neodpovídejte, děkujeme.";

$to = $visitor_email;//<== update the email address
$headers = 'From: pochod@valasskapolanka.cz' . "\r\n" .
    "Reply-To: pochod@valasskapolanka.cz" . "\r\n" .
    "Content-Type: text/plain; charset=UTF-8" . "\r\n" .
    'Bcc: daweedek@gmail.com,pochod@valasskapolanka.cz' . "\r\n" .
    'X-Mailer: PHP/' . phpversion();
//Send the email!
mail($to,$email_subject,$email_body,$headers);

//done. redirect to thank-you page.
//header('Location: thank-you.html');


// Function to validate against any email injection attempts
function IsInjected($str)
{
    $injections = array('(\n+)',
        '(\r+)',
        '(\t+)',
        '(%0A+)',
        '(%0D+)',
        '(%08+)',
        '(%09+)'
    );
    $inject = join('|', $injections);
    $inject = "/$inject/i";
    if(preg_match($inject,$str))
    {
        return true;
    }
    else
    {
        return false;
    }
}

?>