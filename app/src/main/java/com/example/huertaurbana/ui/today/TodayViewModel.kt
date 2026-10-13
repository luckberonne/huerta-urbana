package com.example.huertaurbana.ui.today

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertaurbana.data.HuertaDatabase
import com.example.huertaurbana.data.SowingEntity
import com.example.huertaurbana.data.WateringEntity
import com.example.huertaurbana.domain.LettuceRules
import com.example.huertaurbana.domain.Sowing
import com.example.huertaurbana.domain.Task
import com.example.huertaurbana.domain.Watering
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodayViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = HuertaDatabase.get(application).dao()

    val sowings: StateFlow<List<Sowing>> = dao.observeSowings()
        .map { list -> list.map { it.toDomain() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val waterings: StateFlow<List<Watering>> = dao.observeWaterings()
        .map { list -> list.map { it.toDomain() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addSowing(date: LocalDate) {
        viewModelScope.launch { dao.insertSowing(SowingEntity(dateEpochDay = date.toEpochDay())) }
    }

    fun markWatered(sowingId: Long, today: LocalDate = LocalDate.now()) {
        viewModelScope.launch { dao.insertWatering(WateringEntity(sowingId = sowingId, dateEpochDay = today.toEpochDay())) }
    }

    fun tasksToday(sowings: List<Sowing>, waterings: List<Watering>, today: LocalDate = LocalDate.now()): List<Task> =
        LettuceRules.tasksFor(today, sowings, waterings)
}
