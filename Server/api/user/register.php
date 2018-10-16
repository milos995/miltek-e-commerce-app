<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

$data = json_decode(file_get_contents("php://input"));

$full_name = stripcslashes($data->full_name);
$email = stripcslashes($data->email);
$password = stripcslashes($data->password);

global $conn;

$status = 0;
$message = "";

if(empty($full_name) || empty($email) || empty($password)){
	$message = "Sva polja su obavezna";
}else{
	$sql = "select * from user where email = '".$email."' and role = 0";
	$result = mysqli_query($conn,$sql);
		
	if(mysqli_num_rows($result)>0){
		$message = "E-mail koji ste odabrali već postoji u našoj bazi";	
	}else{
		$sql = "insert into user (full_name, email, password) values('".$full_name."','".$email."','".$password."')";
		$result = mysqli_query($conn,$sql);
		$sql = "SELECT user_id FROM user WHERE email = '".$email."'";
		$result = mysqli_query($conn,$sql);
		$user_item = mysqli_fetch_assoc($result);
		$user_id = $user_item['user_id'];
		$sql = "insert into shipping_address (user_id) values(".$user_id.")";
		$result = mysqli_query($conn,$sql);
		$message = "Uspešno ste se registrovali";
		$status = 1;
	}
}

$post_data = array(
	'status' => $status,
	'message' => $message
);

$post_data = json_encode($post_data);
echo $post_data;

mysqli_close($conn);

?>