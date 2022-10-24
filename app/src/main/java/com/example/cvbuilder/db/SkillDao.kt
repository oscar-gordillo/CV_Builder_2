package com.example.cvbuilder.db

import androidx.room.*
@Dao
interface SkillDao {
    @Insert
    suspend fun addSkill(skill:Skill)
    @Query("select * from Skill order by id")
    suspend fun getAllSkills():List<Skill>
    @Update
    suspend fun updateSkill(skill:Skill)
    @Delete
    suspend fun deleteSkill(skill:Skill)
}