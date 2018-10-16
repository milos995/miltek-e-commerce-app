<?php 
	require_once '../config/db_connection.php';
	include 'helpers/session.php';
	unset($_SESSION['SBUser']);
	header('Location: login.php');
?>