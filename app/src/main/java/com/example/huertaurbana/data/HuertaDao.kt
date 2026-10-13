package com.example.huertaurbana.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HuertaDao {
    @Query("SELECT * FROM sowings ORDER BY dateEpochDay")
    fun observeSowings(): Flow<List<SowingEntity>>

    @Query("SELECT * FROM waterings")
    fun observeWaterings(): Flow<List<WateringEntity>>

    @Insert
    suspend fun insertSowing(sowing: SowingEntity)

    @Insert
    suspend fun insertWatering(watering: WateringEntity)
}
