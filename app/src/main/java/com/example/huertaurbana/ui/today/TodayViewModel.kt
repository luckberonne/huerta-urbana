package com.example.huertaurbana.ui.today

import androidx.lifecycle.ViewModel
import com.example.huertaurbana.domain.LettuceRules
import com.example.huertaurbana.domain.Sowing
import com.example.huertaurbana.domain.Task
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TodayViewModel : ViewModel() {
    private val _sowings = MutableStateFlow<List<Sowing>>(emptyList())
    val sowings: StateFlow<List<Sowing>> = _sowings.asStateFlow()

    fun addSowing(date: LocalDate) {
        _sowings.update { it + Sowing(id = (it.maxOfOrNull { s -> s.id } ?: 0L) + 1, date = date) }
    }

    fun tasksToday(sowings: List<Sowing>, today: LocalDate = LocalDate.now()): List<Task> =
        LettuceRules.tasksFor(today, sowings)
}
