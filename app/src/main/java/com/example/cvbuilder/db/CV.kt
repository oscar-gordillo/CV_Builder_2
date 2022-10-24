package com.example.cvbuilder.db

import androidx.room.Entity
import androidx.room.PrimaryKey


data class CV(
    val name :String,
    val phone :String,
    val email :String,
    val title:String,
    val description:String,
    val skills:List<Skill>,
    val experiences:List<Experience>,
    val educations:List<Education>,
    val certifications:List<Certification>,
    val template:String
)
