package com.example.readforme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.readforme.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val speeds = TtsOptions.speeds
    val dropdownOptions = TtsOptions.language

    var speedIndex by remember { mutableIntStateOf(2) }

    var expanded by remember { mutableStateOf(false) }

    // Default - Room
    val speed by viewModel.speed.collectAsState()
    val language by viewModel.language.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "Configurações",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Change speed locution button
            Text("Velocidade", style = MaterialTheme.typography.labelLarge)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(
                    onClick = {
                        if (speedIndex > 0) speedIndex--

                        viewModel.setUISpeed(speedIndex)
                        viewModel.setSpeed(speeds[speedIndex])
                    }
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Diminuir")
                }

                Text(
                    text = "${speed}x",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = {
                        if (speedIndex < speeds.lastIndex) speedIndex++

                        viewModel.setUISpeed(speedIndex)
                        viewModel.setSpeed(speeds[speedIndex])
                    }
                ) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Aumentar")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Change language button
            Text("Idioma", style = MaterialTheme.typography.labelLarge)

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = language,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    dropdownOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                expanded = false

                                viewModel.setUILanguage(option)
                                viewModel.setLanguage(option)
                            }
                        )
                    }
                }
            }
        }
    }
}