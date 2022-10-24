package com.example.cvbuilder.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Education(
    val schoolName:String,
    val city:String,
    val state:String,
    val degree:String,
    val completionDate:String
):java.io.Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
