package ru.androidlab.todoproj.util

import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.data.Task

object MockUtil {
     fun geList() = listOf(
            Task.AlarmTask("Shopping", "till 5:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping2", "till 6:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping3", "till 7:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping4", "till 8:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping5", "till 9:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping6", "till 10:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping7", "till 5:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping8", "till 6:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping9", "till 7:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping10", "till 8:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping11", "till 9:00 pm", R.drawable.alarm_clock, "4:30"),
            Task.AlarmTask("Shopping12", "till 10:00 pm", R.drawable.alarm_clock, "4:30")
    )
}
