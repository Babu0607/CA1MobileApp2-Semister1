package com.example.studentregistrationca1_78853

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao

interface StudentDataAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Query("Select * from students Where studentId = :id")
    suspend fun getStudentById(id: Int): Student?
    
}