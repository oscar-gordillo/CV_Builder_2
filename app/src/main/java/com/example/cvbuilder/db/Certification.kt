package com.example.cvbuilder.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Certification(
    val name:String,
    val organization:String,
    val date:String,
    val expirationDate:String
):java.io.Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
