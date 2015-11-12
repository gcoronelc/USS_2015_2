<?php

require_once '../dao/EurekaDao.php';

// Parámetros
$IdCuenta = $_REQUEST["IdCuenta"];

// Proceso
$dao = new EurekaDao();
$lista = $dao->ListaMovimientoCuenta($IdCuenta);
$data = "";
if ($lista) {
    $data = json_encode($lista);
}
$data = trim($data);
if (strlen($data) > 0) {
    $data = "{\"cuentas\":" . $data . "}";
}
echo $data;
?>

