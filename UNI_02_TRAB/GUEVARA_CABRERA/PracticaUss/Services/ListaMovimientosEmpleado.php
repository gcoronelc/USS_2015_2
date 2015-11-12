<?php

require_once '../dao/EurekaDao.php';

// Parámetros
$cuenta = $_REQUEST["IdEmpleado"];

// Proceso
$dao = new EurekaDao();
$lista = $dao->ListaMovimientosEmpleado($cuenta);
$data = "";
if ($lista) {
    $data = json_encode($lista);
}
$data = trim($data);
if (strlen($data) > 0) {
    $data = "{\"movimientos\":" . $data . "}";
}

// Retorno
echo $data;
?>

