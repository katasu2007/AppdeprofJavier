package com.example.data

import kotlinx.coroutines.flow.Flow

class StudyRepository(private val studyDao: StudyDao) {
    val allStudyDays: Flow<List<StudyDay>> = studyDao.getAllStudyDays()
    val totalDaysCount: Flow<Int> = studyDao.getStudyDaysCount()

    suspend fun recordStudy(dateIso: String, timestampMillis: Long = System.currentTimeMillis()) {
        studyDao.insertStudyDay(StudyDay(dateIso = dateIso, timestampMillis = timestampMillis))
    }

    suspend fun removeStudy(dateIso: String) {
        studyDao.deleteByDate(dateIso)
    }

    suspend fun isStudiedOn(dateIso: String): Boolean {
        return studyDao.getByDate(dateIso) != null
    }
}
