<?php
    require "init.php";
    $email = $_GET["email"];
    $password = $_GET["password"];

    $message="";
    if(count($_GET)>0) {
        $result = mysqli_query($con,"SELECT * FROM User WHERE Email='$email' and Password = '$password'");
        $count  = mysqli_num_rows($result);
        if(!$count>0) {
            $message = "fail";
            echo json_encode(array("response"=>$message));
        } else{
            $row = mysqli_fetch_assoc($result);
            $id = $row['UserId'];
            $firstName = $row['FirstName'];
            $lastName = $row['LastName'];
            $email = $row['Email'];
            $number = $row['MobileNumber'];
            $companyName = $row['CompanyName'];
            $address = $row['Address'];
            $message = "ok";
            echo json_encode(array("response"=>$message,"id"=>$id,"firstName"=>$firstName,"lastName"=>$lastName,"address"=>$address,"email"=>$email,"number"=>$number,"companyName"=>$companyName));
        }
    }
?>