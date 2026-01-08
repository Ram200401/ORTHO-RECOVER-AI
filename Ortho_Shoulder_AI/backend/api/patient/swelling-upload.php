<?php
// backend/api/patient/swelling-upload.php

require_once __DIR__ . '/../../config/db.php';
require_once __DIR__ . '/../../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../../utils/Response.php';

header("Content-Type: application/json; charset=UTF-8");

$auth = new AuthMiddleware();
$user = $auth->handle('patient');

if (!empty($_FILES['image'])) {
    $targetDir = __DIR__ . "/../../uploads/swelling/";
    $fileName = time() . "_" . basename($_FILES["image"]["name"]);
    $targetFilePath = $targetDir . $fileName;
    $fileType = pathinfo($targetFilePath, PATHINFO_EXTENSION);

    $allowTypes = array('jpg', 'png', 'jpeg', 'gif');
    if (in_array(strtolower($fileType), $allowTypes)) {
        if (move_uploaded_file($_FILES["image"]["tmp_name"], $targetFilePath)) {
            $db = (new Database())->getConnection();
            $query = "INSERT INTO swelling_uploads (patient_id, image_path) VALUES (:id, :path)";
            $stmt = $db->prepare($query);
            $stmt->execute([":id" => $user['id'], ":path" => $fileName]);

            Response::json(true, "Image uploaded successfully", ["file" => $fileName], 201);
        } else {
            Response::json(false, "Error uploading file", null, 500);
        }
    } else {
        Response::json(false, "Invalid file type", null, 400);
    }
} else {
    Response::json(false, "No image uploaded", null, 400);
}
?>