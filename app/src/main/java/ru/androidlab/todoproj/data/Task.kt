package ru.androidlab.todoproj.data

sealed class Task{
    data class AlarmTask(
        var id: Long,
        var title: String,
        var description: String,
        var priority: String): Task()
}
