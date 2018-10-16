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

$sql = "SELECT * FROM orders o JOIN cart c on o.order_id = c.order_id WHERE o.show_order = 1 and c.user_id = $user_id GROUP BY o.order_id";
$results = $conn->query($sql);
 
$post_data = array();

if(mysqli_num_rows($results)>0){
 
    // products array
    $post_data["data"]=array();
    $status = 1;
    $message = "Uspešno pronadjene narudžbine";
 
    while ($orders = mysqli_fetch_assoc($results)){
        $sql_date = strtotime($orders['date']);
        $order_item=array(
            "order_id" => $orders['order_id'],
            "city" => $orders['city'],
            "zip_code" => $orders['zip_code'],
            "address" => $orders['address'],
            "phone" => $orders['phone'],
            "in_total" => $orders['in_total'],
            "date" => date("d/m/Y", $sql_date),
            "status" => $orders['status']
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