<?php
// backend/models/Patient.php

class Patient
{
    private $conn;
    private $table_name = "patients";

    public $user_id;
    public $age;
    public $gender;
    public $surgery_type;
    public $physiotherapist_id;
    public $connection_code;

    public function __construct($db)
    {
        $this->conn = $db;
    }

    public function create()
    {
        $query = "INSERT INTO " . $this->table_name . " (user_id, age, gender, surgery_type, connection_code) VALUES (:user_id, :age, :gender, :surgery, :code)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":user_id", $this->user_id);
        $stmt->bindParam(":age", $this->age);
        $stmt->bindParam(":gender", $this->gender);
        $stmt->bindParam(":surgery", $this->surgery_type);
        $stmt->bindParam(":code", $this->connection_code);
        return $stmt->execute();
    }

    public function connect($patient_id, $code)
    {
        $query = "UPDATE " . $this->table_name . " SET connected = 1 WHERE user_id = :id AND connection_code = :code AND connected = 0";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":id", $patient_id);
        $stmt->bindParam(":code", $code);
        $stmt->execute();
        return $stmt->rowCount() > 0;
    }
}
?>