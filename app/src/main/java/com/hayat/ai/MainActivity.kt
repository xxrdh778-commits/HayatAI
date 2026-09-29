package com.hayat.ai

import android.Manifest
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hayat.ai.settings.SettingsScreen

class MainActivity : ComponentActivity() {

    private val microphonePermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HayatAIApp(
                onRequestMicrophonePermission = {
                    microphonePermissionLauncher.launch(
                        Manifest.permission.RECORD_AUDIO
                    )
                }
            )
        }
    }
}

@Composable
fun HayatAIApp(onRequestMicrophonePermission: () -> Unit = {}) {
    val showSettings = remember { mutableStateOf(false) }
    var transcript by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }

    val context = androidx.compose.ui.platform.LocalContext.current

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            if (showSettings.value) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    TextButton(
                        onClick = { showSettings.value = false }
                    ) {
                        Text("← Back")
                    }

                    SettingsScreen()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hayat AI",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your Voice AI Agent",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Ready to listen",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            onRequestMicrophonePermission()
                        }
                    ) {
                        Text("🎙 Start Listening")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = {
                            isListening = false
                        }
                    ) {
                        Text("Stop Listening")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = if (transcript.isBlank()) "Ready to listen" else transcript,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isListening) "Status: Listening..." else "Status: Idle",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Current action: None",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    TextButton(
                        onClick = { showSettings.value = true }
                    ) {
                        Text("Settings")
                    }
                }
            }
        }
    }
}
