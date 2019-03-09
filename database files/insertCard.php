<?php
    require "init.php";
    $userId = $_POST['userID'];
    $frontImage = $_POST['frontImage'];
    $companyName = $_POST['companyName'];
    $companyAddress = $_POST['companyAddress'];
    $firstName1 = $_POST['firstName1'];
    $contactNumber1 = $_POST['contactNumber1'];
    $contactEmail1 = $_POST['contactEmail1'];
    $designation1 = $_POST['designation1'];
    $sql = "SELECT * FROM Cards WHERE CompanyName='$companyName'";
    $result = mysqli_query($con,$sql);
    $message="";

    if($con){
        if(mysqli_num_rows($result)>0) {
            $message = "exist";
        }else{
            $upload_path = "images/$frontImage.jpg";
    
            $sql = "INSERT INTO Cards (UserId, FrontImage, CompanyName, CompanyAddress, FirstName1, ContactNumber1, ContactEmail1, Designation1) VALUES ('$userId', '$frontImage', '$companyName', '$companyAddress', '$firstName1', '$contactNumber1', '$contactEmail1', '$designation1')";
    
            if(mysqli_query($con,$sql)){
                file_put_contents($upload_path,base64_decode($frontImage));
                $message = "File uploaded";
                echo json_encode(array("response"=>$message));
            }else{
                $message = "File uploaded fail";
                echo json_encode(array("response"=>$message,"path"=>$upload_path));
            }
            // if(count($_POST)>0){
            //     if(!$_FILES['image']['error']==UPLOAD_ERR_INI_SIZE){
            //         $target = "images/".basename($_FILES['image']['name']);
            //         if(file_exists($target)){
            //             $message="Sorry file is already exists";
            //             echo json_encode(array("response"=>$message));
            //         }
            //         else{
            //             mysqli_query($con,$sql);
            //             $tmp_name = $_FILES['image']['tmp_name'];
    
            //             if(move_uploaded_file($tmp_name, $target)){
            //                 $message = "Image upload succesfully";
            //                 compressImage($tmp_name,$target,60);
            //             }else{
            //                 $message = "Image not moved";
            //             }
            //             echo json_encode(array("response"=>$message));
            //         }
            //     }   
            // }
        }
    }else{
        $message = "COnnection error";
        echo json_encode(array("response"=>$message));
    }

    // function compressImage($source,$destination,$quality){
	// 	$info = getimagesize($source);
	// 	$image = imagecreatefromjpeg($source);
	// 	imagejpeg($image,$destination,$quality);
	// }
?>