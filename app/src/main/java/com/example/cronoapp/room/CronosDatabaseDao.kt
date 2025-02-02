package com.example.cronoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cronoapp.model.Cronos
import kotlinx.coroutines.flow.Flow

//Interface = metodos -> Reporitory -> ViewModel -> Views
@Dao //Data Access Observer
interface CronosDatabaseDao {

    //CRUD
    @Query("SELECT * FROM cronos")
    fun getCronos(): Flow<List<Cronos>>

    @Query("SELECT * FROM cronos WHERE id = :id")
    fun getCronosById(id: Long): Flow<Cronos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCronos(crono: Cronos)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCronos(crono: Cronos)

    @Delete
    suspend fun deleteCronos(crono: Cronos)
}