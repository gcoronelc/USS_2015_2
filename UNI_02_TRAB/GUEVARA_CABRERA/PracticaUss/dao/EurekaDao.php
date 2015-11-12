<?php

require_once '../ds/MyPDO.php';

class EurekaDao extends MyPDO {

    public function IniciarLogin($cuenta, $clave) {
        $rec = null;
        try {
            $query = "call IniciarLogin(?,?)";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $cuenta);
            $stm->bindParam(2, $clave);
            $stm->execute();
            $rec = $stm->fetch(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $rec;
    }

    public function ConsultarCuenta($cuenta) {
        $lista = null;
        try {
            $query = "call ConsultarCuenta(?)";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $cuenta);
            $stm->execute();
            $lista = $stm->fetch(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $lista;
    }

    public function ListaMovimientosEmpleado($IdEmpleado) {
        $lista = null;
        try {
            $query = "call ListaMovimientosEmpleado(?)";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $IdEmpleado);
            $stm->execute();
            $lista = $stm->fetchAll(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $lista;
    }
    
    public function ListaMovimientoCuenta($IdCuenta) {
        $lista = null;
        try {
            $query = "call ListaMovimientoCuenta(?)";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $IdCuenta);
            $stm->execute();
            $lista = $stm->fetchAll(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $lista;
    }

    public function HacerRetiro($Idcuenta, $IdEmpledo, $Monto) {
        $execute1 = null;
        try {
            $query = "call HacerRetiro(?,?,?)";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $Idcuenta);
            $stm->bindParam(2, $IdEmpledo);
            $stm->bindParam(3, $Monto);
            $execute1 = $stm->execute();
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        }
        return $execute1;
    }

    public function HacerDeposito($Idcuenta, $IdEmpledo, $Monto) {
        /* @var $execute type */
        $execute = null;
        try {
            $query = "call HacerDeposito(?,?,?)";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $Idcuenta);
            $stm->bindParam(2, $IdEmpledo);
            $stm->bindParam(3, $Monto);
            $execute = $stm->execute();
        } catch (Exception $exc) {
            echo $exc->getTraceAsString();
        }
        return $execute;
    }

}

?>
