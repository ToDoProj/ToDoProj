package ru.androidlab.todoproj.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.androidlab.todoproj.data.TaskDao
import ru.androidlab.todoproj.data.TaskEntity

class TaskRepository (private val dao: TaskDao) : ITaskRepository{
    override fun getAllActualTask(): LiveData<List<TaskEntity>> = Transformations.map(dao. getAllActualTask(false)) { list ->
        list.map {
            TaskEntity(it.id, it.title, it.description, it.priority, it.done)
        }
    }

    override fun getAllDone(): LiveData<List<TaskEntity>> = Transformations.map(dao.getAllDone(true)) { list ->
        list.map {
            TaskEntity(it.id, it.title, it.description, it.priority, it.done)
        }
    }

    override fun filterByLowPriority(): LiveData<List<TaskEntity>> = Transformations.map(dao.getAllLowPriority(false)) { list ->
        list.map {
            TaskEntity(it.id, it.title, it.description, it.priority, it.done)
        }
    }

    override fun filterByMediumPriority(): LiveData<List<TaskEntity>> = Transformations.map(dao.getAllMediumPriority(false)) { list ->
        list.map {
            TaskEntity(it.id, it.title, it.description, it.priority, it.done)
        }
    }

    override fun filterByHighPriority(): LiveData<List<TaskEntity>> = Transformations.map(dao.getAllHighPriority(false)) { list ->
        list.map {
            TaskEntity(it.id, it.title, it.description, it.priority, it.done)
        }
    }

    override fun save(task: TaskEntity) {
        dao.save(TaskEntity.fromDto(task))
    }

    override fun saveRemovedTask(task: TaskEntity) {
        dao.insert(task)
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

    override fun updateDone(task: TaskEntity) {
        dao.update(task)
    }

}