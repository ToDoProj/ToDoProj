package ru.androidlab.todoproj.util

import java.util.*

interface DateTimePickerDialogPickerListener {
    fun datePicked(date: Date)
    fun timePicked(time: Date)
}
