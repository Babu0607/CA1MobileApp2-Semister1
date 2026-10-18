package com.example.studentregistrationca1_78853

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class StudentViewModel(application: Application): AndroidViewModel(application) {

    private val studentDao = StudentDatabase.getDatabase(application).studentDao()

    private val _searchResult = MutableStateFlow<String?>(null)
    val searchResult: StateFlow<String?> = _searchResult
    fun insertStudent(
        name: String, email: String, course: String, year: Int,
        onResult: (Boolean, String) -> Unit) {
        if (year !in 1..4) {
            onResult(false, "should be between 1 to 4")
            return
        }

        viewModelScope.launch {
            val newStudent = Student(name = name, email = email, course = course, year = year)
            studentDao.insertStudent(newStudent)
            onResult(true, "well done you're successfully registred")
        }
    }

    fun searchStudentById(idStr: String){
        val id = idStr.toIntOrNull()
        if (id == null){
            _searchResult.value = "enter your student number thank you."
            return
        }

        viewModelScope.launch{
            val student = studentDao.getStudentById(id)
            if (student != null){
                _searchResult.value = "found! Name: ${student.name}, Course: ${student.course}"
            } else {
                _searchResult.value = "sorry: student with ID $id couldn't find database"
            }
        }
    }
}
 