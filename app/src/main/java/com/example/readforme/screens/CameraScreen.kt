package com.example.readforme.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.readforme.MainViewModel
import com.example.readforme.ui.theme.Background
import com.example.readforme.ui.theme.Primary
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.ExecutorService

@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraScreen(
    cameraExecutor: ExecutorService,
    viewModel: MainViewModel = hiltViewModel()
) {
    val recognizedText by viewModel.recognizedText.collectAsState()

    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    var cameraEnabled by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onPermissionResult(isGranted)
        if (isGranted) cameraEnabled = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraEnabled) {
            AndroidView(
                factory = { ctx ->

                    val previewView = PreviewView(ctx)

                    val cameraProviderFuture =
                        ProcessCameraProvider.getInstance(ctx)

                    cameraProviderFuture.addListener({

                        val cameraProvider =
                            cameraProviderFuture.get()

                        val preview = Preview.Builder()
                            .build()
                            .also {
                                it.setSurfaceProvider(
                                    previewView.surfaceProvider
                                )
                            }

                        val imageAnalyzer =
                            ImageAnalysis.Builder()
                                .setBackpressureStrategy(
                                    ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                                )
                                .build()

                        val recognizer =
                            TextRecognition.getClient(
                                TextRecognizerOptions.DEFAULT_OPTIONS
                            )

                        imageAnalyzer.setAnalyzer(cameraExecutor) { imageProxy ->

                            val mediaImage = imageProxy.image

                            if (cameraEnabled && mediaImage != null) {

                                val image =
                                    InputImage.fromMediaImage(
                                        mediaImage,
                                        imageProxy.imageInfo.rotationDegrees
                                    )

                                recognizer.process(image)
                                    .addOnSuccessListener {
                                        if (cameraEnabled) {
                                            viewModel.onTextRecognized(it.text)
                                        }
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }
                            }
                        }

                        cameraProvider.unbindAll()

                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageAnalyzer
                        )

                    }, ContextCompat.getMainExecutor(ctx))

                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Button(
                    onClick = {
                        viewModel.onSpeakClicked(recognizedText)
                    }) {
                    Text("Falar")
                }

                Button(
                    onClick = {
                        cameraEnabled = false
                        viewModel.clearRecognizedText()
                        viewModel.stopSpeak()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }

        if (!cameraEnabled) {
            Box(
                modifier = Modifier
                    .background(
                        color = Background
                    )
                    .fillMaxSize()
                    .padding(24.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "ReadForMe",
                        fontSize = 32.sp,
                        color = Primary
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = "Aponte a câmera para um texto\npara iniciar a leitura.",
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        textAlign = Center
                    )

                    Spacer(modifier = Modifier.padding(32.dp))

                    Button(
                        onClick = {

                            if (
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                cameraEnabled = true
                            } else {
                                permissionLauncher.launch(
                                    Manifest.permission.CAMERA
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary
                        )
                    ) {
                        Text("Iniciar câmera")
                    }
                }
            }
        }
        Text(
            text = recognizedText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}