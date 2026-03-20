package com.example.readforme.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.readforme.ScreenState
import com.example.readforme.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    currentScreen: ScreenState,
    onNavigate: (ScreenState) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "ReadForMe",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }

                NavigationDrawerItem(
                    label = { Text("Modo Câmera") },
                    selected = currentScreen is ScreenState.Camera,
                    onClick = {
                        onNavigate(ScreenState.Camera)
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Leitura") },
                    selected = currentScreen is ScreenState.Reading,
                    onClick = {
                        onNavigate(ScreenState.Reading)
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Configurações") },
                    selected = currentScreen is ScreenState.Settings,
                    onClick = {
                        onNavigate(ScreenState.Settings)
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {

        Scaffold(
            onMenuClick = {
                scope.launch { drawerState.open() }
            },
            content = content
        )
    }
}