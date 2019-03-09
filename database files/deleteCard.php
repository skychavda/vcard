<?php
    require 'init.php';
    $cardId = $_POST['cardId'];
    $message = "";
    $sql = "DELETE FROM Cards WHERE CardId = '$cardId'";
    if(mysqli_query($con,$sql)){
        $message = "Delete";
    }else{
        $message = "Error";
    }
    echo json_encode(array('response'=>$message));
?>