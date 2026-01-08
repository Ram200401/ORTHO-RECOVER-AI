<?php
// backend/api/auth/login.php

require_once __DIR__ . '/../../config/db.php';
require_once __DIR__ . '/../../models/User.php';
require_once __DIR__ . '/../../utils/Response.php';
require_once __DIR__ . '/../../utils/JWT.php';

header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

$db = (new Database())->getConnection();
$userModel = new User($db);

$data = json_decode(file_get_contents("php://input"));

if ((!empty($data->email) || !empty($data->phone)) && !empty($data->password)) {
    $user = null;
    if (!empty($data->email)) {
        $user = $userModel->findByEmail($data->email);
    } else {
        $user = $userModel->findByPhone($data->phone);
    }

    if ($user && password_verify($data->password, $user['password_hash'])) {
        $config = require __DIR__ . '/../../config/jwt_config.php';
        $token = JWT::encode([
            "id" => $user['id'],
            "role" => $user['role'],
            "name" => $user['name']
        ], $config['secret'], $config['expiry']);

        Response::json(true, "Login successful", [
            "token" => $token,
            "user" => [
                "id" => $user['id'],
                "name" => $user['name'],
                "role" => $user['role']
            ]
        ]);
    } else {
        Response::json(false, "Invalid credentials", null, 401);
    }
} else {
    Response::json(false, "Incomplete data", null, 400);
}
?>