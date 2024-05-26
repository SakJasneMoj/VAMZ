package com.example.vamzsem

import android.widget.CalendarView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun InlineDatePicker(onDateSelected: (String) -> Unit) {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    AndroidView(
        factory = { context ->
            CalendarView(context).apply {
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val selectedDate = sdf.format(
                        GregorianCalendar(year, month, dayOfMonth).time
                    )
                    onDateSelected(selectedDate)
                }
            }
        }
    )
}
