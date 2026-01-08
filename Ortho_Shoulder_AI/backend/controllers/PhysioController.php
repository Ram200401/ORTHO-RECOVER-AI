<?php
// backend/controllers/PhysioController.php

require_once __DIR__ . '/../models/Patient.php';
require_once __DIR__ . '/../models/Exercise.php';
require_once __DIR__ . '/../utils/Response.php';

class PhysioController
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function getPatients($physio_id)
    {
        $query = "SELECT u.id, u.name, u.email, u.phone, p.age, p.surgery_type, p.connected 
                  FROM users u 
                  JOIN patients p ON u.id = p.user_id 
                  WHERE p.physiotherapist_id = :id";
        $stmt = $this->db->prepare($query);
        $stmt->bindParam(":id", $physio_id);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function addPatient($physio_id, $patient_email, $patient_phone = null)
    {
        // Logic to link an existing patient via connections
        // For MVP, we assumed Physio "adds" a patient which generates a code.
        // Actually, the requirement says "Add patient (basic info only) -> generate code"
        // This implies creating a placeholder user or just a patient record.
        // Let's assume the Physio provides email/phone, we check if they exist, 
        // if not we create a user. If yes, we link them.

        $query = "UPDATE patients SET physiotherapist_id = :physio 
                  WHERE (user_id IN (SELECT id FROM users WHERE email = :email OR phone = :phone))
                  AND physiotherapist_id IS NULL";
        $stmt = $this->db->prepare($query);
        $stmt->execute([":physio" => $physio_id, ":email" => $patient_email, ":phone" => $patient_phone]);

        return $stmt->rowCount() > 0;
    }
}
?>