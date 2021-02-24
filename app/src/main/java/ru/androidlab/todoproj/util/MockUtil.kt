package ru.androidlab.todoproj.util

import ru.androidlab.todoproj.data.Task

object MockUtil {
     fun geList() = listOf(
            Task.AlarmTask(1,"Shopping", "till 5:00 pm", "low"),
            Task.AlarmTask(2,"Shopping2", "till 6:00 pm", "low"),
            Task.AlarmTask(3,"Shopping3", "till 7:00 pm", "low"),
            Task.AlarmTask(4,"Shopping4", "till 8:00 pm", "low"),
            Task.AlarmTask(5,"Shopping5", "till 9:00 pm", "low"),
            Task.AlarmTask(6,"Shopping6", "till 10:00 pm", "low"),
            Task.AlarmTask(7,"Shopping7", "till 11:00 pm", "low"),
            Task.AlarmTask(8,"Shopping8", "till 12:00 pm", "low"),
            Task.AlarmTask(9,"Shopping9", "till 13:00 pm", "low"),
            Task.AlarmTask(10,"Shopping10", "till 14:00 pm", "low"),
            Task.AlarmTask(11,"Shopping11", "till 15:00 pm", "low"),
            Task.AlarmTask(12,"Shopping12", "till 16:00 pm", "low")
    )
}
