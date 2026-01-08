<?php
// backend/models/Physio.php

class Physio
{
    private $conn;
    private $table_name = "physiotherapists";

    public $user_id;
    public $qualification;
    public $specialization;

    public function __construct($db)
    {
        $this->conn = $db;
    }

    public function create()
    {
        $query = "INSERT INTO " . $this->table_name . " (user_id, qualification, specialization) VALUES (:user_id, :qual, :spec)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":user_id", $this->user_id);
        $stmt->bindParam(":qual", $this->qualification);
        $stmt->bindParam(":spec", $this->specialization);
        return $stmt->execute();
    }
}
?>