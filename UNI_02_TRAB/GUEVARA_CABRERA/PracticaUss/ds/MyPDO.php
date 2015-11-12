<?php

class MyPDO {

    protected $pdo = null;
    public $database = "mysql";
    public $host = "localhost";
    public $username = "root";
    public $password = "";
    public $databasename = "eurekabank";

    function __construct() {
        try {
            $this->pdo = new PDO("mysql:host=$this->host;dbname=$this->databasename", $this->username, $this->password);
            $this->pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $this->pdo->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER);
            $this->pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            throw new Exception("Error de acceso a la base de datos");
        }
    }

}

?>