package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.util.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("RACHA", appName)
    }

    @Test
    fun `test streak calculation with consecutive days`() {
        val today = DateUtils.getTodayIso()
        val yesterday = DateUtils.getOffsetDateIso(-1)
        val twoDaysAgo = DateUtils.getOffsetDateIso(-2)

        val set = setOf(today, yesterday, twoDaysAgo)
        val streak = DateUtils.calculateCurrentStreak(set)
        assertEquals(3, streak)
    }

    @Test
    fun `test streak maintained from yesterday if not yet studied today`() {
        val yesterday = DateUtils.getOffsetDateIso(-1)
        val twoDaysAgo = DateUtils.getOffsetDateIso(-2)

        val set = setOf(yesterday, twoDaysAgo)
        val streak = DateUtils.calculateCurrentStreak(set)
        assertEquals(2, streak)
    }
}
