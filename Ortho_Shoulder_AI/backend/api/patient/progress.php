<?php
// backend/api/patient/progress.php

require_once __DIR__ . '/../../config/db.php';
require_once __DIR__ . '/../../middleware/AuthMiddleware.php';

header("Content-Type: application/json; charset=UTF-8");

$auth = new AuthMiddleware();
$user = $auth->handle('patient');

$db = (new Database())->getConnection();

// Fetch summary data
$romQuery = "SELECT * FROM rom_sessions WHERE patient_id = :id ORDER BY session_time DESC";
$logQuery = "SELECT l.*, e.name FROM exercise_logs l JOIN exercises e ON l.exercise_id = e.id WHERE l.patient_id = :id ORDER BY completed_at DESC";

$romStmt = $db->prepare($romQuery);
$logStmt = $db->prepare($logQuery);

$romStmt->execute([":id" => $user['id']]);
$logStmt->execute([":id" => $user['id']]);

Response::json(true, "Progress fetched", [
    "rom_sessions" => $romStmt->fetchAll(PDO::FETCH_ASSOC),
    "exercise_logs" => $logStmt->fetchAll(PDO::FETCH_ASSOC)
]);
?>