<?php

require_once '../dao/EurekaDao.php';

$Idcuenta = $_REQUEST["idcuenta"];
$IdEmpledo = $_REQUEST["idempleado"];
$Monto = $_REQUEST["monto"];

$dao = new EurekaDao();
$stm = $dao->HacerRetiro($Idcuenta, $IdEmpledo, $Monto);
$data = json_encode($stm);
echo $data;
?>

