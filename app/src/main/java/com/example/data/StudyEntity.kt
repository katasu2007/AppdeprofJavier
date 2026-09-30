package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_days")
data class StudyDay(
    @PrimaryKey
    val dateIso: String, // Format: "YYYY-MM-DD", e.g. "2026-09-30"
    val timestampMillis: Long = System.currentTimeMillis()
)
