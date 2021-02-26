package ru.androidlab.todoproj.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.androidlab.todoproj.data.TaskDao
import ru.androidlab.todoproj.data.TaskEntity

class TaskRepository (private val dao: TaskDao) : ITaskRepository{
    override fun getAll(): LiveData<List<TaskEntity>> = Transformations.map(dao.getAll()) { list ->
        list.map {
            TaskEntity(it.id, it.title, it.description, it.priority)
        }
    }

    override fun save(task: TaskEntity) {
        dao.save(TaskEntity.fromDto(task))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

}