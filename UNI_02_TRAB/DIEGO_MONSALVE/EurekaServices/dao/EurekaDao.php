<?php

require_once '../ds/MyPDO.php';

class EurekaDao extends MyPDO {

    public function consultarCliente($codigo) {
        $rec = null;
        try {
            $query = "select chr_cliecodigo as codigo, vch_cliepaterno as paterno, vch_cliematerno as materno, 
				vch_clienombre as nombre, chr_cliedni as DNI, vch_clieciudad as ciudad, vch_cliedireccion as direccion, 
				vch_clietelefono as telefono, vch_clieemail as email from cliente where chr_cliecodigo = ?";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $codigo);
            $stm->execute();
            $rec = $stm->fetch(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $rec;
    }

    public function consultarMovimientos($cuenta) {
        $lista = null;
        try {
            $query = "SELECT M.int_movinumero as num, c.chr_cuencodigo as cuenta, c.dec_cuensaldo as saldo, c.chr_cliecodigo as cliente, M.dtt_movifecha as fecha, M.dec_moviimporte as importe, TM.vch_tipodescripcion as tipo "; 
			$query .= "FROM MOVIMIENTO as M, TIPOMOVIMIENTO as TM, CUENTA as C ";
			$query .= "WHERE M.chr_tipocodigo = TM.chr_tipocodigo and C.vch_cuenestado = 'ACTIVO' and M.chr_cuencodigo = ? and C.chr_cuencodigo = ? ";
			$query .= "ORDER BY M.int_movinumero DESC;";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $cuenta);
			$stm->bindParam(2, $cuenta);
            $stm->execute();
            $lista = $stm->fetchAll(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $lista;
    }
	
	

	public function login($usuario, $password){
		$resultado = null;
		try{
			$consulta = "SELECT * FROM EMPLEADO WHERE vch_emplusuario = ? and vch_emplclave = ?;";
			$stm = $this->pdo->prepare($consulta);
			$stm->bindParam(1, $usuario);
			$stm->bindParam(2, $password);
			$stm->execute();
			$resultado = $stm->fetch(PDO::FETCH_ASSOC);
		}catch(Exception $e){
			throw $e;
		}
		
		return $resultado;
	}
	public function consultarCuenta($cuenta, $password){
		$resultado = null;
		try{
			$consulta = "SELECT * FROM CUENTA WHERE chr_cuencodigo = ? and chr_cuenclave = ?";
			$stm = $this->pdo->prepare($consulta);
			$stm->bindParam(1, $cuenta);
			$stm->bindParam(2, $password);
			$stm->execute();
			$resultado = $stm->fetch(PDO::FETCH_ASSOC);
		}catch(Exception $e){
			throw $e;
		}
		
		return $resultado;
	}
	
	public function insertarMovimiento($cuenta, $fechaMovimiento, $codEmpleado, $tipoMovimiento, $importe){
		$resultado = null;
		$cuentaRef = null;
		try{
			$consulta = "SELECT COUNT(int_movinumero)+1 as movimientos FROM MOVIMIENTO WHERE chr_cuencodigo = :cuenta;";
			$stm = $this->pdo->prepare($consulta);
			$stm->bindParam(':cuenta', $cuenta);
			$stm->execute();
			$row = $stm->fetch(PDO::FETCH_ASSOC);
			$numMovimiento = $row['movimientos'];
		
			$insert = "INSERT INTO MOVIMIENTO VALUES";
			$insert .= "(:cuenta, :numMov, :fechaMov, :codEmpleado, :tipoMov, :importe, :cuentaRef)";
			$stm = $this->pdo->prepare($insert);
			$stm->bindParam(':cuenta', $cuenta);
			$stm->bindParam(':numMov', $numMovimiento);
			$stm->bindParam(':fechaMov', $fechaMovimiento);
			$stm->bindParam(':codEmpleado', $codEmpleado);
			$stm->bindParam(':tipoMov', $tipoMovimiento);
			$stm->bindParam(':importe', $importe);
			$stm->bindParam(':cuentaRef', $cuentaRef);
			$stm->execute();
			
			$update = "";
			if($tipoMovimiento=='003'){
				$update = "UPDATE cuenta SET dec_cuensaldo = dec_cuensaldo + :importe WHERE chr_cuencodigo = :cuenta;";
			}else if($tipoMovimiento=='004'){
				$update = "UPDATE cuenta SET dec_cuensaldo = dec_cuensaldo - :importe WHERE chr_cuencodigo = :cuenta;";
			}
			$stm = $this->pdo->prepare($update);
			$stm->bindParam(':importe', $importe);
			$stm->bindParam(':cuenta', $cuenta);
			$stm->execute();
			
			$resultado = 1;
		}catch(Exception $e){
			throw $e;
		}
		return $resultado;
	}
	
}

?>
