<?php 
	require_once '../config/db_connection.php';
	include 'helpers/session.php';
	include 'helpers/helpers.php';
	if(!is_logged_in()){
		login_redirect();
	}
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
		
		<h2 class="text-center">Narudžbine</h2><hr>
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th></th>
				<th>Narudžbina</th>
				<th>Ukupno</th>
				<th>Datum</th>
				<th>Status</th>
			</thead>
			<tbody>
				<?php 
				$orders_query = $conn->query("SELECT * FROM orders WHERE status != 2");
				while($order = mysqli_fetch_assoc($orders_query)): ?>
					<tr>
						<td><a href="orders.php?order_id=<?=$order['order_id'];?>" class="btn btn-xs my-btn">Detalji</a></td>
						<td>Narudžbina br. <?=$order['order_id'];?></td>
						<td><?=money($order['in_total']);?></td>
						<td><?=pretty_date($order['date']);?></td>
						<td><?=(($order['status'] == 0)?'U obradi':'Poslato');?></td>
					</tr>
				<?php endwhile; ?>
			</tbody>
		</table>
	</div>
</body>
</html>