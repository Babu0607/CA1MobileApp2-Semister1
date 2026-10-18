package com.example.studentregistrationca1_78853

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val studentId: Int=0,
    val name: String,
    val email: String,
    val course: String,
    val year: Int
)