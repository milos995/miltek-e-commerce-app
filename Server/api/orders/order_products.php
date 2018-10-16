<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

if(isset($_GET['order_id'])){
    $order_id = $_GET['order_id'];
}else{
    http_response_code(400);
    die();
}

global $conn;

$status = 0;
$message = "";

$sql = "SELECT  p.title, p.description, p.image, c.ordered_quantity FROM cart c JOIN product p ON c.product_id = p.product_id WHERE c.order_id = $order_id";
$results = $conn->query($sql);
 
$post_data = array();

if(mysqli_num_rows($results)>0){
 
    // products array
    $post_data["data"]=array();
    $status = 1;
    $message = "Uspešno pronadjeni proizvodi iz narudžbine";
 
    while ($orders = mysqli_fetch_assoc($results)){
        $order_item=array(
            "title" => $orders['title'],
            "description" => $orders['description'],
            "image" => $orders['image'],
            "quantity" => $orders['ordered_quantity'],
        );
 
        array_push($post_data["data"], $order_item);
    }
}else{
	$message = "Vaša istorija kupovine je prazna";
}

$post_data['status'] = $status;
$post_data['message'] = $message;


$post_data = json_encode($post_data);
echo $post_data;

mysqli_close($conn);

?>