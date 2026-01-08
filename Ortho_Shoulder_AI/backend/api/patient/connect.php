<?php
// backend/api/patient/connect.php

require_once __DIR__ . '/../../config/db.php';
require_once __DIR__ . '/../../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../../models/Patient.php';

header("Content-Type: application/json; charset=UTF-8");

$auth = new AuthMiddleware();
$user = $auth->handle('patient');

$db = (new Database())->getConnection();
$patientModel = new Patient($db);

$data = json_decode(file_get_contents("php://input"));

if (!empty($data->code)) {
    // Note: The code is unique but it's generated FOR the patient during register.
    // The requirement says: Physio adds patient -> backend generates unique code.
    // Patient enters code -> account is linked.
    // This means Physio needs to provide the code to the patient.

    $query = "UPDATE patients SET connected = 1 WHERE user_id = :uid AND connection_code = :code";
    $stmt = $db->prepare($query);
    $stmt->execute([":uid" => $user['id'], ":code" => $data->code]);

    if ($stmt->rowCount() > 0) {
        Response::json(true, "Connected successfully");
    } else {
        Response::json(false, "Invalid connection code or already connected", null, 400);
    }
} else {
    Response::json(false, "Code is required", null, 400);
}
?>