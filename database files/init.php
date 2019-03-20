<?php
$host = "mysql.vcardscanner.gq";
$user_name = "vcardscanner";
$user_password = "vcard123@";
$db_name = "vcardscanner";

$con = mysqli_connect($host,$user_name,$user_password,$db_name);

if(!$con)
echo "failed";
?>