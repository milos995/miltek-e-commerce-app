<!DOCTYPE html>
<html>
<head>
	<title>Brendovi</title>
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
					<li  class="active"><a href="brands.php">Brendovi</a></li>
					<li><a href="categories.php">Kategorije</a></li>
					<li><a href="products.php">Proizvodi</a></li>
					<li><a href="users.php">Korisnici</a></li>
					<li><a href="top_rated.php">Izvestaji</a></li>
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
			$sql = "SELECT * FROM brand ORDER BY brand";
			$results = $conn->query($sql);
			$errors = array();

			// edit brand
			if(isset($_GET['edit']) && !empty($_GET['edit'])){
				$edit_id = (int)$_GET['edit'];
				$sql2 = "SELECT * FROM brand WHERE brand_id = $edit_id";
				$edit_result = $conn->query($sql2);
				$eBrand = mysqli_fetch_assoc($edit_result);
			}	

			// delete brand
			if(isset($_GET['delete']) && !empty($_GET['delete'])){
				$delete_id = (int)$_GET['delete'];
				$sql = "DELETE FROM brand WHERE brand_id = $delete_id";
				$success = $conn->query($sql);
				if($success){
					header('Location: brands.php');
				}else{
					$errors[] = 'Nije moguće obrisati ovaj brend!';
					echo display_errors($errors);
				}
			}

			// add brand
			if(isset($_POST['add_submit'])){
				$brand = $_POST['brand'];
				if($_POST['brand'] == ''){
					$errors[] .= 'Morate uneti brend!';
				}

				$sql = "SELECT * FROM brand WHERE brand = '$brand'";
				if(isset($_GET['edit'])){
					$sql = "SELECT * FROM brand WHERE brand = '$brand' AND brand_id != $edit_id";
				}
				$result = $conn->query($sql);
				$count = mysqli_num_rows($result);

				if($count > 0){
					$errors[] .= $brand.' već postoji u bazi';
				}

				if(!empty($errors)){
					echo display_errors($errors);
				}else{
					$sql = "INSERT INTO brand (brand) VALUES ('$brand')";
					if(isset($_GET['edit'])){
						$sql = "UPDATE brand SET brand = '$brand' WHERE brand_id = $edit_id";
					}	
					$conn->query($sql);
					header('Location: brands.php');
				}
			}
		 ?>
		<h2 class="text-center">Brendovi</h2><hr>
		<!-- Brand form -->
		<div class="text-center">
			<form class="form-inline" action="brands.php<?=((isset($_GET['edit']))?'?edit='.$edit_id:'');?>" method="post">
				<div class="form-group">
					<?php 
						$brand_value = "";
						if(isset($_GET['edit'])){
							$brand_value = $eBrand['brand'];
						}else{
							if(isset($_POST['brand'])){
								$brand_value = $_POST['brand'];
							}
						} ?>
					<label for="brand">Brend:</label>
					<input type="text" name="brand" id="brand" class="form-control" value="<?=$brand_value;?>">
					<?php if(isset($_GET['edit'])): ?>
						<a href="brands.php" class="btn btn-danger">Otkaži</a>
					<?php endif; ?>
					<input type="submit" name="add_submit" value="<?=((isset($_GET['edit']))?'Izmeni':'Dodaj');?>" class="btn my-btn">
				</div>
			</form>
		</div><hr>

		<!-- Brand table -->
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th></th>
				<th>Brend</th>
				<th></th>
			</thead>
			<tbody>
				<?php while($brand = mysqli_fetch_assoc($results)): ?>
					<tr>
						<td><a href="brands.php?edit=<?=$brand['brand_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
						<td><?=$brand['brand'];?></td>
						<td><a href="brands.php?delete=<?=$brand['brand_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove-sign"></span></a></td>
					</tr>
			<?php endwhile; ?>
			</tbody>
		</table>
	</div>	
</body>
</html>