package com.example.ortho_shoulder_ai.data.model

data class Exercise(
    val id: String,
    val name: String,
    val instructions: List<String>,
    val safetyTip: String
)

object ExerciseRepo {
    private val exercises = listOf(
        Exercise(
            id = "pendulum",
            name = "Pendulum Exercise (Shoulder)",
            instructions = listOf(
                "Stand beside a table or chair and support yourself with your unaffected arm.",
                "Bend slightly forward at the waist and let the affected arm hang down freely.",
                "Gently swing the arm forward and backward like a pendulum.",
                "Next, swing the arm side to side.",
                "Finally, make small circular movements clockwise and anticlockwise."
            ),
            safetyTip = "Do not use shoulder muscles actively. The movement should be gentle and pain-free."
        ),
        Exercise(
            id = "wall_pushups",
            name = "Wall Push-Ups",
            instructions = listOf(
                "Stand facing a wall at arm’s length.",
                "Place both palms on the wall at shoulder height and shoulder-width apart.",
                "Slowly bend your elbows and lean your body toward the wall.",
                "Keep your body straight and heels on the floor.",
                "Push back to the starting position."
            ),
            safetyTip = "Avoid locking your elbows. Stop if you feel shoulder or elbow pain."
        ),
        Exercise(
            id = "cross_body",
            name = "Cross-Body Shoulder Stretch",
            instructions = listOf(
                "Stand or sit upright with your back straight.",
                "Lift the affected arm and bring it across your chest.",
                "Use your other hand to gently pull the arm closer to your body.",
                "Hold the stretch for 15–20 seconds.",
                "Slowly release and return to the starting position."
            ),
            safetyTip = "Stretch should be gentle. Do not force the arm if pain increases."
        ),
        Exercise(
            id = "elbow_wrist",
            name = "Elbow, Wrist & Hand Movement Exercise",
            instructions = listOf(
                "Sit comfortably with your arm supported.",
                "Slowly bend and straighten your elbow fully.",
                "Rotate your forearm so the palm faces up, then down.",
                "Bend your wrist up and down gently.",
                "Open and close your fingers, making a fist and then spreading them wide."
            ),
            safetyTip = "Move within a pain-free range. Avoid jerky or fast movements."
        )
    )

    fun getExerciseById(id: String): Exercise? = exercises.find { it.id == id }
    
    fun getExerciseByName(name: String): Exercise? = exercises.find { it.name == name }
}
