package com.example.huertaurbana.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Sowing(val id: Long, val date: LocalDate)

enum class TaskType { WATER, HARVEST }

data class Task(val sowingId: Long, val type: TaskType, val date: LocalDate)

object LettuceRules {
    const val WATER_EVERY_DAYS = 2
    const val HARVEST_AFTER_DAYS = 45

    fun tasksFor(day: LocalDate, sowings: List<Sowing>): List<Task> =
        sowings.mapNotNull { sowing ->
            val daysSince = ChronoUnit.DAYS.between(sowing.date, day)
            when {
                daysSince < 0 -> null
                daysSince == HARVEST_AFTER_DAYS.toLong() -> Task(sowing.id, TaskType.HARVEST, day)
                daysSince < HARVEST_AFTER_DAYS && daysSince % WATER_EVERY_DAYS == 0L ->
                    Task(sowing.id, TaskType.WATER, day)
                else -> null
            }
        }
}
