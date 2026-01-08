<?php
// backend/api/physio/exercise-plan.php

require_once __DIR__ . '/../../config/db.php';
require_once __DIR__ . '/../../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../../models/Exercise.php';

header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

$auth = new AuthMiddleware();
$user = $auth->handle('physiotherapist');

$db = (new Database())->getConnection();
$planModel = new ExercisePlan($db);

$data = json_decode(file_get_contents("php://input"));

if (!empty($data->patient_id) && !empty($data->title) && !empty($data->exercises)) {
    $planId = $planModel->createPlan(
        $user['id'],
        $data->patient_id,
        $data->title,
        $data->description ?? '',
        $data->start_date ?? null,
        $data->end_date ?? null
    );

    if ($planId) {
        foreach ($data->exercises as $ex) {
            $planModel->addExercise($planId, $ex->name, $ex->video_url ?? '', $ex->repetitions, $ex->instructions ?? '');
        }
        Response::json(true, "Exercise plan created", ["id" => $planId], 201);
    } else {
        Response::json(false, "Failed to create plan", null, 500);
    }
} else {
    Response::json(false, "Incomplete data", null, 400);
}
?>