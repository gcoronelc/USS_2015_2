<?php

require_once '../dao/EurekaDao.php';

// Parámetros
$cuenta = $_REQUEST["cuenta"];

// Proceso
$dao = new EurekaDao();
$lista = $dao->consultarMovimientos($cuenta);
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