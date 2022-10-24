package com.example.cvbuilder.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skill(
    val type:String,
    val name:String,
    val percentage:Int
):java.io.Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
