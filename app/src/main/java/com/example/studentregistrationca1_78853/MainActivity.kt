package com.example.studentregistrationca1_78853

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentregistrationca1_78853.ui.theme.StudentRegistrationCA178853Theme

class MainActivity : ComponentActivity() {

    private val studentViewModel: StudentViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentRegistrationCA178853Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation (
                        viewModel = studentViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: StudentViewModel, modifier: Modifier = Modifier){
    var currentScreen by remember{ mutableStateOf("home")}
    when (currentScreen){
        "home" -> HomeScreen(
            onNavigateToRegister = {currentScreen = "register"},
            onNavigateToSearch = {currentScreen = "search"},
            modifier = modifier
        )
        "register" -> RegistrationScreen(
            viewModel = viewModel,
            onBackToHome = {currentScreen = "home"}
        )
        "search" -> SearchScreen(
            viewModel = viewModel,
            onBackToHome = {currentScreen = "home"},
            modifier = modifier
        )
    }
}

@Composable
fun RegistrationScreen(viewModel: StudentViewModel, modifier: Modifier = Modifier){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var yearStr by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Student Registration",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = { Text("Full Name")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text ("Email Address")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )


        OutlinedTextField(
            value = course,
            onValueChange = {course = it},
            label = {Text ("Course")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )


        OutlinedTextField(
            value = yearStr,
            onValueChange = {yearStr = it},
            label = {Text ("Year 1-4")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

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
        if(message.isNotBlank()){
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text= message, color=MaterialTheme.colorScheme.primary)

        }
    }
}

@Composable
fun SearchScreen(viewModel: StudentViewModel, onBackToHome: () -> Unit, modifier: Modifier = Modifier){
    var searchId by remember { mutableStateOf("") }
    val searchResult by viewModel.searchResult.collectAsState(initial = null)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Please search Student ID",
            style = MaterialTheme.typography.headlineMedium,
            modifier =  Modifier.padding(24.dp)
        )
        OutlinedTextField(
            value = searchId,
            onValueChange = {searchId = it},
            label = {Text("please student ID")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )
        Button(
            onClick = {
                viewModel.searchStudentById(searchId)
            },
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(bottom=12.dp)
        ) {
            Text("Search")
        }
        Button(
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Back to Home page")
        }
        if (searchResult != null){
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = searchResult ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}