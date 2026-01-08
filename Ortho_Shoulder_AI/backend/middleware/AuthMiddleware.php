<?php
// backend/middleware/AuthMiddleware.php

require_once __DIR__ . '/../utils/JWT.php';
require_once __DIR__ . '/../utils/Response.php';

class AuthMiddleware
{
    public function handle($required_role = null)
    {
        $headers = getallheaders();
        $authHeader = $headers['Authorization'] ?? '';

        if (preg_match('/Bearer\s(\S+)/', $authHeader, $matches)) {
            $token = $matches[1];
            $config = require __DIR__ . '/../config/jwt_config.php';
            $payload = JWT::decode($token, $config['secret']);

            if ($payload) {
                if ($required_role && $payload['role'] !== $required_role) {
                    Response::json(false, "Forbidden: Access denied for role " . $payload['role'], null, 403);
                }
                return $payload;
            }
        }

        Response::json(false, "Unauthorized: Invalid or missing token", null, 401);
    }
}
?>