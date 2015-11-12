<?php

require_once '../dao/EurekaDao.php';

// Parámetros
$codigo = $_REQUEST["codigo"];
//$codigo='00003';

// Proceso
$dao = new EurekaDao();
$rec = $dao->consultarCliene($codigo);
if ($rec) {
    $rec["estado"] = 1; // Existe
} else {
    $rec["estado"] = 0; // No existe
}
$data = json_encode($rec);

// Retorno
echo $data;
?>