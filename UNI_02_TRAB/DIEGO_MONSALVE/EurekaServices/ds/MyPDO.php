<?php

class MyPDO {

    protected $pdo = null;

    function __construct() {
        try {
            // Parámetros
            $param = parse_ini_file("../conf/connect.ini", true);
            $dsn = $param["database"]["01"];
            $username = $param["database"]["02"];
            $passwd = $param["database"]["03"];
            // Conexión
            $this->pdo = new PDO($dsn, $username, $passwd);
            $this->pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $this->pdo->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER);
            $this->pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw new Exception("Error de acceso a la base de datos");
        }
    }

}

?>