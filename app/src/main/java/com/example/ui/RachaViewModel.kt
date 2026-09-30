package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.MotivationalQuote
import com.example.data.MotivationalQuotesProvider
import com.example.data.StudyDay
import com.example.data.StudyRepository
import com.example.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DayStatus(
    val dateIso: String,
    val dayNameShort: String,
    val dayNumber: Int,
    val isToday: Boolean,
    val isStudied: Boolean
)

data class RachaUiState(
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val totalDaysStudied: Int = 0,
    val isStudiedToday: Boolean = false,
    val todayFormatted: String = "",
    val lastSevenDays: List<DayStatus> = emptyList(),
    val currentQuote: MotivationalQuote = MotivationalQuotesProvider.getRandomQuote(),
    val studiedDaysThisWeek: Int = 0,
    val celebrationTrigger: Long = 0L // Timestamp trigger for celebratory pulse/haptics
)

class RachaViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    private val _currentQuote = MutableStateFlow(MotivationalQuotesProvider.getRandomQuote())
    private val _celebrationTrigger = MutableStateFlow(0L)

    val uiState: StateFlow<RachaUiState> = combine(
        repository.allStudyDays,
        _currentQuote,
        _celebrationTrigger
    ) { studyDays, quote, celebrationTimestamp ->
        val studiedDateSet = studyDays.map { it.dateIso }.toSet()
        val todayIso = DateUtils.getTodayIso()
        val isTodayStudied = studiedDateSet.contains(todayIso)

        val currentStreak = DateUtils.calculateCurrentStreak(studiedDateSet)
        val bestStreak = DateUtils.calculateBestStreak(studiedDateSet)
        val totalDays = studiedDateSet.size

        val last7IsoList = DateUtils.getLast7DaysIsoList()
        val last7DaysStatus = last7IsoList.map { iso ->
            DayStatus(
                dateIso = iso,
                dayNameShort = DateUtils.getDayOfWeekShort(iso),
                dayNumber = DateUtils.getDayOfMonth(iso),
                isToday = (iso == todayIso),
                isStudied = studiedDateSet.contains(iso)
            )
        }

        val studiedInLast7 = last7DaysStatus.count { it.isStudied }

        RachaUiState(
            currentStreak = currentStreak,
            bestStreak = bestStreak,
            totalDaysStudied = totalDays,
            isStudiedToday = isTodayStudied,
            todayFormatted = DateUtils.getFormattedDate(todayIso),
            lastSevenDays = last7DaysStatus,
            currentQuote = quote,
            studiedDaysThisWeek = studiedInLast7,
            celebrationTrigger = celebrationTimestamp
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RachaUiState()
    )

    fun markTodayStudied() {
        viewModelScope.launch {
            val todayIso = DateUtils.getTodayIso()
            repository.recordStudy(todayIso)
            // Trigger celebration effect
            _celebrationTrigger.value = System.currentTimeMillis()
            // Provide a fresh motivational quote each time
            _currentQuote.value = MotivationalQuotesProvider.getRandomQuote(_currentQuote.value.id)
        }
    }

    fun undoTodayStudied() {
        viewModelScope.launch {
            val todayIso = DateUtils.getTodayIso()
            repository.removeStudy(todayIso)
        }
    }

    fun toggleDay(dateIso: String) {
        viewModelScope.launch {
            val isStudied = repository.isStudiedOn(dateIso)
            if (isStudied) {
                repository.removeStudy(dateIso)
            } else {
                repository.recordStudy(dateIso)
                _celebrationTrigger.value = System.currentTimeMillis()
                _currentQuote.value = MotivationalQuotesProvider.getRandomQuote(_currentQuote.value.id)
            }
        }
    }

    fun shuffleQuote() {
        _currentQuote.value = MotivationalQuotesProvider.getRandomQuote(_currentQuote.value.id)
    }

    class Factory(private val repository: StudyRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RachaViewModel::class.java)) {
                return RachaViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
