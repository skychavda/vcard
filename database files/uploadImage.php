<?php
    require "init.php";
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(0);
    $userId = $_POST['userID'];
    $companyName = $_POST['companyName'];
    $companyAddress = $_POST['companyAddress'];
    $firstName1 = $_POST['firstName1'];
    $contactNumber1 = $_POST['contactNumber1'];
    $contactEmail1 = $_POST['contactEmail1'];
    $designation1 = $_POST['designation1'];
    $message = "";

// Check if image file is a actual image or fake image
if($con) {
    $target_dir = "uploads/";
    $target_file = $target_dir . basename($_FILES["image"]["name"]);
    $uploadOk = 1;
    $imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
    $check = getimagesize($_FILES["image"]["tmp_name"]);
    // Check if file already exists
    if (file_exists($target_file)) {
      $message = "Sorry, file already exists.";
      $uploadOk = 0;
      echo json_encode(array("response"=>$message));
    } else if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg" && $imageFileType != "gif" ) {
      $message = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
      $uploadOk = 0;
      echo json_encode(array("response"=>$message));
    } else {
      // '$companyName', '$companyAddress', '$firstName1', '$contactNumber1', '$contactEmail1', '$designation1'
        $image = $_FILES['image']['name'];
        $sql = "INSERT INTO Cards (UserId, FrontImage, CompanyName, CompanyAddress, FirstName1, ContactNumber1, ContactEmail1, Designation1) VALUES ('$userId', '$image', '$companyName', '$companyAddress', '$firstName1', '$contactNumber1', '$contactEmail1', '$designation1')";
        mysqli_query($con,$sql);
        if (move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)) {
            $message =  "The file ". basename( $_FILES["image"]["name"]). " has been uploaded.";
            compressImage($tmp_name,$target,60);
            echo json_encode(array("response"=>$message));
        } else {
            $message = "Sorry, there was an error uploading your file.";
            echo json_encode(array("response"=>$message));
        }
      }

}
function compressImage($source,$destination,$quality){
  $info = getimagesize($source);
  $image = imagecreatefromjpeg($source);
  imagejpeg($image,$destination,$quality);
}
?>