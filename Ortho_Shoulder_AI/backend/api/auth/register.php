<?php
// backend/api/auth/register.php

require_once __DIR__ . '/../../config/db.php';
require_once __DIR__ . '/../../models/User.php';
require_once __DIR__ . '/../../models/Physio.php';
require_once __DIR__ . '/../../models/Patient.php';
require_once __DIR__ . '/../../utils/Response.php';

header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

$db = (new Database())->getConnection();
$user = new User($db);

$data = json_decode(file_get_contents("php://input"));

if (!empty($data->name) && (!empty($data->email) || !empty($data->phone)) && !empty($data->password) && !empty($data->role)) {
    $user->name = $data->name;
    $user->email = $data->email ?? null;
    $user->phone = $data->phone ?? null;
    $user->role = $data->role;
    $user->password = $data->password;

    // Check if user exists
    if ($user->email && $user->findByEmail($user->email)) {
        Response::json(false, "Email already exists", null, 400);
    }
    if ($user->phone && $user->findByPhone($user->phone)) {
        Response::json(false, "Phone already exists", null, 400);
    }

    $userId = $user->create();
    if ($userId) {
        if ($user->role === 'physiotherapist') {
            $physio = new Physio($db);
            $physio->user_id = $userId;
            $physio->qualification = $data->qualification ?? '';
            $physio->specialization = $data->specialization ?? '';
            $physio->create();
        } else {
            $patient = new Patient($db);
            $patient->user_id = $userId;
            $patient->age = $data->age ?? null;
            $patient->gender = $data->gender ?? null;
            $patient->surgery_type = $data->surgery_type ?? '';
            $patient->connection_code = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 8);
            $patient->create();
        }

        Response::json(true, "User registered successfully", ["id" => $userId], 201);
    } else {
        Response::json(false, "Unable to register user", null, 500);
    }
} else {
    Response::json(false, "Incomplete data", null, 400);
}
?>