package com.example.ortho_shoulder_ai.ui.screens.exercises

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.video.*
import androidx.core.content.ContextCompat
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.util.PoseDetectorHelper
import com.example.ortho_shoulder_ai.util.ShoulderPoseAnalyzer
import com.example.ortho_shoulder_ai.util.ExerciseSessionManager
import com.example.ortho_shoulder_ai.util.SessionState
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.UseCaseGroup
import kotlin.math.roundToInt

@Composable
fun ExerciseSessionScreen(
    exerciseName: String,
    onBack: () -> Unit,
    onSessionComplete: (String, Int, Int) -> Unit,
    onContactPhysio: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    var isPaused by remember { mutableStateOf(false) }
    var secondsElapsed by remember { mutableLongStateOf(0L) }
    
    var currentROM by remember { mutableIntStateOf(0) }
    var maxROM by remember { mutableIntStateOf(0) }
    var currentReps by remember { mutableIntStateOf(0) }
    var feedbackMessage by remember { mutableStateOf("Positioning body...") }
    var poseResults by remember { mutableStateOf<PoseLandmarkerResult?>(null) }
    
    val sessionManager = remember { 
        ExerciseSessionManager(exerciseName) {
            // Callback when calibration is complete
            println("Calibration Complete!")
        }
    }

    val poseDetectorHelper = remember {
        PoseDetectorHelper(
            context = context,
            poseLandmarkerHelperListener = object : PoseDetectorHelper.LandmarkerListener {
                override fun onError(error: String) {
                    feedbackMessage = "Error: $error"
                }

                override fun onResults(result: PoseLandmarkerResult, inferenceTime: Long, imageHeight: Int, imageWidth: Int) {
                    poseResults = result
                    val analysis = sessionManager.processFrame(result)
                    currentROM = analysis.currentAngle
                    if (currentROM > maxROM) {
                        maxROM = currentROM
                    }
                    currentReps = analysis.repCount
                    feedbackMessage = analysis.feedbackMessage
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        sessionManager.start()
    }

    // Timer logic
    LaunchedEffect(isPaused) {
        while (!isPaused) {
            kotlinx.coroutines.delay(1000)
            secondsElapsed++
        }
    }

    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA) }
    var hasCameraPermission by remember {

        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }
    var hasAudioPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.RECORD_AUDIO
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            hasCameraPermission = permissions[android.Manifest.permission.CAMERA] ?: hasCameraPermission
            hasAudioPermission = permissions[android.Manifest.permission.RECORD_AUDIO] ?: hasAudioPermission
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            )
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // --- 1. FULL-SCREEN CAMERA PREVIEW ---
        if (hasCameraPermission) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                lifecycleOwner = lifecycleOwner,
                cameraSelector = cameraSelector,
                isRecording = !isPaused, // Record when not paused
                onRecordingFinished = { file ->
                    // Handle finished recording (e.g., save to gallery or upload)
                    println("Recording finished: ${file.absolutePath}")
                },
                onImageAnalyzed = { imageProxy ->
                    poseDetectorHelper.detectLiveStream(
                        imageProxy,
                        cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA
                    )
                }
            )
            
            // --- AI SKELETON OVERLAY ---
            PoseOverlay(
                modifier = Modifier.fillMaxSize(),
                poseResults = poseResults
            )

            // --- SCANNING LASER EFFECT ---
            if (sessionManager.state == SessionState.CALIBRATING) {
                ScanningLaserOverlay(progress = sessionManager.calibrationProgress)
            }
        } else {

            // Placeholder/Error view for permission denied
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Camera Permission Required",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        // --- 2. AI GUIDANCE OVERLAY (Shoulder Focused) ---
        // Skeleton removed as requested. Keeping only the floating feedback and indicators.

        // --- 2. AI GUIDANCE OVERLAY removed ---

        // --- 4. HEADER SECTION ---

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(Color.White.copy(alpha = 0.15f), CircleShape)
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = exerciseName,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Reps: 10 | Sets: 3",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }

                IconButton(
                    onClick = { /* Info action */ },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.15f), CircleShape)
                ) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pulse-style Feedback Bar
            AnimatedVisibility(
                visible = feedbackMessage.isNotEmpty(),
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Surface(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    if (feedbackMessage.contains("straight", true)) Color.Yellow 
                                    else if (feedbackMessage.contains("Calibrating", true)) Color.Cyan
                                    else Color(0xFF2ECC71), 
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = feedbackMessage,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // --- 5. PROGRESS & TIMER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 200.dp)
                .align(Alignment.BottomCenter)
        ) {
            // Circular Progress (Reps)
            Box(
                modifier = Modifier.size(120.dp).align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = 0.4f,
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF2ECC71),
                    strokeWidth = 10.dp,
                    trackColor = Color.White.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$currentReps", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text(text = "REPS", color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            // ROM Indicator
            Box(
                modifier = Modifier.size(100.dp).align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = (currentROM / 180f).coerceIn(0f, 1f),
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF3B82F6),
                    strokeWidth = 8.dp,
                    trackColor = Color.White.copy(alpha = 0.1f),
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$currentROM°", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = "ANGLE", color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Timer
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.AccessTime, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = formatTimer(secondsElapsed), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // --- 6. BOTTOM CONTROL PANEL (Glassmorphism Effect) ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White.copy(alpha = 0.15f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ControlCircleButton(
                        icon = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                        label = if (isPaused) "Resume" else "Pause",
                        onClick = { isPaused = !isPaused }
                    )

                    ControlCircleButton(
                        icon = Icons.Default.Stop,
                        label = "Stop",
                        containerColor = Color(0xFFE74C3C),
                        onClick = {
                            // Calculate hypothetical accuracy for now
                            val accuracy = if (currentReps > 0) 92 else 0 
                            onSessionComplete(exerciseName, accuracy, maxROM)
                        }
                    )

                    ControlCircleButton(
                        icon = Icons.Default.Cached,
                        label = "Switch",
                        onClick = {
                            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            } else {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            }
                        }
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = onContactPhysio,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(2.dp, Color(0xFFE74C3C))
                ) {
                    Text(text = "Contact Physio", color = Color(0xFFE74C3C), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    cameraSelector: CameraSelector,
    isRecording: Boolean,
    onRecordingFinished: (File) -> Unit,
    onImageAnalyzed: (ImageProxy) -> Unit
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var recording: Recording? by remember { mutableStateOf(null) }
    val videoCapture = remember {
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
            .build()
        VideoCapture.withOutput(recorder)
    }

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }
        },
        modifier = modifier,
        update = { previewView ->
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                    .build()
                    .also {
                        it.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                            onImageAnalyzed(imageProxy)
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis,
                        videoCapture
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )


    LaunchedEffect(isRecording) {
        if (isRecording) {
            val name = "Exercise_" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis()) + ".mp4"
            val file = File(context.cacheDir, name)
            val outputOptions = FileOutputOptions.Builder(file).build()

            var recordingBuilder = videoCapture.output
                .prepareRecording(context, outputOptions)
            
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                recordingBuilder = recordingBuilder.withAudioEnabled()
            }

            recording = recordingBuilder
                .start(ContextCompat.getMainExecutor(context)) { event ->

                    if (event is VideoRecordEvent.Finalize) {
                        if (!event.hasError()) {
                            onRecordingFinished(file)
                        }
                    }
                }
        } else {
            recording?.stop()
            recording = null
        }
    }
}

@Composable
fun ControlCircleButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    containerColor: Color = Color.White.copy(alpha = 0.1f),
    iconColor: Color = Color.White,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(56.dp),
            shape = CircleShape,
            color = containerColor,
            onClick = onClick
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(24.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

private fun formatTimer(seconds: Long): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", mins, secs)
}

@Composable
fun PoseOverlay(
    modifier: Modifier = Modifier,
    poseResults: PoseLandmarkerResult?
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Canvas(modifier = modifier) {
        poseResults?.let { results ->
            if (results.landmarks().isNotEmpty()) {
                val landmarks = results.landmarks()[0]
                
                // Draw Professional Connections (Bones)
                val connections = listOf(
                    11 to 13, 13 to 15, // Left arm
                    12 to 14, 14 to 16, // Right arm
                    11 to 23, 12 to 24, // Torso sides
                    11 to 12, 23 to 24  // Shoulders and Hips
                )

                connections.forEach { (startIdx, endIdx) ->
                    val start = landmarks[startIdx]
                    val end = landmarks[endIdx]
                    
                    if (start.presence().orElse(0f) > 0.5f && end.presence().orElse(0f) > 0.5f) {
                        drawLine(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.White.copy(alpha = 0.4f), Color.White.copy(alpha = 0.1f))
                            ),
                            start = Offset(start.x() * size.width, start.y() * size.height),
                            end = Offset(end.x() * size.width, end.y() * size.height),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }

                // Draw Landmark Joints with Pulse
                landmarks.forEachIndexed { index, landmark ->
                    if (landmark.presence().orElse(0f) > 0.5f && index in listOf(11, 12, 13, 14, 15, 16, 23, 24)) {
                        val isShoulder = index == 11 || index == 12
                        val x = landmark.x() * size.width
                        val y = landmark.y() * size.height
                        
                        // Joint Dot
                        drawCircle(
                            color = if (isShoulder) Color(0xFF3B82F6) else Color.White,
                            radius = if (isShoulder) 6.dp.toPx() else 4.dp.toPx(),
                            center = Offset(x, y)
                        )
                        
                        // Clinical Pulse Effect for Shoulders
                        if (isShoulder) {
                            drawCircle(
                                color = Color(0xFF3B82F6).copy(alpha = pulseAlpha),
                                radius = 14.dp.toPx(),
                                center = Offset(x, y),
                                style = Stroke(width = 1.dp.toPx())
                            )
                            drawCircle(
                                color = Color(0xFF3B82F6).copy(alpha = pulseAlpha * 0.5f),
                                radius = 20.dp.toPx(),
                                center = Offset(x, y)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScanningLaserOverlay(progress: Float) {
    val infiniteTransition = rememberInfiniteTransition(label = "laser")
    val laserY = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "laserPosition"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val y = laserY.value * size.height
        
        // Horizontal Laser Line
        drawLine(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Transparent, Color.Cyan, Color.Transparent)
            ),
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 4.dp.toPx()
        )
        
        // Glow effect
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Cyan.copy(alpha = 0.1f), Color.Transparent),
                startY = y - 40.dp.toPx(),
                endY = y + 40.dp.toPx()
            ),
            topLeft = Offset(0f, y - 40.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(size.width, 80.dp.toPx())
        )
        
        // Calibration Progress Bar (Bottom)
        val barWidth = size.width * 0.6f
        val barX = (size.width - barWidth) / 2
        val barY = size.height - 100.dp.toPx()
        
        drawRoundRect(
            color = Color.White.copy(alpha = 0.2f),
            topLeft = Offset(barX, barY),
            size = androidx.compose.ui.geometry.Size(barWidth, 8.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
        )
        
        drawRoundRect(
            color = Color.Cyan,
            topLeft = Offset(barX, barY),
            size = androidx.compose.ui.geometry.Size(barWidth * progress, 8.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
        )
    }
}
