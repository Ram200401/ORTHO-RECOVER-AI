<?php
// backend/models/Exercise.php

class ExercisePlan
{
    private $conn;
    public function __construct($db)
    {
        $this->conn = $db;
    }

    public function createPlan($physio_id, $patient_id, $title, $description, $start_date, $end_date)
    {
        $query = "INSERT INTO exercise_plans (physiotherapist_id, patient_id, title, description, start_date, end_date) 
                  VALUES (:physio_id, :patient_id, :title, :description, :start, :end)";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([
            ":physio_id" => $physio_id,
            ":patient_id" => $patient_id,
            ":title" => $title,
            ":description" => $description,
            ":start" => $start_date,
            ":end" => $end_date
        ]);
        return $this->conn->lastInsertId();
    }

    public function addExercise($plan_id, $name, $video_url, $reps, $instructions)
    {
        $query = "INSERT INTO exercises (plan_id, name, video_url, repetitions, instructions) 
                  VALUES (:plan_id, :name, :url, :reps, :instr)";
        $stmt = $this->conn->prepare($query);
        return $stmt->execute([
            ":plan_id" => $plan_id,
            ":name" => $name,
            ":url" => $video_url,
            ":reps" => $reps,
            ":instr" => $instructions
        ]);
    }
}
?>