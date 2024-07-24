package com.example.taskmanager.mainui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.taskmanager.data.Item
import com.example.taskmanager.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun UpdateTask(viewModel: TaskViewModel, item: Item, onDismiss: () -> Unit){

    var task by remember{ mutableStateOf(item.task) }
    var reminderDate by remember{ mutableStateOf(item.reminderDate)}
    var reminderTime by remember{ mutableStateOf(item.reminderTime) }
    val coroutineScope = rememberCoroutineScope()

    Dialog(onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
        ){
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 8.dp
        ){
            Column() {

                TextField(value = task,
                    onValueChange = {task = it},
                    label = { Text("Edit Task")
                    },
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                TextField(value = reminderDate,
                    onValueChange = {reminderDate = it},
                    label = { Text("Edit Reminder Date")
                        Modifier.padding(8.dp)
                    },
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                TextField(value = reminderTime,
                    onValueChange = {reminderTime = it},
                    label = { Text("Edit Reminder Time")
                        Modifier.padding(8.dp)
                    },
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 10.dp)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                item.task = task
                                item.reminderDate = reminderDate
                                item.reminderTime = reminderTime
                                viewModel.updateItem(item)
                            }
                            onDismiss()
                        },
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp)
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}