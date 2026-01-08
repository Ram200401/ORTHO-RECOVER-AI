<?php
// backend/api/physio/patients.php

require_once __DIR__ . '/../../config/db.php';
require_once __DIR__ . '/../../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../../controllers/PhysioController.php';

header("Content-Type: application/json; charset=UTF-8");

$auth = new AuthMiddleware();
$user = $auth->handle('physiotherapist');

$db = (new Database())->getConnection();
$physioController = new PhysioController($db);

$patients = $physioController->getPatients($user['id']);
Response::json(true, "Patients fetched", $patients);
?>