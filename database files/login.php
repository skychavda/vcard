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
            $name = $row['Name'];
            $email = $row['Email'];
            $number = $row['MobileNumber'];
            $companyName = $row['CompanyName'];
            $message = "ok";
            echo json_encode(array("response"=>$message,"id"=>$id,"name"=>$name,"email"=>$email,"number"=>$number,"companyName"=>$companyName));
        }
    }
?>