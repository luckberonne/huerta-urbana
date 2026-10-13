package com.example.huertaurbana.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertaurbana.domain.LettuceRules
import com.example.huertaurbana.domain.TaskType
import com.example.huertaurbana.ui.theme.HarvestOrange
import com.example.huertaurbana.ui.theme.WaterBlue
import com.example.huertaurbana.ui.today.TodayViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private val spanish = Locale("es", "AR")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: TodayViewModel = viewModel()) {
    val sowings by viewModel.sowings.collectAsStateWithLifecycle()
    var month by remember { mutableStateOf(YearMonth.now()) }
    val today = LocalDate.now()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendario", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "‹",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.clickable { month = month.minusMonths(1) }.padding(horizontal = 16.dp, vertical = 4.dp),
                )
                Text(
                    month.month.getDisplayName(TextStyle.FULL, spanish).replaceFirstChar { it.uppercase() } + " " + month.year,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    "›",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.clickable { month = month.plusMonths(1) }.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("L", "M", "M", "J", "V", "S", "D").forEach {
                    Text(it, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelLarge)
                }
            }
            val firstOffset = month.atDay(1).dayOfWeek.value - 1
            val cells: List<LocalDate?> = List(firstOffset) { null } + (1..month.lengthOfMonth()).map { month.atDay(it) }
            cells.chunked(7).forEach { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    week.forEach { date ->
                        DayCell(date, date == today, date?.let { LettuceRules.marksOn(it, sowings) }.orEmpty(), Modifier.weight(1f))
                    }
                    repeat(7 - week.size) { Box(Modifier.weight(1f)) }
                }
            }
            Legend()
        }
    }
}

@Composable
private fun DayCell(date: LocalDate?, isToday: Boolean, marks: Set<TaskType>, modifier: Modifier) {
    Column(
        modifier = modifier.aspectRatio(0.8f).padding(2.dp).let {
            if (isToday) it.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)) else it
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (date != null) {
            Text(date.dayOfMonth.toString(), style = MaterialTheme.typography.bodyLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally), modifier = Modifier.padding(top = 4.dp).fillMaxWidth().height(8.dp)) {
                if (TaskType.WATER in marks) Dot(WaterBlue)
                if (TaskType.HARVEST in marks) Dot(HarvestOrange)
            }
        }
    }
}

@Composable
private fun Dot(color: Color) {
    Box(Modifier.size(8.dp).background(color, CircleShape))
}

@Composable
private fun Legend() {
    Row(horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
        Dot(WaterBlue); Text("Riego", style = MaterialTheme.typography.bodyMedium)
        Dot(HarvestOrange); Text("Cosecha", style = MaterialTheme.typography.bodyMedium)
    }
}
