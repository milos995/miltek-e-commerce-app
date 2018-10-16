<!DOCTYPE html>
<html>
<head>
	<title>Najprodavaniji</title>
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
					<li><a href="index.php">MILTEK Admin</a></li>
					<li><a href="brands.php">Brendovi</a></li>
					<li><a href="categories.php">Kategorije</a></li>
					<li><a href="products.php">Proizvodi</a></li>
					<li><a href="users.php">Korisnici</a></li>
					<li class="active"><a href="top_rated.php">Izvestaji</a></li>
					<li class="logout"><a href="logout.php">Odjavite se</a></li>
				</ul>			
			</div>
		</nav>
		<?php 
			require_once '../config/db_connection.php';
			include 'helpers/session.php';
			include 'helpers/helpers.php';
			if(!is_logged_in()){
				login_redirect();
			}
			// get brands
			$sql = "SELECT p.product_id, p.title, p.price, c.category, b.brand, SUM(ca.ordered_quantity) as sold FROM product p join category c ON p.category_id = c.category_id JOIN brand b on p.brand_id = b.brand_id JOIN cart ca on p.product_id = ca.product_id GROUP BY ca.product_id ORDER BY sold DESC LIMIT 10";
			$results = $conn->query($sql);
			$errors = array();			
		 ?>
		<h2 class="text-center">TOP 10 proizvoda</h2><hr>	

		<!-- Products table -->
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th>Proizvod</th>
				<th>Cena</th>
				<th>Kategorija</th>
				<th>Brend</th>
				<th>Prodato</th>
			</thead>
			<tbody>
				<?php while($product = mysqli_fetch_assoc($results)): ?>
					<tr>
						<td><?=$product['title'];?></td>
						<td><?=money($product['price']);?></td>
						<td><?=$product['category'];?></td>
						<td><?=$product['brand'];?></td>
						<td><?=$product['sold'];?></td>
					</tr>
				<?php endwhile; ?>
			</tbody>
		</table>
		
		<div class="col-md-6 margin-top">
			<h2 class="text-center">TOP 5 kategorija</h2><hr>
			<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th>Kategorija</th>
				<th>Prodato</th>
			</thead>
			<tbody>
				<?php 
				$category_query = $conn->query("SELECT c.category, SUM(ca.ordered_quantity) as sold FROM category c join product p on c.category_id = p.category_id join cart ca on p.product_id = ca.product_id WHERE p.category_id = c.category_id GROUP BY c.category_id ORDER BY sold DESC LIMIT 5");
				while($category = mysqli_fetch_assoc($category_query)): ?>
					<tr>
						<td><?=$category['category'];?></td>
						<td><?=$category['sold'];?></td>
					</tr>
				<?php endwhile; ?>
			</tbody>
		</table>
		</div>

		<div class="col-md-6 margin-top">
			<h2 class="text-center">TOP 5 brendova</h2><hr>
			<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th>Brend</th>
				<th>Prodato</th>
			</thead>
			<tbody>
				<?php 
				$brand_query = $conn->query("SELECT b.brand, SUM(ca.ordered_quantity) as sold FROM brand b join product p on b.brand_id = p.brand_id join cart ca on p.product_id = ca.product_id WHERE p.brand_id = b.brand_id GROUP BY b.brand_id ORDER BY sold DESC LIMIT 5");
				while($brand = mysqli_fetch_assoc($brand_query)): ?>
					<tr>
						<td><?=$brand['brand'];?></td>
						<td><?=$brand['sold'];?></td>
					</tr>
				<?php endwhile; ?>
			</tbody>
		</table>
		</div>
	</div>
</body>
</html>