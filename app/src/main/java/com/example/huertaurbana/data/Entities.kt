package com.example.huertaurbana.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.huertaurbana.domain.Sowing
import com.example.huertaurbana.domain.Watering
import java.time.LocalDate

@Entity(tableName = "sowings")
data class SowingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateEpochDay: Long,
) {
    fun toDomain() = Sowing(id, LocalDate.ofEpochDay(dateEpochDay))
}

@Entity(tableName = "waterings")
data class WateringEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sowingId: Long,
    val dateEpochDay: Long,
) {
    fun toDomain() = Watering(sowingId, LocalDate.ofEpochDay(dateEpochDay))
}
