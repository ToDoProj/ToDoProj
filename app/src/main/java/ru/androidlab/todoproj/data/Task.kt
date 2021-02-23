package ru.androidlab.todoproj.data

sealed class Task{
    data class AlarmTask(val textTitle: String, val textTime: String, val alarmClockImage: Int, val time: String): Task()
}
