package ru.androidlab.todoproj.views.activity

import android.app.DatePickerDialog
import java.util.*

interface ITaskSetupView{
    fun openDateTimeDialog(datePickerDialog: DatePickerDialog)
    fun showRemindButton(show: Boolean)
    fun showRemindInfo(show: Boolean)
    fun setRemindInfo(remindDate: Date)
    fun showCompleteForm()
}