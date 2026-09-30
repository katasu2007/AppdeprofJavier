package com.example.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val dayOfWeekFormat = SimpleDateFormat("EEE", Locale("es", "ES"))
    private val fullDateFormat = SimpleDateFormat("d 'de' MMMM", Locale("es", "ES"))

    fun getTodayIso(): String {
        return isoFormat.format(Date())
    }

    fun getOffsetDateIso(daysOffsetFromToday: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysOffsetFromToday)
        return isoFormat.format(calendar.time)
    }

    fun getLast7DaysIsoList(): List<String> {
        val result = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        // 6 days ago up to today (7 days total)
        calendar.add(Calendar.DAY_OF_YEAR, -6)
        for (i in 0 until 7) {
            result.add(isoFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return result
    }

    fun getDayOfWeekShort(dateIso: String): String {
        return try {
            val date = isoFormat.parse(dateIso) ?: Date()
            val text = dayOfWeekFormat.format(date)
            // Clean up: e.g. "mié." -> "MIÉ"
            text.replace(".", "").uppercase(Locale("es", "ES"))
        } catch (e: Exception) {
            ""
        }
    }

    fun getDayOfMonth(dateIso: String): Int {
        return try {
            val date = isoFormat.parse(dateIso) ?: Date()
            val cal = Calendar.getInstance()
            cal.time = date
            cal.get(Calendar.DAY_OF_MONTH)
        } catch (e: Exception) {
            1
        }
    }

    fun getFormattedDate(dateIso: String): String {
        return try {
            val date = isoFormat.parse(dateIso) ?: Date()
            fullDateFormat.format(date)
        } catch (e: Exception) {
            dateIso
        }
    }

    /**
     * Calculates the current consecutive streak.
     * If user studied today, counts backwards from today.
     * If user hasn't studied today yet, but studied yesterday, streak is maintained (counting from yesterday).
     * Otherwise, streak is 0.
     */
    fun calculateCurrentStreak(studiedDateSet: Set<String>): Int {
        if (studiedDateSet.isEmpty()) return 0

        val todayIso = getTodayIso()
        val yesterdayIso = getOffsetDateIso(-1)

        val startFromToday = studiedDateSet.contains(todayIso)
        val startFromYesterday = studiedDateSet.contains(yesterdayIso)

        if (!startFromToday && !startFromYesterday) {
            return 0
        }

        val startOffset = if (startFromToday) 0 else -1
        var streak = 0
        var currentOffset = startOffset

        while (true) {
            val dateToCheck = getOffsetDateIso(currentOffset)
            if (studiedDateSet.contains(dateToCheck)) {
                streak++
                currentOffset--
            } else {
                break
            }
        }

        return streak
    }

    /**
     * Calculates the all-time maximum consecutive streak from all studied dates.
     */
    fun calculateBestStreak(studiedDateSet: Set<String>): Int {
        if (studiedDateSet.isEmpty()) return 0
        val dates = studiedDateSet.mapNotNull {
            try { isoFormat.parse(it) } catch (e: Exception) { null }
        }.sorted()

        if (dates.isEmpty()) return 0

        var maxStreak = 1
        var currentStreak = 1

        val calPrev = Calendar.getInstance()
        val calCurr = Calendar.getInstance()

        for (i in 1 until dates.size) {
            calPrev.time = dates[i - 1]
            calCurr.time = dates[i]

            calPrev.add(Calendar.DAY_OF_YEAR, 1)
            val isNextDay = calPrev.get(Calendar.YEAR) == calCurr.get(Calendar.YEAR) &&
                    calPrev.get(Calendar.DAY_OF_YEAR) == calCurr.get(Calendar.DAY_OF_YEAR)

            if (isNextDay) {
                currentStreak++
                if (currentStreak > maxStreak) {
                    maxStreak = currentStreak
                }
            } else {
                currentStreak = 1
            }
        }

        val activeStreak = calculateCurrentStreak(studiedDateSet)
        return maxOf(maxStreak, activeStreak)
    }
}
