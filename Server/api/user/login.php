<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

$data = json_decode(file_get_contents("php://input"));

$email = stripcslashes($data->email);
$password = stripcslashes($data->password);

global $conn;

$status = 0;
$message = "";
$user_id = 0;
$full_name = "";
$city = "";
$zip_code = 0;
$address = "";
$phone = "";

if(empty($email) || empty($password)){
	$message = "Sva polja su obavezna";
}else{
	$stmt = $conn->prepare('SELECT u.*, sa.city, sa.zip_code, sa.address, sa.phone FROM user u JOIN shipping_address sa ON u.user_id = sa.user_id WHERE email = ? AND password = ? AND role = 0');
	$stmt->bind_param("ss", $email, $password);
	$stmt->execute();

	$result = $stmt->get_result();
	if($result->num_rows === 1){
		$row = $result->fetch_assoc();
		
		$status = 1;
		$message = "Uspešno ste se prijavili!";
		$user_id = $row['user_id'];
		$full_name = $row['full_name'];
		$city = $row['city'];
		$zip_code = $row['zip_code'];
		$address = $row['address'];
		$phone = $row['phone'];
	}else{
		$message = "Uneli ste nepostojeći email i/ili lozinku";
		$email = "";
	}
}

$post_data = array(
	'status' => $status,
	'message' => $message,
	'data' => array(
		'user_id' => $user_id,
		'full_name' => $full_name,
		'email' => $email,
		'city' => $city,
		'zip_code' => $zip_code,
		'address' => $address,
		'phone' => $phone
	)
);

$post_data = json_encode($post_data);
echo $post_data;

mysqli_close($conn);

?>