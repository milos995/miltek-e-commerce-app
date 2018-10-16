<?php 
	require_once '../config/db_connection.php';
	include 'helpers/session.php';
	include 'helpers/helpers.php';
	if(!is_logged_in()){
		login_redirect();
	}

	// change status
	if(isset($_GET['complete']) && ($_GET['complete'] == 1 || $_GET['complete'] == 2)){
		$order_id = (int)$_GET['order_id'];
		$status = (int)$_GET['complete'];
		$conn->query("UPDATE orders SET status = $status WHERE order_id = $order_id");
		header('Location: index.php');
	}

	$order_id = (int)$_GET['order_id'];
	$order_query = $conn->query("SELECT o.*, u.full_name FROM orders o JOIN cart c ON o.order_id = c.order_id JOIN user u ON c.user_id = u.user_id WHERE o.order_id = $order_id GROUP BY o.order_id");
	$order = mysqli_fetch_assoc($order_query);

	$products_query = $conn->query("SELECT  p.title, ca.category, b.brand, p.price, c.ordered_quantity FROM cart c JOIN product p ON c.product_id = p.product_id JOIN category ca ON ca.category_id = p.category_id JOIN brand b ON b.brand_id = p.brand_id  WHERE c.order_id = $order_id");	
?>
<!DOCTYPE html>
<html>
<head>
	<title>MILTEK Admin</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">	
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>	
</head>
<body>
	<div class="container-fluid">
		<nav class="navbar navbar-inverse navbar-fixed-top navbar-collapse navigation">
			<div class="container">				
				<ul class="nav navbar-nav">
					<li class="active"><a href="index.php">MILTEK Admin</a></li>
					<li><a href="brands.php">Brendovi</a></li>
					<li><a href="categories.php">Kategorije</a></li>
					<li><a href="products.php">Proizvodi</a></li>
					<li><a href="users.php">Korisnici</a></li>
					<li><a href="top_rated.php">Izvestaji</a></li>
					<li class="logout"><a href="logout.php">Odjavite se</a></li>
				</ul>
			</div>
		</nav>
		
		<h2 class="text-center">Naručeni proizvodi</h2><hr>
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th>Proizvod</th>
				<th>Kategorija</th>
				<th>Brend</th>
				<th>Cena</th>
				<th>Količina</th>
			</thead>
			<tbody>
				<?php 
				while($product = mysqli_fetch_assoc($products_query)): ?>
					<tr>
						<td><?=$product['title'];?></td>
						<td><?=$product['category'];?></td>
						<td><?=$product['brand'];?></td>
						<td><?=money($product['price']);?></td>
						<td><?=$product['ordered_quantity'];?></td>
					</tr>
				<?php endwhile; ?>
			</tbody>
		</table>

		<h2 class="text-center">Detalji narudžbine br. <?=$order_id;?></h2><hr>
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>		
				<th>Ime i prezime</th>	
				<th>Adresa</th>	
				<th>Broj telefona</th>
				<th>Datum</th>
				<th>Ukupno</th>	
			</thead>
			<tbody>
				<tr>
					<td><?=$order['full_name'];?></td>
					<td><?=$order['zip_code'].' - '.$order['city'].', '.$order['address'];?></td>
					<td><?=$order['phone'];?></td>
					<td><?=pretty_date($order['date']);?></td>
					<td><?=money($order['in_total']);?></td>					
				</tr>
			</tbody>
		</table>
		
		<div class="pull-right margin-top">
			<a href="index.php" class="btn btn-danger">Otkaži</a>
			<a href="orders.php?complete=<?=(($order['status'] == 0)?1:2);?>&order_id=<?=$order_id;?>" class="btn my-btn"><?=(($order['status'] == 0)?'Pošalji':'Završi');?> narudžbinu</a>
		</div>
	</div>
</body>
</html>