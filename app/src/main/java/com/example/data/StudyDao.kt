package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyDao {
    @Query("SELECT * FROM study_days ORDER BY dateIso DESC")
    fun getAllStudyDays(): Flow<List<StudyDay>>

    @Query("SELECT * FROM study_days WHERE dateIso = :dateIso LIMIT 1")
    suspend fun getByDate(dateIso: String): StudyDay?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudyDay(studyDay: StudyDay)

    @Query("DELETE FROM study_days WHERE dateIso = :dateIso")
    suspend fun deleteByDate(dateIso: String)

    @Query("SELECT COUNT(*) FROM study_days")
    fun getStudyDaysCount(): Flow<Int>
}
