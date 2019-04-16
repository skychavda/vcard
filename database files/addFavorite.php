<?php
    require 'init.php';
    $key = $_GET['key'];
    $CardId = $_GET['cardId'];
    $sql = "UPDATE Cards SET IsFavorite = '$key' WHERE CardId = '$CardId'";
    $result = mysqli_query($con, $sql);
    if(!$result>0) {
        $message = "fail";
        echo json_encode(array("response"=>$message));
    } else {
        $message = "added";
        echo json_encode(array("response"=>$message));
    }
?>