package com.example.cronoapp.reporitory

import com.example.cronoapp.model.Cronos
import com.example.cronoapp.room.CronosDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CronosRepository @Inject constructor(private val cronosDatabaseDao: CronosDatabaseDao) {

    suspend fun addCrono(crono: Cronos) = cronosDatabaseDao.insertCronos(crono)

    suspend fun updateCrono(crono: Cronos) = cronosDatabaseDao.updateCronos(crono)

    suspend fun deleteCrono(crono: Cronos) = cronosDatabaseDao.deleteCronos(crono)

    fun getAllCronos(): Flow<List<Cronos>> = cronosDatabaseDao.getCronos().flowOn(Dispatchers.IO).conflate()

    fun getById(id: Long): Flow<Cronos> = cronosDatabaseDao.getCronosById(id)
}