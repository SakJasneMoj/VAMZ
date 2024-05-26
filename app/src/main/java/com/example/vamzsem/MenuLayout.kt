package com.example.vamzsem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Settings



@Composable
fun MenuLayout(
    windowInfo: WindowInfo,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB3E5FC), // Light blue
                                Color(0xFF81D4FA), // Medium blue
                                Color(0xFF4FC3F7)  // Darker blue
                            )
                        )
                    )
            ) {
                Column {
                    DrawerHeader()
                    DrawerBody(
                        items = listOf(
                            MenuItem(
                                id = Screen.Calories.route,
                                title = "Calories",
                                contentDescription = "Go to calories screen",
                                icon = Icons.Default.Home
                            ),
                            MenuItem(
                                id = Screen.Sports.route,
                                title = "Sports",
                                contentDescription = "Go to sports screen",
                                icon = Icons.Default.DirectionsRun
                            ),
                            MenuItem(
                                id = Screen.Calendar.route,
                                title = "Calendar",
                                contentDescription = "Go to calendar screen",
                                icon = Icons.Default.CalendarToday
                            ),
                            MenuItem(
                                id = Screen.Settings.route,
                                title = "Settings",
                                contentDescription = "Go to settings screen",
                                icon = Icons.Default.Settings
                            ),
                        ),
                        onItemClick = { menuItem ->
                            scope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate(menuItem.id) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Gradient background
            GradientBackground()

            // Content area
            content()

            // Top App Bar overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent),
                contentAlignment = Alignment.TopCenter
            ) {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            }
        }
    }
}
