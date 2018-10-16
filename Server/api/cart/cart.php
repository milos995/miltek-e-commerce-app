<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

if(isset($_GET['user_id'])){
    $user_id = $_GET['user_id'];
}else{
    http_response_code(400);
    die();
}

global $conn;

$status = 0;
$message = "";

$sql = "SELECT * FROM cart c join product p on c.product_id = p.product_id where c.user_id = ".$user_id." AND order_id IS NULL";
$results = $conn->query($sql);
 
$post_data = array();

if(mysqli_num_rows($results)>0){
 
    // products array
    $post_data["data"]=array();
    $status = 1;
    $message = "Uspešno pronadjeni proizvodi";
 
    while ($cart = mysqli_fetch_assoc($results)){
        $cart_item=array(
            "cart_id" => $cart['cart_id'],
            "user_id" => $cart['user_id'],
            "product_id" => $cart['product_id'],
            "title" => $cart['title'],
            "price" => $cart['price'],
            "image" => $cart['image'],
            "quantity" => $cart['quantity']
        );
        array_push($post_data["data"], $cart_item);
    }
}else{
	$message = "Vaša korpa je prazna";
}

$post_data['status'] = $status;
$post_data['message'] = $message;


$post_data = json_encode($post_data);
echo $post_data;

mysqli_close($conn);

?>