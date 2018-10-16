<!DOCTYPE html>
<html>
<head>
	<title>Kategorije</title>
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
					<li class="active"><a href="categories.php">Kategorije</a></li>
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
			$uploadName = '';
			// get categories
			$sql = "SELECT * FROM category ORDER BY category";
			$results = $conn->query($sql);
			$errors = array();

			// edit category
			if(isset($_GET['edit']) && !empty($_GET['edit'])){
				$edit_id = (int)$_GET['edit'];
				$sql2 = "SELECT * FROM category WHERE category_id = $edit_id";
				$edit_result = $conn->query($sql2);
				$eCategory = mysqli_fetch_assoc($edit_result);

				if(isset($_GET['delete_image'])){
					$image_url = "../img/categories/".$eCategory['image'];		
					unlink($image_url);
					$conn->query("UPDATE category SET image = '' WHERE category_id = $edit_id");
					header('Location: categories.php?edit='.$edit_id);
				}
				$uploadName = $eCategory['image'];	
			}	

			// delete category
			if(isset($_GET['delete']) && !empty($_GET['delete'])){
				$delete_id = (int)$_GET['delete'];
				$category_delete = $conn->query("SELECT * FROM category WHERE category_id = $delete_id");
				$cate_del = mysqli_fetch_assoc($category_delete);
				$sql = "DELETE FROM category WHERE category_id = $delete_id";
				$success = $conn->query($sql);
				if($success){
					$image_url = "../img/categories/".$cate_del['image'];		
					unlink($image_url);
					header('Location: categories.php');
				}else{
					$errors[] = 'Nije moguće obrisati ovu kategoriju!';
					echo display_errors($errors);
				}				
			}

			// add category
			if(isset($_POST['add_submit'])){
				$category = $_POST['category'];
				if($_POST['category'] == ''){
					$errors[] .= 'Morate uneti kategoriju!';
				}

				$sql = "SELECT * FROM category WHERE category = '$category'";
				if(isset($_GET['edit'])){
					$sql = "SELECT * FROM category WHERE category = '$category' AND category_id != $edit_id";
				}
				$result = $conn->query($sql);
				$count = mysqli_num_rows($result);

				if($count > 0){
					$errors[] .= $category.' već postoji u bazi';
				}

				if(isset($_FILES['photo']) && $_FILES['photo']['size'] > 0){
					$photo = $_FILES['photo'];
					$name = $photo['name'];
					$nameArray = explode('.',$name);
					$fileName = $nameArray[0];
					$fileExt = $nameArray[1];
					$tmpLoc = $photo['tmp_name'];
					$uploadName = md5(microtime()).'.'.$fileExt;
					$uploadPath = '../img/categories/'.$uploadName;

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
					$sql = "INSERT INTO category (category, image) VALUES ('$category', '$uploadName')";
					if(isset($_GET['edit'])){
						$sql = "UPDATE category SET category = '$category', image = '$uploadName' WHERE category_id = $edit_id";
					}	
					$conn->query($sql);
					header('Location: categories.php');
				}
			}
		 ?>
		<h2 class="text-center">Kategorije</h2><hr>
		<!-- Category form -->
		<div class="text-center">
			<form class="form-inline" action="categories.php<?=((isset($_GET['edit']))?'?edit='.$edit_id:'');?>" method="POST" enctype="multipart/form-data">
				<div class="form-group">
					<?php 
						$saved_image = '';
						$category_value = "";
						if(isset($_GET['edit'])){
							$category_value = $eCategory['category'];
							$saved_image = $eCategory['image'];
							$uploadName = $saved_image;
						}else{
							if(isset($_POST['category'])){
								$category_value = $_POST['category'];
							}
						} ?>
					<?php if($saved_image != ''):?>
						<div class="saved-image">
							<img src="../img/categories/<?=$saved_image;?>"/><br>
							<a href="categories.php?delete_image=1&edit=<?=$edit_id;?>" class="text-danger">Izbriši sliku</a>
						</div>
					<?php else: ?>
						<label for="photo">Slika:</label>
						<input type="file" name="photo" id="photo" class="form-control">
					<?php endif; ?>					

					<label for="category">Kategorija:</label>
					<input type="text" name="category" id="category" class="form-control" value="<?=$category_value;?>">
					<?php if(isset($_GET['edit'])): ?>
						<a href="categories.php" class="btn btn-danger">Otkaži</a>
					<?php endif; ?>
					<input type="submit" name="add_submit" value="<?=((isset($_GET['edit']))?'Izmeni':'Dodaj');?>" class="btn my-btn">
				</div>
			</form>
		</div><hr>

		<!-- Categories table -->
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th></th>
				<th>Kategorija</th>
				<th></th>
			</thead>
			<tbody>
				<?php while($category = mysqli_fetch_assoc($results)): ?>
					<tr>
						<td><a href="categories.php?edit=<?=$category['category_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
						<td><?=$category['category'];?></td>
						<td><a href="categories.php?delete=<?=$category['category_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove-sign"></span></a></td>
					</tr>
			<?php endwhile; ?>
			</tbody>
		</table>
	</div>
</body>
</html>