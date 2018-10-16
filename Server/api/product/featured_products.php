<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

global $conn;

$status = 0;
$message = "";

$sql = "SELECT * FROM product WHERE featured = 1 ORDER BY price ASC";
$results = $conn->query($sql);
 
$post_data = array();

if(mysqli_num_rows($results)>0){
 
    // products array
    $post_data["data"]=array();
    $status = 1;
    $message = "Uspešno pronadjeni proizvodi";
 
    while ($product = mysqli_fetch_assoc($results)){
        $quantity = $product['quantity'];
        $product_item=array(
            "product_id" => $product['product_id'],
            "title" => $product['title'],
            "price" => $product['price'],
            "brand_id" => $product['brand_id'],
            "category_id" => $product['category_id'],
            "image" => $product['image'],
            "description" => $product['description'],
            "featured" => $product['featured'],
            "quantity" => $quantity
        );
        if($quantity > 0){
            array_push($post_data["data"], $product_item);
        }
    }
}else{
	$message = "Nismo pronašli ni jedan proizvod";
}

$post_data['status'] = $status;
$post_data['message'] = $message;


$post_data = json_encode($post_data);
echo $post_data;

mysqli_close($conn);

?>