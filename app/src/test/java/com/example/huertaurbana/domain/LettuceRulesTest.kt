package com.example.huertaurbana.domain

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LettuceRulesTest {
    private val sown = LocalDate.of(2026, 10, 1)
    private val sowings = listOf(Sowing(1, sown))

    @Test
    fun watersOnSowingDayAndEveryTwoDays() {
        listOf(0L, 2L, 4L, 44L).forEach {
            assertEquals(listOf(Task(1, TaskType.WATER, sown.plusDays(it))), LettuceRules.tasksFor(sown.plusDays(it), sowings))
        }
    }

    @Test
    fun noTaskOnOffDays() {
        listOf(1L, 3L, 43L).forEach {
            assertTrue(LettuceRules.tasksFor(sown.plusDays(it), sowings).isEmpty())
        }
    }

    @Test
    fun harvestsOnDay45() {
        assertEquals(listOf(Task(1, TaskType.HARVEST, sown.plusDays(45))), LettuceRules.tasksFor(sown.plusDays(45), sowings))
    }

    @Test
    fun noTaskBeforeSowingOrAfterHarvest() {
        assertTrue(LettuceRules.tasksFor(sown.minusDays(1), sowings).isEmpty())
        assertTrue(LettuceRules.tasksFor(sown.plusDays(46), sowings).isEmpty())
    }
}
