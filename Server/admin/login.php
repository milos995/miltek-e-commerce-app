<?php 
	require_once '../config/db_connection.php';
	include 'helpers/session.php';
	include 'helpers/helpers.php';

	$email = ((isset($_POST['email']))?$_POST['email']:'');
	$email = trim($email);
	$password = ((isset($_POST['password']))?$_POST['password']:'');
	$password = trim($password);
	$errors = array();
?>
<!DOCTYPE html>
<html>
<head>
	<title>Admin login</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">	
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>	
</head>
<body>
	<style>
		body {
			background-image: url("../img/background.png");
			background-repeat: repeat;			
		}
	</style>

	<div class="container-fluid">
		<div id="login-form">
			<div>
				<?php 
					if($_POST){
						if(empty($_POST['email']) || empty($_POST['password'])){
							$errors[] = 'Morate uneti email i lozinku.';
						}

						if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
							$errors[] = 'Email adresa nije validna.';
						}

						$query = $conn->query("SELECT * FROM user WHERE email = '$email' and role = 1");
						$user = mysqli_fetch_assoc($query);
						$userCount = mysqli_num_rows($query);
						if($userCount != 1){
							$errors[] = 'Uneta email adresa ne postoji u našoj bazi.';
						}

						if(strcmp($password, $user['password']) != 0){
							$errors[] = 'Uneli ste pogrešnu lozinku.';
						}

						if(!empty($errors)){
							echo display_errors($errors);
						}else{
							$user_id = $user['user_id'];
							login($user_id);
						}
					}
				?>
			</div>
			<h2 class="text-center">Prijavljivanje</h2><hr>
			<form action="login.php" method="post">
				<div class="form-group">
					<label for="email">Email:</label>
					<input type="text" name="email" id="email" class="form-control" value="<?=$email;?>">
				</div>
				<div class="form-group">
					<label for="password">Lozinka:</label>
					<input type="password" name="password" id="password" class="form-control" value="<?=$password;?>">
				</div>
				<div class="form-group text-right">
					<input type="submit" value="Prijavite se" class="btn my-btn">
				</div>
			</form>
		</div>
	</div>
</body>
</html>