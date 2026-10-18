package com.example.studentregistrationca1_78853

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun RegistrationScreen(viewModel: StudentViewModel, onBackToHome: () -> Unit){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var yearStr by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Student Registration",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = { Text("Full Name")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text ("Email Address")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = course,
            onValueChange = {course = it},
            label = {Text ("Course name")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = yearStr,
            onValueChange = {yearStr = it},
            label = {Text ("Year 1-4")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val year = yearStr.toIntOrNull() ?:1
                if (name.isNotBlank() && email.isNotBlank() && course.isNotBlank()){
                    viewModel.insertStudent(name, email, course, year) {success, msg ->
                        message = msg
                        if (success){
                            name = ""
                            email = ""
                            course = ""
                            yearStr = ""
                        }
                    }
                } else{
                    message = "Please make sure need to fill it out"
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ){
            Text("Please Register Students")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button (
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Back to Home Page")

        }

        if(message.isNotBlank()){
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text= message, color=MaterialTheme.colorScheme.primary)

        }
    }
 }
}
