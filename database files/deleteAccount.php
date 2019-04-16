<?php
  require "init.php";
  $UserId = $_POST['userId'];
  if(isset($_POST['userId'])){
    $sql = "DELETE FROM User WHERE UserId = '$UserId'";
    $result = mysqli_query($con, $sql);
    $message = "deleted";
    echo json_encode(array("response"=>$message));
  }
?>