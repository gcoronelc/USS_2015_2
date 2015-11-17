<?php

require_once '../dao/EurekaDao.php';

//parametros
$usuario = $_REQUEST['usuario'];
$password = $_REQUEST['password'];

// Proceso
$dao = new EurekaDao();
$resultado = $dao->login($usuario, $password);
if($resultado){
    $resultado["estado"] = 1;
}else{
    $resultado["estado"] = 0;
}
$data = json_encode($resultado);

// Retorno
echo $data;
?>