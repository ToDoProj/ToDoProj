package ru.androidlab.todoproj.presenters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.util.DateTimePickerDialogProducer
import ru.androidlab.todoproj.util.MockUtil
import ru.androidlab.todoproj.util.MockUtil.GET_TASK_ENTITY
import ru.androidlab.todoproj.util.MockUtil.ID_EXTRA
import ru.androidlab.todoproj.util.MockUtil.TITLE_EXTRA
import ru.androidlab.todoproj.util.NotifierAlarm
import ru.androidlab.todoproj.views.activity.ITaskSetupView
import java.util.*

class TaskSetupPresenter(
    val view: ITaskSetupView,
    private val dateTimePickerDialogProducer: DateTimePickerDialogProducer
) {

    fun openDateTimeDialog() {
        dateTimePickerDialogProducer.createDialog().show()
    }

    fun createAlarm(date: Date, task: TaskEntity, context: Context) {
        val calendar = Calendar.getInstance()

        val alarmIntent = Intent(context, NotifierAlarm::class.java)
        alarmIntent.putExtra(TITLE_EXTRA, task.title)
        alarmIntent.putExtra(ID_EXTRA, task.id)
        alarmIntent.putExtra(REMIND_DATE, date.toString())
        alarmIntent.putExtra(GET_TASK_ENTITY, task)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, 110, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        calendar.timeInMillis = date.time
        calendar.set(Calendar.YEAR, date.year)
        calendar.set(Calendar.HOUR_OF_DAY, date.hours)
        calendar.set(Calendar.MINUTE, date.minutes)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmPendingIntent)
    }

    private companion object {
        const val REMIND_DATE = "remind_date"
        const val REQUEST_CODE = 1001
    }
}