<?php

require_once '../dao/EurekaDao.php';

//parametros
$cuenta = $_REQUEST['cta'];
$fechaMovimiento = $_REQUEST['fm'];
$codEmpleado = $_REQUEST['empl'];
$tipoMovimiento = $_REQUEST['tm'];
$importe = $_REQUEST['imp'];

// Proceso
$dao = new EurekaDao();
$res = $dao->insertarMovimiento($cuenta, $fechaMovimiento, $codEmpleado, $tipoMovimiento, $importe);
if($res){
	if($res == 1){
		$resultado["estado"] = 1;
	}else{
		$resultado["estado"] = 0;
	}
}else{
	$resultado["estado"] = 0;
}
$data = json_encode($resultado);

echo $data;
?>