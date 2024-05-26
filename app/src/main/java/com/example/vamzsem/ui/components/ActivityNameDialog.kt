package com.example.vamzsem.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun ActivityNameDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var activityName by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Enter Activity Name") },
        text = {
            androidx.compose.material.TextField(
                value = activityName,
                onValueChange = { activityName = it }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(activityName.text) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
