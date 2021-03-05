package ru.androidlab.todoproj.data.repository

import androidx.lifecycle.LiveData
import ru.androidlab.todoproj.data.TaskEntity

interface ITaskRepository {
    fun getAll():List<TaskEntity>
    fun getById(id:Long):TaskEntity
    fun getAllActualTask(): LiveData<List<TaskEntity>>
    fun getAllDone(): LiveData<List<TaskEntity>>
    fun filterByLowPriority(): LiveData<List<TaskEntity>>
    fun filterByMediumPriority(): LiveData<List<TaskEntity>>
    fun filterByHighPriority(): LiveData<List<TaskEntity>>
    fun save(task: TaskEntity)
    fun removeById(id: Long)
    fun updateContentById(id: Long, title: String, description: String, priority: String, done: Boolean)
    fun update(task: TaskEntity)
    fun updateDone(task: TaskEntity)
}