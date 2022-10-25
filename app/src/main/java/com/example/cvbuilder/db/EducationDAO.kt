package com.example.cvbuilder.db

import androidx.room.*
@Dao
interface EducationDAO {
    @Insert
    suspend fun addEducation(education:Education)
    @Query("select * from Education order by id")
    suspend fun getAllEducation():List<Education>
    @Update
    suspend fun updateEducation(education:Education)
    @Delete
    suspend fun deleteEducation(education:Education)
}