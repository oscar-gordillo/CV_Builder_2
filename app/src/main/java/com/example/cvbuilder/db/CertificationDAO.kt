package com.example.cvbuilder.db

import androidx.room.*
@Dao
interface CertificationDAO {
    @Insert
    suspend fun addCertification(certification:Certification)
    @Query("select * from Certification order by id")
    suspend fun getAllCertification():List<Certification>
    @Update
    suspend fun updateCertification(certification:Certification)
    @Delete
    suspend fun deleteCertification(certification:Certification)
}