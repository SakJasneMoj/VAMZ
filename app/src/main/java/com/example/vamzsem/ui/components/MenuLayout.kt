package com.example.vamzsem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vamzsem.ui.screens.DrawerBody
import com.example.vamzsem.ui.screens.Screen
import com.example.vamzsem.ui.utils.GradientBackground
import com.example.vamzsem.ui.utils.WindowInfo
import com.example.vamzsem.viewModel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun MenuLayout(
    windowInfo: WindowInfo,
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    content: @Composable () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val profileName by profileViewModel.profileName.collectAsState()

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
                    DrawerHeader(profileName)
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

@Composable
fun DrawerHeader(profileName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome, $profileName", style = MaterialTheme.typography.h6)
        }
    }
}
