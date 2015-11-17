<?php

require_once '../dao/EurekaDao.php';

//parametros
$cuenta = $_REQUEST['cuenta'];
$password = $_REQUEST['password'];

// Proceso
$dao = new EurekaDao();
$resultado = $dao->consultarCuenta($cuenta, $password);
if($resultado){
    $resultado["estado"] = 1;
}else{
    $resultado["estado"] = 0;
}
$data = json_encode($resultado);

// Retorno
echo $data;
?>