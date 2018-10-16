<?php 
	session_start();

	if(isset($_SESSION['SBUser'])){
		global $conn;
		$user_id = $_SESSION['SBUser'];
		$query = $conn->query("SELECT * FROM user WHERE user_id = $user_id and role = 1");
		$user_data = mysqli_fetch_assoc($query);

	}

	function login($user_id){
		$_SESSION['SBUser'] = $user_id;
		header('Location: index.php');
	}

	function is_logged_in(){
		if(isset($_SESSION['SBUser']) && $_SESSION['SBUser'] > 0){
			return true;
		}
		return false;
	}

	function login_redirect($url = 'login.php'){
		header('Location: '.$url);
	}
?>