package ru.androidlab.todoproj.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.androidlab.todoproj.data.TaskDao
import ru.androidlab.todoproj.data.TaskEntity

class TaskRepository (private val dao: TaskDao) : ITaskRepository{
    override fun getAll(): LiveData<List<TaskEntity>> = Transformations.map(dao.getAll()) { list ->
        list.map {
            TaskEntity(it.id, it.title, it.description, it.priority, it.done)
        }
    }

    override fun save(task: TaskEntity) {
        dao.save(TaskEntity.fromDto(task))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun updateContentById(id: Long, title: String, description: String, priority: String, done: Boolean) {
        dao.updateContentById(id, title, description, priority, done)
    }

    override fun update(task: TaskEntity) {
        dao.update(TaskEntity.fromDto(task))
    }

}