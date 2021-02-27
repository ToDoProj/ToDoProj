package ru.androidlab.todoproj.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.androidlab.todoproj.data.AppDataBase
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.data.repository.ITaskRepository
import ru.androidlab.todoproj.data.repository.TaskRepository

private val empty = TaskEntity(
    id = 0,
    title = "",
    description = "",
    priority = "Low",
    done = false
)

class TaskViewModel (application: Application): AndroidViewModel(application){
    private val repository: ITaskRepository = TaskRepository(
        AppDataBase.getInstance(context = application).taskDao()
    )
    val data =repository.getAll()
    val edit = MutableLiveData(empty)


    fun save() {
        edit.value?.let {
            repository.save(it)
        }
        edit.value = empty
    }

    fun edit(task: TaskEntity) {
        edit.value = task
    }

    // Можно попробовать вот эту функцию для изменение на выполнено в БД
    fun markDone(done: Boolean){
        val status = true
        edit.value = edit.value?.copy(done = status)
    }

    fun changeContent(title: String, priority: String) {
        val text = title.trim()
        val spinner = priority.trim()
        if (edit.value?.title == text) {
            return
        }
        edit.value = edit.value?.copy(title = text)
        edit.value = edit.value?.copy(priority = spinner)
    }

    fun removeById(id: Long) = repository.removeById(id)
}