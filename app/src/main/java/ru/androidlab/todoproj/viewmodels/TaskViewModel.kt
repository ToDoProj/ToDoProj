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
    priority = "Low"
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

    fun changeContent(content: String) {
        val text = content.trim()
        if (edit.value?.title == text) {
            return
        }
        edit.value = edit.value?.copy(title = text)
    }

    fun removeById(id: Long) = repository.removeById(id)
}