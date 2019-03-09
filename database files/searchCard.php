<?php
    require 'init.php';
    if(isset($_GET['key'])){
        $key = $_GET['key'];
        $userId = $_GET['userId'];
        $query = "SELECT * FROM Cards WHERE UserId = '$userId' AND CompanyName LIKE '%$key%'";
        $result = mysqli_query($con, $query);
        $response = array();
        while ($row = mysqli_fetch_assoc($result)){
            array_push($response,
            array(
                'userId'=>$row['CardId'],
                'frontImage'=>$row['FrontImage'],
                'companyName'=>$row['CompanyName'],
                'firstName1'=>$row['FirstName1'],
                'contactNumber1'=>$row['ContactNumber1'],
                'contactEmail'=>$row['ContactEmail1'],
                'designation'=>$row['Designation1']
            ));
        }
        echo json_encode($response);
    }else{
        $query = "SELECT * FROM Cards WHERE UserId = '$userId'";
        $result = mysqli_query($con, $query);
        $response = array();
        while ($row = mysqli_fetch_assoc($result)){
            array_push($response,
            array(
                'userId'=>$row['CardId'],
                'frontImage'=>$row['FrontImage'],
                'companyName'=>$row['CompanyName'],
                'firstName1'=>$row['FirstName1'],
                'contactNumber1'=>$row['ContactNumber1'],
                'contactEmail'=>$row['ContactEmail1'],
                'designation'=>$row['Designation1']
            ));
        }
        echo json_encode($response);
    }
?>

/* <?php
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
        }else if(!$_FILES['image']['error']==UPLOAD_ERR_INI_SIZE){
            $target = "images/".basename($_FILES['image']['name']);
            if(file_exists($target)){
                $message="Sorry file is already exists";
                echo json_encode(array("response"=>$message));
            }
            else{
                $sql = "INSERT INTO Cards (UserId, FrontImage, CompanyName, CompanyAddress, FirstName1, ContactNumber1, ContactEmail1, Designation1) VALUES ('$userId', '$frontImage', '$companyName', '$companyAddress', '$firstName1', '$contactNumber1', '$contactEmail1', '$designation1')";
                $tmp_name = $_FILES['image']['tmp_name'];
                mysqli_query($con,$sql);
                if(move_uploaded_file($tmp_name, $target)){
                    $message = "Image upload succesfully";
                    compressImage($tmp_name,$target,60);
                }else{
                    $message = "Image not moved";
                }
                echo json_encode(array("response"=>$message));
            }
        }
    }else{
        $message = "COnnection error";
        echo json_encode(array("response"=>$message));
    }

    function compressImage($source,$destination,$quality){
		$info = getimagesize($source);
		$image = imagecreatefromjpeg($source);
		imagejpeg($image,$destination,$quality);
	}
?> */