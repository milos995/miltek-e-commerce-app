<!DOCTYPE html>
<html>
<head>
	<title>Proizvodi</title>
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
					<li class="active"><a href="products.php">Proizvodi</a></li>
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
			// delete product
			if(isset($_GET['delete'])){			
				$delete_id = (int)$_GET['delete'];
				$product_results = $conn->query("SELECT * FROM product WHERE product_id = $delete_id");
				$product = mysqli_fetch_assoc($product_results);
				$success = $conn->query("DELETE FROM product WHERE product_id = $delete_id");
				if($success){					
					$image_url = "../img/products/".$product['image'];		
					unlink($image_url);
					header('Location: products.php');
				}else{
					$error = array();
					$error[] = "Nije moguće obrisati ovaj proizovd!";
					echo display_errors($error);
				}
			}

			$uploadName = '';

			if(isset($_GET['add']) || isset($_GET['edit'])){
				$brandQuery = $conn->query("SELECT * FROM brand ORDER BY brand");
				$categoryQuery = $conn->query("SELECT * FROM category ORDER BY category");
				$title = ((isset($_POST['title']) && $_POST['title'] != '')?$_POST['title']:'');
				$brand = ((isset($_POST['brand']) && $_POST['brand'] != '')?$_POST['brand']:'');
				$category = ((isset($_POST['category']) && $_POST['category'] != '')?$_POST['category']:'');
				$price = ((isset($_POST['price']) && $_POST['price'] != '')?$_POST['price']:'');
				$quantity = ((isset($_POST['quantity']) && $_POST['quantity'] != '')?$_POST['quantity']:'');
				$description = ((isset($_POST['description']) && $_POST['description'] != '')?$_POST['description']:'');
				$saved_image = '';

				if(isset($_GET['edit'])){
					$edit_id = (int)$_GET['edit'];
					$product_results = $conn->query("SELECT * FROM product WHERE product_id = $edit_id");
					$product = mysqli_fetch_assoc($product_results);
					if(isset($_GET['delete_image'])){
						$image_url = "../img/products/".$product['image'];		
						unlink($image_url);
						$conn->query("UPDATE product SET image = '' WHERE product_id = $edit_id");
						header('Location: products.php?edit='.$edit_id);
					}
					$title = ((isset($_POST['title']) && $_POST['title'] != '')?$_POST['title']:$product['title']);
					$brand = ((isset($_POST['brand']) && $_POST['brand'] != '')?$_POST['brand']:$product['brand_id']);
					$category = ((isset($_POST['category']) && $_POST['category'] != '')?$_POST['category']:$product['category_id']);
					$price = ((isset($_POST['price']) && $_POST['price'] != '')?$_POST['price']:$product['price']);
					$quantity = ((isset($_POST['quantity']) && $_POST['quantity'] != '')?$_POST['quantity']:$product['quantity']);
					$description = ((isset($_POST['description']) && $_POST['description'] != '')?$_POST['description']:$product['description']);
					$saved_image = (($product['image'] != '')?$product['image']:'');
					$uploadName = $saved_image;
				}

				if($_POST){			
					$errors = array();
					$required = array('title', 'brand', 'category', 'price', 'quantity', 'description');
					foreach ($required as $field) {
						if($_POST[$field] == ''){
							$errors[] = 'Sva polja sa znakom * su obavezna';
							break;
						}
					}

					if(isset($_FILES['photo']) && $_FILES['photo']['size'] > 0){
						$photo = $_FILES['photo'];
						$name = $photo['name'];
						$nameArray = explode('.',$name);
						$fileName = $nameArray[0];
						$fileExt = $nameArray[1];
						$tmpLoc = $photo['tmp_name'];
						$uploadName = md5(microtime()).'.'.$fileExt;
						$uploadPath = '../img/products/'.$uploadName;

						$info = getimagesize($_FILES['photo']['tmp_name']);
						if ($info === FALSE) {
							$errors[] = 'Fajl koji ste odabrali nije slika!';
						}

						if (($info[2] !== IMAGETYPE_GIF) && ($info[2] !== IMAGETYPE_JPEG) && ($info[2] !== IMAGETYPE_PNG)) {
							$errors[] = 'Dozvoljeni su samo gif/jpeg/png formati!';
						}
					}

					if(!empty($errors)){
						echo display_errors($errors);
					}else{
						if(isset($_FILES['photo']) && $_FILES['photo']['size'] > 0){
							move_uploaded_file($tmpLoc,$uploadPath);
						}
						$insertSql = "INSERT INTO product (title, price, brand_id, category_id, image, description, featured, quantity) VALUES ('$title', $price, $brand, $category, '$uploadName', '$description', 0, $quantity)";
						if(isset($_GET['edit'])){
							$insertSql = "UPDATE product SET title = '$title', price = $price, brand_id = $brand, category_id = $category, image = '$uploadName', description = '$description', quantity = $quantity WHERE product_id = $edit_id";
						}
						$conn->query($insertSql);
						header('Location: products.php');
					}
				}
			?>
			<h2 class="text-center"><?=((isset($_GET['edit']))?'Izmeni':'Dodaj novi');?> proizvod</h2><hr>
			
			<!-- products form -->
			<form action="products.php?<?=((isset($_GET['edit']))?'edit='.$edit_id:'add=1');?>" method="POST" enctype="multipart/form-data">
				<div class="form-group col-md-3">
					<label for="title">Naziv proizvoda*:</label>
					<input type="text" name="title" id="title" class="form-control" value="<?=$title;?>">
				</div>
				<div class="form-group col-md-3">
					<label for="brand">Brend*:</label>
					<select class="form-control" id="brand" name="brand">
						<option value="" <?=(($brand == '')?'selected':'');?>></option>
						<?php while($b = mysqli_fetch_assoc($brandQuery)): ?>
							<option value="<?=$b['brand_id'];?>" <?=(($brand == $b['brand_id'])?'selected':'');?>><?=$b['brand'];?></option>
						<?php endwhile; ?>
					</select>
				</div>
				<div class="form-group col-md-3">
					<label for="category">Kategorija*:</label>
					<select class="form-control" id="category" name="category">
						<option value="" <?=(($category == '')?'selected':'');?>></option>
						<?php while($c = mysqli_fetch_assoc($categoryQuery)): ?>
							<option value="<?=$c['category_id'];?>" <?=(($category == $c['category_id'])?'selected':'');?>><?=$c['category'];?></option>
						<?php endwhile; ?>
					</select>
				</div>
				<div class="form-group col-md-3">
					<label for="price">Cena*:</label>
					<input type="number" min="0" step="0.01" name="price" id="price" class="form-control" value="<?=$price;?>">
				</div>
				<div class="form-group col-md-3">
					<label for="quantity">Količina*:</label>
					<input type="number" min="0" name="quantity" id="quantity" class="form-control" value="<?=$quantity;?>">
				</div>
				<div class="form-group col-md-3">
					<?php if($saved_image != ''):?>
						<div class="saved-image">
							<img src="../img/products/<?=$saved_image;?>"/><br>
							<a href="products.php?delete_image=1&edit=<?=$edit_id;?>" class="text-danger">Izbriši sliku</a>
						</div>
					<?php else: ?>
						<label for="photo">Slika:</label>
						<input type="file" name="photo" id="photo" class="form-control">
					<?php endif; ?>
				</div>
				<div class="form-group col-md-6">
					<label for="description">Opis*:</label>
					<textarea type="text" name="description" id="description" class="form-control" rows="6"><?=$description;?></textarea>
				</div>
				<div class="form-group pull-right col-md-3 text-right">
					<a href="products.php" class="btn btn-danger">Otkaži</a>
					<input type="submit" value="<?=((isset($_GET['edit']))?'Izmeni':'Dodaj');?> proizvod" class="btn my-btn">
				</div><div class="clearfix"></div>
			</form>
			<?php 	
			}else{

			$sql = "SELECT * FROM product p JOIN brand b ON p.brand_id = b.brand_id JOIN category c ON p.category_id = c.category_id ORDER BY title";
			$presults = $conn->query($sql);

			if(isset($_GET['featured'])){
				$id = (int) $_GET['id'];
				$featured = (int) $_GET['featured'];
				$featuredSql = "UPDATE product SET featured = $featured WHERE product_id = $id";
				$conn->query($featuredSql);
				header('Location: products.php');
			}
		 ?>
		<h2 class="text-center">Proizvodi</h2>
		<a href="products.php?add=1" class="btn my-btn pull-right" id="add-product-btn">Dodaj proizvod</a><div class="clearfix"></div>
		<hr>

		<!-- products table -->
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th></th>
				<th>Proizvod</th>
				<th>Cena</th>
				<th>Kategorija</th>
				<th>Brend</th>
				<th>Količina</th>
				<th>Izdvojiti</th>
			</thead>
			<tbody>
				<?php while($product = mysqli_fetch_assoc($presults)): ?>
					<tr  class="<?=(($product['quantity'] == 0)?'danger':'');?>">
						<td>
							<a href="products.php?edit=<?=$product['product_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></a>
							<a href="products.php?delete=<?=$product['product_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove-sign"></span></a>
						</td>
						<td><?=$product['title'];?></td>
						<td><?=money($product['price']);?></td>
						<td><?=$product['category'];?></td>
						<td><?=$product['brand'];?></td>
						<td><?=$product['quantity'];?></td>
						<td><a href="products.php?featured=<?=(($product['featured'] == 0)?'1':'0');?>&id=<?=$product['product_id'];?>" class="btn btn-xs btn-default">
								<span class="glyphicon glyphicon-<?=(($product['featured'] == 1)?'minus':'plus');?>"></span>
							</a>&nbsp <?=(($product['featured'] == 1)?'Izdvojen proizvod':'');?>
						</td>
					</tr>
				<?php endwhile; ?>
			</tbody>
		</table>
		<?php } ?>
	</div>
</body>
</html>