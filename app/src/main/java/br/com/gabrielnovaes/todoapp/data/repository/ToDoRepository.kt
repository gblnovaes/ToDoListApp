package br.com.gabrielnovaes.todoapp.data.repository

import androidx.lifecycle.LiveData
import br.com.gabrielnovaes.todoapp.data.ToDoDao
import br.com.gabrielnovaes.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao){
    fun getAllData() : LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData) {
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAllData() {
        toDoDao.deleteAll()
    }
}