package ru.androidlab.todoproj.data.repository

import androidx.lifecycle.LiveData
import ru.androidlab.todoproj.data.TaskEntity

interface ITaskRepository {
    fun getAll(): LiveData<List<TaskEntity>>
    fun save(task: TaskEntity)
    fun removeById(id: Long)
}