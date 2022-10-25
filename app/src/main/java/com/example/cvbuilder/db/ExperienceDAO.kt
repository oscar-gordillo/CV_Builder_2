package com.example.cvbuilder.db

import androidx.room.*
@Dao
interface ExperienceDAO {
    @Insert
    suspend fun addExperience(experience:Experience)
    @Query("select * from Experience order by id")
    suspend fun getAllExperience():List<Experience>
    @Update
    suspend fun updateExperience(experience:Experience)
    @Delete
    suspend fun deleteExperience(experience:Experience)
}