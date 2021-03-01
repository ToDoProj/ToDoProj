package ru.androidlab.todoproj.data.repository

import androidx.lifecycle.LiveData
import ru.androidlab.todoproj.data.TaskEntity

interface ITaskRepository {
    fun getAll(): LiveData<List<TaskEntity>>
    fun save(task: TaskEntity)
    fun removeById(id: Long)
    fun updateContentById(id: Long, title: String, description: String, priority: String, done: Boolean)
    fun update(task: TaskEntity)
}