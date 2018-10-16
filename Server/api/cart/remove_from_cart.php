<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

$data = json_decode(file_get_contents("php://input"));

$cart_id = stripcslashes($data->cart_id);
$user_id = stripcslashes($data->user_id);
$product_id = stripcslashes($data->product_id);

global $conn;

$status = 0;
$message = "";

if(empty($cart_id) || empty($user_id) || empty($product_id)){
	$message = "Došlo je do greške";
}else{
	$sql = "DELETE FROM cart WHERE cart_id = ".$cart_id;
	$conn->query($sql);
	$message = "Uspešno obrisano iz korpe";
	$status = 1;
}

$post_data['status'] = $status;
$post_data['message'] = $message;

$post_data = json_encode($post_data);
echo $post_data;

mysqli_close($conn);

?>