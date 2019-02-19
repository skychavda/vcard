<?php
$host = "localhost";
$user_name = "id8741352_vcardscanner";
$user_password = "vcard@123";
$db_name = "id8741352_vcardmaster";

$con = mysqli_connect($host,$user_name,$user_password,$db_name);

if($con)
echo "success..";
else
echo "failed";
?>