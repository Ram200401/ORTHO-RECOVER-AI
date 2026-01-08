package com.example.ortho_shoulder_ai.data.model

data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val surgery: String,
    val weeksPostOp: Int,
    val notes: String,
    val lastROM: String,
    val currentPlan: List<String>
)

object PatientRepository {
    private val rotatorCuffPatients = listOf(
        Patient(
            id = "RC001",
            name = "Sarah Johnson",
            age = 45,
            gender = "Female",
            surgery = "Rotator Cuff Repair",
            weeksPostOp = 3,
            notes = "Patient reporting mild pain (3/10) during extension. Swelling has decreased significantly since last week. Incision site looks clean.",
            lastROM = "115° Flexion",
            currentPlan = listOf("Pendulum Exercise (3×10)", "Wall Climb (3×10)", "Wall Slides (2×20)")
        ),
        Patient(
            id = "RC002",
            name = "Michael Chen",
            age = 52,
            gender = "Male",
            surgery = "Rotator Cuff Repair",
            weeksPostOp = 6,
            notes = "Excellent progress. Patient can now perform daily activities with minimal discomfort. ROM improving steadily.",
            lastROM = "135° Flexion",
            currentPlan = listOf("External Rotation (3×15)", "Scapular Squeezes (3×12)", "Resistance Band Pulls (2×15)")
        ),
        Patient(
            id = "RC003",
            name = "Emily Rodriguez",
            age = 38,
            gender = "Female",
            surgery = "Rotator Cuff Repair",
            weeksPostOp = 2,
            notes = "Early recovery phase. Some swelling present but within normal range. Patient following protocol well.",
            lastROM = "95° Flexion",
            currentPlan = listOf("Passive ROM (4×10)", "Pendulum Exercise (3×10)", "Ice therapy (3×daily)")
        ),
        Patient(
            id = "RC004",
            name = "David Martinez",
            age = 48,
            gender = "Male",
            surgery = "Rotator Cuff Repair",
            weeksPostOp = 8,
            notes = "Near full recovery. Patient ready to progress to strengthening phase. No pain during exercises.",
            lastROM = "155° Flexion",
            currentPlan = listOf("Resistance Training (3×12)", "Internal Rotation (3×15)", "Overhead Press (2×10)")
        ),
        Patient(
            id = "RC005",
            name = "Jennifer Lee",
            age = 41,
            gender = "Female",
            surgery = "Rotator Cuff Repair",
            weeksPostOp = 4,
            notes = "Moderate progress. Patient experiencing some stiffness in the morning. Recommended heat therapy before exercises.",
            lastROM = "120° Flexion",
            currentPlan = listOf("Active ROM (3×12)", "Shoulder Circles (3×10)", "Stretching Routine (2×daily)")
        )
    )

    private val arthroscopyPatients = listOf(
        Patient(
            id = "SA001",
            name = "Robert Thompson",
            age = 55,
            gender = "Male",
            surgery = "Shoulder Arthroscopy",
            weeksPostOp = 2,
            notes = "Minimal invasive procedure recovery going well. Small incisions healing nicely. Patient reports reduced pain.",
            lastROM = "110° Flexion",
            currentPlan = listOf("Gentle ROM (4×10)", "Pendulum Exercise (3×8)", "Passive Stretching (2×daily)")
        ),
        Patient(
            id = "SA002",
            name = "Amanda White",
            age = 43,
            gender = "Female",
            surgery = "Shoulder Arthroscopy",
            weeksPostOp = 5,
            notes = "Rapid recovery. Patient already returning to light activities. Minimal swelling and good mobility.",
            lastROM = "140° Flexion",
            currentPlan = listOf("Active ROM (3×15)", "Light Resistance (3×12)", "Functional Movements (2×10)")
        ),
        Patient(
            id = "SA003",
            name = "James Wilson",
            age = 39,
            gender = "Male",
            surgery = "Shoulder Arthroscopy",
            weeksPostOp = 1,
            notes = "Early post-op phase. Patient managing pain well with medication. Following rest and ice protocol.",
            lastROM = "85° Flexion",
            currentPlan = listOf("Rest and Ice (hourly)", "Passive ROM (2×8)", "Sling use as needed")
        ),
        Patient(
            id = "SA004",
            name = "Lisa Anderson",
            age = 47,
            gender = "Female",
            surgery = "Shoulder Arthroscopy",
            weeksPostOp = 7,
            notes = "Excellent outcome. Patient has regained most of pre-surgery function. Ready for discharge planning.",
            lastROM = "160° Flexion",
            currentPlan = listOf("Maintenance Exercises (3×10)", "Sport-Specific Training (2×weekly)", "Home Program")
        ),
        Patient(
            id = "SA005",
            name = "Kevin Brown",
            age = 50,
            gender = "Male",
            surgery = "Shoulder Arthroscopy",
            weeksPostOp = 3,
            notes = "Steady progress. Patient compliant with home exercise program. Some residual stiffness in external rotation.",
            lastROM = "125° Flexion",
            currentPlan = listOf("ROM Exercises (3×12)", "External Rotation Focus (3×15)", "Stretching (2×daily)")
        )
    )

    private val impingementPatients = listOf(
        Patient(
            id = "IS001",
            name = "Patricia Davis",
            age = 44,
            gender = "Female",
            surgery = "Impingement Surgery",
            weeksPostOp = 4,
            notes = "Decompression successful. Patient reports significant reduction in overhead pain. ROM improving.",
            lastROM = "130° Flexion",
            currentPlan = listOf("Scapular Stabilization (3×12)", "Overhead ROM (3×10)", "Posture Training (daily)")
        ),
        Patient(
            id = "IS002",
            name = "Thomas Garcia",
            age = 51,
            gender = "Male",
            surgery = "Impingement Surgery",
            weeksPostOp = 6,
            notes = "Very good progress. Patient can now perform overhead activities without pain. Strength returning.",
            lastROM = "145° Flexion",
            currentPlan = listOf("Strengthening (3×15)", "Overhead Press (3×10)", "Functional Training (2×weekly)")
        ),
        Patient(
            id = "IS003",
            name = "Maria Hernandez",
            age = 36,
            gender = "Female",
            surgery = "Impingement Surgery",
            weeksPostOp = 2,
            notes = "Early recovery. Patient experiencing expected post-op discomfort. Inflammation controlled with medication.",
            lastROM = "100° Flexion",
            currentPlan = listOf("Gentle ROM (4×8)", "Anti-inflammatory Protocol", "Ice Therapy (3×daily)")
        ),
        Patient(
            id = "IS004",
            name = "Christopher Taylor",
            age = 49,
            gender = "Male",
            surgery = "Impingement Surgery",
            weeksPostOp = 8,
            notes = "Near complete recovery. Patient cleared for return to work. Minimal restrictions remaining.",
            lastROM = "165° Flexion",
            currentPlan = listOf("Maintenance Program (3×weekly)", "Work Conditioning", "Ergonomic Training")
        ),
        Patient(
            id = "IS005",
            name = "Nancy Moore",
            age = 42,
            gender = "Female",
            surgery = "Impingement Surgery",
            weeksPostOp = 5,
            notes = "Good progress overall. Some difficulty with overhead reaching. Continuing to work on scapular mechanics.",
            lastROM = "135° Flexion",
            currentPlan = listOf("Scapular Exercises (3×12)", "Overhead Training (3×10)", "Stretching Routine (2×daily)")
        )
    )

    fun getPatientsForSurgery(surgery: String): List<Patient> {
        return when (surgery) {
            "Rotator Cuff Repair" -> rotatorCuffPatients
            "Shoulder Arthroscopy" -> arthroscopyPatients
            "Impingement Surgery" -> impingementPatients
            else -> emptyList()
        }
    }

    fun getPatientById(patientId: String): Patient? {
        return (rotatorCuffPatients + arthroscopyPatients + impingementPatients)
            .find { it.id == patientId }
    }
}
