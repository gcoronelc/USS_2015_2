<?php

require_once '../dao/EurekaDao.php';

// Parámetros
$cuenta = $_REQUEST["cuenta"];
$importe= $_REQUEST["importe"];
$clave = $_REQUEST["clave"];
$empleado=$_REQUEST["empleado"];

// Proceso
$dao = new EurekaDao();
$rec = $dao->registrarRetiro($cuenta,$importe,$clave,$empleado);

if ($rec) {
    $rec["estado"] = 1; // Existe
} else {
    $rec["estado"] = 0; // No existe
}
$data = json_encode($rec);
// Retorno
echo $data;
?>