package com.example.taskmanager.mainui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.ui.theme.TaskManagerTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetDate(reminderDate: MutableState<String>, showDate:MutableState<Boolean>){

    val dateState = rememberDatePickerState(Date().time) // Date().time will show the correct date
   // var selectedData by remember{ mutableStateOf(userDate) }

        DatePickerDialog(
            onDismissRequest = {
                showDate.value=false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDate.value=false
                }) {
                    reminderDate.value = convert(dateState.selectedDateMillis!!)
                     Text(text="Set Date")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDate.value = false
                })
                {
                    Text("Cancel")
                }
            },
            modifier = Modifier.fillMaxSize()
            )
        {
         DatePicker(state = dateState)
        }
        Spacer(modifier = Modifier.height(20.dp))
    //Text(convert(dateState.selectedDateMillis!!))
}



private fun convert(date:Long):String{
    val dateNew = Date(date) // converts the long to date object
    val formatter = SimpleDateFormat.getDateInstance() //Creates a date formatter that uses the default date and time format for the current locale.

    return formatter.format(dateNew) // converts the  Date object to a string
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetTime(reminderTime: MutableState<String>, showTime: MutableState<Boolean>){
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

        TimePickerDialog(onDismiss = { showTime.value=false }, onConfirm = {showTime.value = false}) {
            TimePicker(state = timePickerState)
        }

    reminderTime.value = "${timePickerState.hour}:${timePickerState.minute}"
   // Text("${timePickerState.hour}:${timePickerState.minute}")
}

@Composable
fun TimePickerDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, content: @Composable () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {content()},
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text("Set Time")
            }
        })

}

@Preview(showBackground = true)
@Composable
fun DateAndTimePreview() {
    TaskManagerTheme {
    }

}

@Preview(showBackground = true)
@Composable
fun TimePickerDialogPreview() {
    TaskManagerTheme {
    }
}

