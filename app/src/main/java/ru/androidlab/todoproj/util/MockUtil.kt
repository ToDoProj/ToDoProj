package ru.androidlab.todoproj.util

import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.adapters.Item

object MockUtil {
     fun geList() = listOf(
            Item.Card("Shopping", "till 5:00 pm", R.drawable.alarm_clock, "4:30"),
            Item.Card("Shopping2", "till 6:00 pm", R.drawable.alarm_clock, "4:30"),
            Item.Card("Shopping3", "till 7:00 pm", R.drawable.alarm_clock, "4:30"),
            Item.Card("Shopping4", "till 8:00 pm", R.drawable.alarm_clock, "4:30"),
            Item.Card("Shopping5", "till 9:00 pm", R.drawable.alarm_clock, "4:30"),
            Item.Card("Shopping6", "till 10:00 pm", R.drawable.alarm_clock, "4:30")
    )
}
