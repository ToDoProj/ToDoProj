package ru.androidlab.todoproj.views.activity

import android.app.DatePickerDialog

interface ITaskSetupView{
    fun openDateTimeDialog(datePickerDialog: DatePickerDialog)
}