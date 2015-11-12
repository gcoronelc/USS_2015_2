<?php

require_once '../dao/EurekaDao.php';

// Parámetros
$usuario = $_REQUEST["usuario"];
$clave = $_REQUEST["clave"];
//$codigo='00003';

// Proceso
$dao = new EurekaDao();
$rec = $dao->iniciarSesion($usuario,$clave);
if ($rec) {
    $rec["estado"] = 1; // Existe
} else {
    $rec["estado"] = 0; // No existe
}
$data = json_encode($rec);

// Retorno
echo $data;
?>