package com.hayat.ai.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val savedKey = ApiKeyStore.get(context)
    val apiKey = remember { mutableStateOf(savedKey) }
    val saved = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "API Key",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = apiKey.value,
            onValueChange = {
                apiKey.value = it
                saved.value = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter API Key") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                ApiKeyStore.save(context, apiKey.value)
                saved.value = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        if (saved.value) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "API Key saved",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
