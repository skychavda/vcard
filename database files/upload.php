<?php
    require "init.php";
    $userId = $_POST['userID'];
    // $frontImage = $_POST['frontImage'];
    $companyName = $_POST['companyName'];
    $companyAddress = $_POST['companyAddress'];
    $firstName1 = $_POST['firstName1'];
    $contactNumber1 = $_POST['contactNumber1'];
    $contactEmail1 = $_POST['contactEmail1'];
    $designation1 = $_POST['designation1'];
    // $sql = "SELECT * FROM Cards WHERE CompanyName='$companyName'";
    // $result = mysqli_query($con,$sql);
    $message="";

    // if($con){
    //     $target = "images/$frontImage.jpg";
    //     $sql = "INSERT INTO Cards (UserId, UserCategoryId, FrontImage, BackImage, CompanyName, CompanyAddress, FirstName1, LastName1, ContactNumber1, ContactEmail1, Designation1, DOB, FirstName2, LastName2, ContactNumber2, ContactEmail2, DOB2, Designation2, FirstName3, LastName3, ContactNumber3, ContactEmail3, DOB3, Designation3, CityId, StateId, Logo, TelephoneNumber, Email, NoteId, IsFavorite, Status, CreatedDate) VALUES ('$userId', NULL,'$target', NULL, '$companyName', '$companyAddress', '$firstName1', NULL, '$contactNumber1', '$contactEmail1', '$designation1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
    //     if(mysqli_query($con, $sql)){
    //         file_put_contents($target,base64_decode($frontImage));
    //         echo json_encode(array("response"=>"Image uploaded"));
    //     }else{
    //         echo json_encode(array("response"=>"image not upload"));
    //     }
    // }

    if($con){
        if(!$_FILES['image']['error']==UPLOAD_ERR_INI_SIZE){
            if(mysqli_num_rows($result)>0) {
                $message = "exist";
            }else{
                $target = "images/".basename($_FILES['image']['name']);
                if(file_exists($target)){
                    $message="Sorry file is already exists";
                    echo json_encode(array("response"=>$message,"tmp"=>$_FILES['image']['name']));
                }
                else{
                    $frontImage = $_FILES['image']['name'];
                    $sql = "INSERT INTO Cards (UserId, UserCategoryId, FrontImage, BackImage, CompanyName, CompanyAddress, FirstName1, LastName1, ContactNumber1, ContactEmail1, Designation1, DOB, FirstName2, LastName2, ContactNumber2, ContactEmail2, DOB2, Designation2, FirstName3, LastName3, ContactNumber3, ContactEmail3, DOB3, Designation3, CityId, StateId, Logo, TelephoneNumber, Email, NoteId, IsFavorite, Status, CreatedDate) VALUES ('$userId', NULL,'$frontImage', NULL, '$companyName', '$companyAddress', '$firstName1', NULL, '$contactNumber1', '$contactEmail1', '$designation1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
                    $tmp_name = $_FILES['image']['name'];
                    mysqli_query($con,$sql);
                    if(move_uploaded_file($tmp_name, $target)){
                        $message = "Image upload succesfully";
                        compressImage($tmp_name,$target,60);
                    }else{
                        $message = "Image not moved";
                    }
                    echo json_encode(array("response"=>$message,"tmp"=>$_FILES['image']));
                }
            }
        }else{
            $message = "Image error";
            echo json_encode(array("response"=>$message,"tmp"=>$_FILES['image']));
        }
    } else {
        $message = "connection error";
        echo json_encode(array("response"=>$message));
    }

    function compressImage($source,$destination,$quality){
		$info = getimagesize($source);
		$image = imagecreatefromjpeg($source);
		imagejpeg($image,$destination,$quality);
	}
?>
// <html>
// <body>
//     <form method="POST" enctype="multipart/form-data">
//         <input type="hidden" name="MAX_FILE_SIZE" value="100000" />
// <input type="file" name="image"><br/>
// <button name="upload">submit</button>
// </form>
// </body>
// </html>