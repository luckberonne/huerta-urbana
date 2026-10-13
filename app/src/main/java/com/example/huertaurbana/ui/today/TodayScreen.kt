package com.example.huertaurbana.ui.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter
import com.example.huertaurbana.domain.LettuceRules
import com.example.huertaurbana.domain.Task
import com.example.huertaurbana.domain.TaskType
import com.example.huertaurbana.ui.sowing.SowingDialog

private val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(viewModel: TodayViewModel = viewModel()) {
    val sowings by viewModel.sowings.collectAsStateWithLifecycle()
    var showForm by remember { mutableStateOf(false) }
    val tasks = viewModel.tasksToday(sowings)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Huerta Urbana", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Hoy en tu huerta", style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier.weight(1f)) {
                if (tasks.isEmpty()) {
                    EmptyState(hasSowings = sowings.isNotEmpty())
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 8.dp),
                    ) {
                        items(tasks, key = { "${it.sowingId}-${it.type}" }) { TaskCard(it) }
                    }
                }
            }
            Button(
                onClick = { showForm = true },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            ) { Text("Agregar siembra") }
        }
    }

    if (showForm) {
        SowingDialog(
            onDismiss = { showForm = false },
            onSave = { date ->
                viewModel.addSowing(date)
                showForm = false
            },
        )
    }
}

@Composable
private fun TaskCard(task: Task) {
    val text = when (task.type) {
        TaskType.WATER -> "Regar lechuga"
        TaskType.HARVEST -> "Cosechar lechuga"
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text, style = MaterialTheme.typography.titleMedium)
            val detail = when (task.type) {
                TaskType.WATER -> "Sembrada el ${task.sowedOn.format(dateFormat)} · día ${task.dayNumber} de ${LettuceRules.HARVEST_AFTER_DAYS}"
                TaskType.HARVEST -> "Sembrada el ${task.sowedOn.format(dateFormat)} · ¡ya pasaron ${task.dayNumber} días!"
            }
            Text(detail, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun EmptyState(hasSowings: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            if (hasSowings) "Nada para hoy" else "Todavía no cargaste ninguna siembra",
            style = MaterialTheme.typography.titleMedium,
        )
        if (!hasSowings) {
            Text("Agregá una para ver qué hacer hoy.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
