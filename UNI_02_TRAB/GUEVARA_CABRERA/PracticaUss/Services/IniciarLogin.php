<?php
require_once '../dao/EurekaDao.php';

// Parámetros
$cuenta = $_REQUEST["usuario"];
$clave = $_REQUEST["clave"];

// Proceso
$dao = new EurekaDao();
$rec = $dao->IniciarLogin($cuenta,$clave);
if ($rec) {
    $rec["estado"] = 1; // Existe
} else {
    $rec["estado"] = 0; // No existe
}
$data = json_encode($rec);

// Retorno
echo $data;
?>

