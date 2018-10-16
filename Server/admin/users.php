<!DOCTYPE html>
<html>
<head>
	<title>Korisnici</title>
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
					<li  class="active"><a href="users.php">Korisnici</a></li>
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
			// get users
			$sql = "SELECT * FROM user ORDER BY full_name";
			$results = $conn->query($sql);
			$errors = array();

			// edit user
			if(isset($_GET['edit']) && !empty($_GET['edit'])){
				$edit_id = (int)$_GET['edit'];
				$sql2 = "SELECT * FROM user WHERE user_id = $edit_id";
				$edit_result = $conn->query($sql2);
				$eUser = mysqli_fetch_assoc($edit_result);
			}	

			// delete user
			if(isset($_GET['delete']) && !empty($_GET['delete'])){
				$delete_id = (int)$_GET['delete'];
				$sql = "DELETE FROM user WHERE user_id = $delete_id";
				$success = $conn->query($sql);
				if($success){
					header('Location: users.php');
				}else{
					$errors[] = 'Nije moguće obrisati ovog korisnika!';
					echo display_errors($errors);
				}				
			}

			// add user
			if(isset($_POST['add_submit'])){
				$full_name = $_POST['full_name'];
				$email = $_POST['email'];
				$password = $_POST['password'];
				$role = $_POST['role'];

				if($_POST['full_name'] == '' || $_POST['email'] == '' || $_POST['password'] == '' || $_POST['role'] == ''){
					$errors[] .= 'Sva polja su obavezna!';
				}

				if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
					$errors[] .= 'Email adresa nije validna!';
				}

				if(strlen($password) < 6){
					$errors[] .= 'Lozinka mora imati najmanje 6 karaktera!';
				}

				$sql = "SELECT * FROM user WHERE email = '$email' AND role = $role";
				if(isset($_GET['edit'])){
					$sql = "SELECT * FROM user WHERE email = '$email' AND user_id != $edit_id AND role = $role";
				}
				$result = $conn->query($sql);
				$count = mysqli_num_rows($result);

				if($count > 0){
					$errors[] .= $email.' već postoji u bazi';
				}

				if(!empty($errors)){
					echo display_errors($errors);
				}else{
					if(isset($_GET['edit'])){
						$sql = "UPDATE user SET email = '$email', full_name = '$full_name', password = '$password', role = $role WHERE user_id = $edit_id";
						$conn->query($sql);
					}else{
						$sql = "INSERT INTO user (full_name, email, password, role) VALUES ('$full_name', '$email', '$password', $role)";
						$conn->query($sql);
						if($role == 0){
							$sql = "SELECT user_id FROM user WHERE email = '".$email."'";
							$result = mysqli_query($conn,$sql);
							$user_item = mysqli_fetch_assoc($result);
							$user_id = $user_item['user_id'];
							$sql = "INSERT INTO shipping_address (user_id) VALUES (".$user_id.")";
							$conn->query($sql);
						}
					}					
					header('Location: users.php');
				}
			}
		 ?>
		<h2 class="text-center">Korisnici</h2><hr>
		<!-- Users form -->
		<div class="text-center">
			<form class="form-inline" action="users.php<?=((isset($_GET['edit']))?'?edit='.$edit_id:'');?>" method="post">
				<div class="form-group">
					<?php 
						$full_name_value = "";
						$email_value = "";
						$password_value = "";
						$role_value = "";
						if(isset($_GET['edit'])){
							$full_name_value = $eUser['full_name'];
							$email_value = $eUser['email'];
							$password_value = $eUser['password'];
							$role_value = $eUser['role'];
						}else{
							if(isset($_POST['full_name'])){
								$full_name_value = $_POST['full_name'];
							}
							if(isset($_POST['email'])){
								$email_value = $_POST['email'];
							}
							if(isset($_POST['role'])){
								$role_value = $_POST['role'];
							}
							if(isset($_POST['password'])){
								$password_value = $_POST['password'];
							}
						} ?>
					<label for="full_name">Ime i prezime:</label>
					<input type="text" name="full_name" id="full_name" class="form-control" value="<?=$full_name_value;?>">

					<label for="email">Email:</label>
					<input type="text" name="email" id="email" class="form-control" value="<?=$email_value;?>">

					<label for="role">Uloga:</label>
					<select class="form-control" id="role" name="role">
						<option value="" <?=(($role_value == '')?'selected':'');?>></option>						
						<option value="0" <?=(($role_value == 0)?'selected':'');?>>Korisnik</option>
						<option value="1" <?=(($role_value == 1)?'selected':'');?>>Admin</option>
					</select>

					<label for="password">Lozinka:</label>
					<input type="password" name="password" id="password" class="form-control" value="<?=$password_value;?>">

					<?php if(isset($_GET['edit'])): ?>
						<a href="users.php" class="btn btn-danger">Otkaži</a>
					<?php endif; ?>
					<input type="submit" name="add_submit" value="<?=((isset($_GET['edit']))?'Izmeni':'Dodaj');?>" class="btn my-btn">
				</div>
			</form>
		</div><hr>

		<!-- Users table -->
		<table class="table table-bordered table-striped table-auto table-condensed">
			<thead>
				<th></th>
				<th>Ime i prezime</th>
				<th>Email</th>
				<th>Uloga</th>
				<th></th>
			</thead>
			<tbody>
				<?php while($user = mysqli_fetch_assoc($results)): ?>
					<tr>
						<td><a href="users.php?edit=<?=$user['user_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
						<td><?=$user['full_name'];?></td>
						<td><?=$user['email'];?></td>
						<td><?=(($user['role']==0)?'Korisnik':'Admin');?></td>
						<td> <?php if($user['user_id'] != $user_data['user_id']): ?>
								<a href="users.php?delete=<?=$user['user_id'];?>" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove-sign"></span></a>
							<?php endif; ?>
						</td>
					</tr>
			<?php endwhile; ?>
			</tbody>
		</table>
	</div>
</body>
</html>