<?php

require_once '../ds/MyPDO.php';

class EurekaDao extends MyPDO {

    public function iniciarSesion($usuario,$clave) {
        $rec = null;
        try {
            
            $query = "select e.chr_emplcodigo, 
            concat_ws(' ',e.vch_emplpaterno,e.vch_emplmaterno,e.vch_emplnombre) as nombre
            from empleado e
            where e.vch_emplusuario=?
            and e.vch_emplclave=?";
            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $usuario);
            $stm->bindParam(2, $clave);
            $stm->execute();
            $rec = $stm->fetch(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $rec;
    }
    
    public function consultarCliene($codigo) {
        $rec = null;
        try {
            
            $query = "select chr_cliecodigo, vch_cliepaterno, vch_cliematerno, 
				vch_clienombre, chr_cliedni, vch_clieciudad, vch_cliedireccion, 
				vch_clietelefono, vch_clieemail 
				from cliente where chr_cliecodigo = ?";
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
            $query = "SELECT 
 	concat_ws(' ',cl.vch_cliepaterno,cl.vch_cliematerno,cl.vch_clienombre) as nombres,
	tm.vch_tipodescripcion, 
	m.dec_moviimporte, 
	m.dtt_movifecha, 
	c.dec_cuensaldo
FROM tipomovimiento tm
JOIN movimiento m ON tm.chr_tipocodigo = m.chr_tipocodigo 
JOIN cuenta c on c.chr_cuencodigo=m.chr_cuencodigo
JOIN cliente  cl on cl.chr_cliecodigo=c.chr_cliecodigo
where m.chr_cuencodigo = ? and 	tm.vch_tipodescripcion !='Cargo por Movimiento'
order by m.dtt_movifecha desc ";

            $stm = $this->pdo->prepare($query);
            $stm->bindParam(1, $cuenta);
            $stm->execute();
            $lista = $stm->fetchAll(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $lista;
    }

public function registrarDeposito($cuenta,$importe,$empleado) {
        $estado = 'hola';
        try {
            $stm = $this->pdo->prepare("call usp_deposito(?,?,?)");
            $stm->bindParam(1, $cuenta);
            $stm->bindParam(2, $importe);
            $stm->bindParam(3, $empleado);
            // llamar al procedimiento almacenado
            $stm->execute();
            $estado = $stm->fetch(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $estado;
    }
	

public function registrarRetiro($cuenta,$importe,$clave,$empleado) {
        $estado = 'hola';
        try {
            $stm = $this->pdo->prepare("call usp_retiro(?,?,?,?)");
            $stm->bindParam(1, $cuenta);
            $stm->bindParam(2, $importe);
            $stm->bindParam(3, $clave);
            $stm->bindParam(4, $empleado);
            // llamar al procedimiento almacenado
            $stm->execute();
            $estado = $stm->fetch(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw $e;
        }
        return $estado;
    }
	
}
?>
