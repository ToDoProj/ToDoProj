package ru.androidlab.todoproj.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import java.util.*

class DateTimePickerDialogProducer(
    private val context: Context,
    private val listener: DateTimePickerDialogPickerListener
) {

    fun createDialog(): DatePickerDialog {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance()
                val newTime = Calendar.getInstance()

                val timePickerDialog = TimePickerDialog(
                    context, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        newDate.set(year, month, dayOfMonth, hour, minute, 0)
                        val currentCalendar = Calendar.getInstance()
                        if (newDate.timeInMillis - currentCalendar.timeInMillis > 0) {
                            listener.timePicked(newDate.time)
                            listener.datePicked(Date(year, month, dayOfMonth))
                        } else {
                            Toast.makeText(context, "Invalid date time", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    newDate.get(Calendar.HOUR_OF_DAY),
                    newTime.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate = System.currentTimeMillis()
        return dialog
    }
}
