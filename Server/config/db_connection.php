<?php 

define('HOST', 'localhost');
define('DB', 'id6936570_miltekdb');
define('USER', 'id6936570_milos');
define('PASS', 'milos123');

$conn = new mysqli(HOST, USER, PASS, DB);

if ($conn->connect_errno) {
    printf("Konekcija neuspešna: %s\n", $mysqli->connect_error);
    exit();
}

$conn->set_charset("utf8");

?>