<?php 
	function display_errors($errors){
		$display = '<ul class="bg-danger">';
		foreach ($errors as $error) {
			$display .= '<li class="text-danger">'.$error.'</li>';
		}
		$display .= '</ul>';
		return $display;
	}

	function money($number){
		return number_format($number,2).' RSD';
	}

	function pretty_date($date){
		$sql_date = strtotime($date);
		return date("d/m/Y", $sql_date); 
	}	
 ?>