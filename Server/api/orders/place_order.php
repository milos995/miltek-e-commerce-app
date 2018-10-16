<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

$data = json_decode(file_get_contents("php://input"));

$user_id = stripcslashes($data->user_id);
$city = stripcslashes($data->city);
$zip_code = stripcslashes($data->zip_code);
$address = stripcslashes($data->address);
$phone = stripcslashes($data->phone);
$quantity = stripcslashes($data->quantity);
$in_total = stripcslashes($data->in_total);
$quantityArray = explode(';',$quantity);
$i = 0;

global $conn;

$status = 0;
$message = "";

if(empty($user_id) || empty($city) || empty($zip_code) || empty($address) || empty($phone) || empty($in_total)){
	$message = "Sva polja su obavezna";
}else{
	$sql = "select * from cart where user_id = ".$user_id." and order_id IS NULL";
	$results = $conn->query($sql);
		
	if(mysqli_num_rows($results) > 0){
		$conn->query("INSERT INTO orders (city, zip_code, address, phone, in_total) VALUES ('$city', $zip_code, '$address', '$phone', $in_total)");
		$order_query = $conn->query("SELECT MAX(order_id) AS order_id FROM orders");
		$order_item = mysqli_fetch_assoc($order_query);
		$order_id = $order_item['order_id'];
	    while ($cart = mysqli_fetch_assoc($results)){
			$cart_id = $cart["cart_id"];
        	$sql = "UPDATE cart SET order_id = $order_id, ordered_quantity = ".$quantityArray[$i]." WHERE cart_id = $cart_id";
			$conn->query($sql);

			$product_query = $conn->query("SELECT * FROM product WHERE product_id = ".$cart['product_id']);
			$product = mysqli_fetch_assoc($product_query);
			$new_quantity = $product['quantity'] - $quantityArray[$i];
			$product_id = $product['product_id'];
			$sql = "UPDATE product SET quantity = ".$new_quantity." WHERE product_id = ".$product_id;	
			$conn->query($sql);	
			
			$i++;	
    	}    	
			
		$message = "Uspešno ste naručili proizvode, uskoro ćemo vas pozvati kako bi potvrdili narudžbinu";
		$status = 1;
	}else{
		$message = "Vaša korpa je prazna";
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