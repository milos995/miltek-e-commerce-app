<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

$data = json_decode(file_get_contents("php://input"));

$email = stripcslashes($data->email);

global $conn;

$status = 0;
$message = "";

if(empty($email)){
	$message = "Email adresa je obavezna";
}else{
	$sql = "select * from user where email = '".$email."' and role = 0";
	$result = mysqli_query($conn,$sql);
		
	if(mysqli_num_rows($result) === 1){
		$row = $result->fetch_assoc();
		$password = $row['password'];
		$full_name = $row['full_name'];

		$to = $email;
		$subject = 'Zaboravljena lozinka';
		$txt = "Poštovani ".$full_name.",\n\nVaša lozinka je: ".$password.".\n\nSrdačan pozdrav,\nVaš Miltek.";

		mail($email, $subject, $txt);	

		$status = 1;
		$message = "Uskoro ćete dobiti email sa vašom lozinkom";	
	}else{
		$message = "E-mail koji ste uneli ne postoji u našoj bazi";
		
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