<?php

require_once '../dao/EurekaDao.php';
$cuenta = $_REQUEST["Idcuenta"];
$dao = new EurekaDao();
$rec = $dao->ConsultarCuenta($cuenta);
if ($rec) {
    $rec["estado"] = 1;
} else {
    $rec["estado"] = 0;
}
$data = json_encode($rec);
echo $data;
?>

