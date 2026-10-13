package com.example.huertaurbana.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Sowing(val id: Long, val date: LocalDate)

data class Watering(val sowingId: Long, val date: LocalDate)

enum class TaskType { WATER, HARVEST }

data class Task(
    val sowingId: Long,
    val type: TaskType,
    val dueDate: LocalDate,
    val sowedOn: LocalDate,
    val today: LocalDate,
    val done: Boolean = false,
) {
    val dayNumber: Int get() = ChronoUnit.DAYS.between(sowedOn, dueDate).toInt()
    val overdue: Boolean get() = !done && dueDate.isBefore(today)
}

object LettuceRules {
    const val WATER_EVERY_DAYS = 2
    const val HARVEST_AFTER_DAYS = 45

    /** Tareas de [day]: una de riego por siembra (la última que tocaba, pendiente o hecha) y la cosecha el día 45. */
    fun tasksFor(day: LocalDate, sowings: List<Sowing>, waterings: List<Watering> = emptyList()): List<Task> =
        sowings.mapNotNull { sowing ->
            val daysSince = ChronoUnit.DAYS.between(sowing.date, day)
            when {
                daysSince < 0 -> null
                daysSince == HARVEST_AFTER_DAYS.toLong() ->
                    Task(sowing.id, TaskType.HARVEST, day, sowing.date, day)
                daysSince < HARVEST_AFTER_DAYS -> {
                    val dueDate = sowing.date.plusDays(daysSince - daysSince % WATER_EVERY_DAYS)
                    val done = waterings.any { it.sowingId == sowing.id && !it.date.isBefore(dueDate) && !it.date.isAfter(day) }
                    Task(sowing.id, TaskType.WATER, dueDate, sowing.date, day, done)
                }
                else -> null
            }
        }
}
