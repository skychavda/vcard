<?php
    require 'init.php';
    $key = $_GET['key'];
    $userId = $_GET['userId'];
    if(isset($_GET['key'])){
        $query = "SELECT * FROM Cards WHERE UserId = '$userId' AND CompanyName LIKE '%$key%'";
        $result = mysqli_query($con, $query);
        $response = array();
        while ($row = mysqli_fetch_assoc($result)){
            array_push($response,
            array(
                'userId'=>$row['CardId'],
                'frontImage'=>'http://vcardscanner.gq/android_db_files/uploads/'.$row['FrontImage'].'',
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
                'frontImage'=>'http://vcardscanner.gq/android_db_files/uploads/'.$row['FrontImage'].'',
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