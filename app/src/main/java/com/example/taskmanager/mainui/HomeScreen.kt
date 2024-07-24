package com.example.taskmanager.mainui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.R
import com.example.taskmanager.data.Item
import com.example.taskmanager.ui.theme.TaskManagerTheme
//import com.example.taskmanager.viewmodel.ReminderViewModel
import com.example.taskmanager.viewmodel.ScreenModeViewModel
import com.example.taskmanager.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier=Modifier.fillMaxSize()){

    //val reminderViewModel: ReminderViewModel = viewModel(factory = ReminderViewModel.Factory)
    val viewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory)
    val screenViewModel: ScreenModeViewModel = viewModel(factory = ScreenModeViewModel.Factory)
    val itemState = viewModel.items.collectAsState()
    val itemList = itemState.value
    var showDialog by remember {mutableStateOf(false)}
    val toggleState = screenViewModel.isDarkTheme.collectAsState().value

    TaskManagerTheme(darkTheme=toggleState){
        Scaffold(
            topBar = {TopBar(screenViewModel, toggleState)},
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    showDialog=true
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
                }
            },
            modifier= Modifier
                .fillMaxSize()
        ){ innerPadding ->
            Task(itemList, modifier.padding(innerPadding), viewModel)
        }

        if(showDialog){
            AddTaskDialog(onDismiss = {showDialog=false},viewModel,modifier)
        }
    }
}

@Composable
fun TopBar(viewModel: ScreenModeViewModel, toggleState: Boolean){

    //val toggleState = viewModel.isDarkTheme.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tasks",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(10.dp))
            Image(
                painter = painterResource(id = R.drawable.task),
                contentDescription = "Task",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Switch(checked = toggleState, onCheckedChange = {
                viewModel.toggleDarkTheme()
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(onDismiss: ()->Unit, viewModel: TaskViewModel, modifier: Modifier = Modifier){

    var task by remember{ mutableStateOf("")}
    var reminderDate = remember{ mutableStateOf("")}
    var reminderTime = remember {mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()
    var showTime = remember{ mutableStateOf(false)}
    var showDate = remember{ mutableStateOf(false)}

    Dialog(onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 8.dp
        ) {
            Column() {
                TextField(value = task,
                    onValueChange = {task = it},
                    label = { Text("Add Task")
                    },
                    modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                ElevatedButton(onClick = {
                    showDate.value = true
                },
                modifier = Modifier.padding(10.dp).wrapContentSize()
                ) {
                    Text("Set Date")
                }
                ElevatedButton(onClick = {
                    showTime.value = true
                },
                    modifier = Modifier.padding(10.dp).wrapContentSize()
                ) {
                    Text("Set Time")
                }
                /*
                TextField(value = reminderDate.value,
                    onValueChange = {reminderDate.value = it},
                    label = { Text("Set Reminder Date")
                        Modifier.padding(8.dp)
                    },
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(8.dp)
                )
                TextField(value = reminderTime.value,
                    onValueChange = {reminderTime.value = it},
                    label = { Text("Set Reminder Time")
                        Modifier.padding(8.dp)
                    },
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(8.dp)
                )*/
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 10.dp)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            val item = Item(0, task, reminderDate.value, reminderTime.value)
                            coroutineScope.launch {
                                viewModel.insertItem(item)
                            }
                            //reminderViewModel.updateReminder(reminderDate.value, reminderTime.value)
                            onDismiss()
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 10.dp)
                    )
                    {
                        Text("Add")
                    }
                }
            }
        }
    }
    if(showDate.value){
        SetDate(reminderDate=reminderDate, showDate=showDate)}

    if(showTime.value)
        SetTime(reminderTime=reminderTime, showTime=showTime)
}

