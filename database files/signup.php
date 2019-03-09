<?php
    require "init.php";
    $name = $_GET["userName"];
    $password = $_GET["password"];
    $mobileNumber = $_GET["number"];
    $email = $_GET["email"];
    $sql = "SELECT * FROM User WHERE Email='$email'";
    $result = mysqli_query($con,$sql);
    $message="";

    if(mysqli_num_rows($result)>0) {
        $message = "exist";
    }else{
        $sql = "INSERT INTO User (Email, Password, MobileNumber, Name)
        VALUES ('$email','$password','$mobileNumber','$name')";

        if ($con->query($sql) === TRUE) {
            $message="ok";
        } else {
        echo $message="error";
        }
    }
    
    $conn->close();
?>