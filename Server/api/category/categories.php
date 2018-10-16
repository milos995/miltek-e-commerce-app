<?php 

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include('../../config/db_connection.php');

global $conn;

$status = 0;
$message = "";

$sql = "SELECT * FROM category";
$results = $conn->query($sql);
 
$post_data = array();

if(mysqli_num_rows($results)>0){
 
    // products array
    $post_data["data"]=array();
    $status = 1;
    $message = "Uspešno pronadjene kategorije";
 
    while ($category = mysqli_fetch_assoc($results)){
        $category_item=array(
            "category_id" => $category['category_id'],
            "category" => $category['category'],
            "image" => $category['image']
        );
 
        array_push($post_data["data"], $category_item);
    }
}else{
	$message = "Nismo pronašli ni jednu kategoriju";
}

$post_data['status'] = $status;
$post_data['message'] = $message;


$post_data = json_encode($post_data);
echo $post_data;

mysqli_close($conn);

?>