<?php
// backend/utils/Response.php

class Response
{
    public static function json($success, $message, $data = null, $status = 200)
    {
        http_response_code($status);
        echo json_encode([
            "success" => $success,
            "message" => $message,
            "data" => $data
        ]);
        exit();
    }
}
?>