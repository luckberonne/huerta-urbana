package com.example.huertaurbana.domain

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LettuceRulesTest {
    private val sown = LocalDate.of(2026, 10, 1)
    private val sowings = listOf(Sowing(1, sown))

    private fun day(n: Long) = sown.plusDays(n)

    @Test
    fun watersOnSowingDayAndEveryTwoDays() {
        listOf(0L, 2L, 4L, 44L).forEach {
            val task = LettuceRules.tasksFor(day(it), sowings).single()
            assertEquals(TaskType.WATER, task.type)
            assertEquals(day(it), task.dueDate)
            assertFalse(task.overdue)
        }
    }

    @Test
    fun harvestsOnDay45() {
        val task = LettuceRules.tasksFor(day(45), sowings).single()
        assertEquals(TaskType.HARVEST, task.type)
        assertEquals(45, task.dayNumber)
    }

    @Test
    fun noTaskBeforeSowingOrAfterHarvest() {
        assertTrue(LettuceRules.tasksFor(day(-1), sowings).isEmpty())
        assertTrue(LettuceRules.tasksFor(day(46), sowings).isEmpty())
    }

    @Test
    fun markedWateringIsDone() {
        val task = LettuceRules.tasksFor(day(4), sowings, listOf(Watering(1, day(4)))).single()
        assertTrue(task.done)
        assertFalse(task.overdue)
    }

    @Test
    fun skippedWateringIsStillPendingAndOverdueTheNextDay() {
        val task = LettuceRules.tasksFor(day(5), sowings, listOf(Watering(1, day(2)))).single()
        assertEquals(day(4), task.dueDate)
        assertFalse(task.done)
        assertTrue(task.overdue)
    }

    @Test
    fun wateringLateClearsTheOverdueTask() {
        val task = LettuceRules.tasksFor(day(5), sowings, listOf(Watering(1, day(5)))).single()
        assertTrue(task.done)
        assertFalse(task.overdue)
    }

    @Test
    fun wateringOtherSowingDoesNotCount() {
        val task = LettuceRules.tasksFor(day(2), sowings, listOf(Watering(99, day(2)))).single()
        assertFalse(task.done)
    }

    @Test
    fun calendarMarksWateringEveryTwoDaysAndHarvestOnDay45() {
        assertEquals(setOf(TaskType.WATER), LettuceRules.marksOn(day(0), sowings))
        assertTrue(LettuceRules.marksOn(day(1), sowings).isEmpty())
        assertEquals(setOf(TaskType.WATER), LettuceRules.marksOn(day(44), sowings))
        assertEquals(setOf(TaskType.HARVEST), LettuceRules.marksOn(day(45), sowings))
        assertTrue(LettuceRules.marksOn(day(46), sowings).isEmpty())
        assertTrue(LettuceRules.marksOn(day(-2), sowings).isEmpty())
    }

    @Test
    fun calendarMarksSeveralSowingsOnTheSameDay() {
        val two = sowings + Sowing(2, day(1))
        assertEquals(setOf(TaskType.WATER, TaskType.HARVEST), LettuceRules.marksOn(day(45), two))
        assertEquals(setOf(TaskType.WATER), LettuceRules.marksOn(day(3), two))
    }
}
