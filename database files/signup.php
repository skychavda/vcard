<?php
    require "init.php";
    $firstname = $_POST["firstName"];
    $lastname = $_POST["lastName"];
    $password = $_POST["password"];
    $mobileNumber = $_POST["number"];
    $email = $_POST["email"];
    $address = $_POST["address"];
    $companyName = $_POST["companyName"];
    $sql = "SELECT * FROM User WHERE Email='$email'";
    $result = mysqli_query($con,$sql);
    $message="";

    if($con){
        if(mysqli_num_rows($result)>0) {
            $message = "user exist";
            echo json_encode(array("response"=>$message));
        }else{
            $sql = "INSERT INTO User (Email, Password, MobileNumber, FirstName, LastName, Address, CompanyName)
            VALUES ('$email','$password','$mobileNumber','$firstname','$lastname','$address','$companyName')";

            if ($con->query($sql) === TRUE) {
                $message="Account created";
                echo json_encode(array("response"=>$message));
            } else {
            $message="error";
            echo json_encode(array("response"=>$message));
            }
        }
    } else {
        $message="error";
        echo json_encode(array("response"=>$message));
    }
    
?>