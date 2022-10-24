package com.example.cvbuilder.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Experience(
    val company:String,
    val city:String,
    val state:String,
    val startDate:String,
    val endDate:String,
    val jobTitle:String,
    val description:String
):java.io.Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
