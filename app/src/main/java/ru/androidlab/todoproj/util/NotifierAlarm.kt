package ru.androidlab.todoproj.util

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.androidlab.todoproj.MainActivity
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.util.MockUtil.GET_TASK_ENTITY
import ru.androidlab.todoproj.util.MockUtil.ID_EXTRA
import ru.androidlab.todoproj.util.MockUtil.TITLE_EXTRA
import ru.androidlab.todoproj.views.activity.TaskSetupActivity

class NotifierAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val activityIntent = Intent(context, TaskSetupActivity::class.java)
        activityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val message = intent.getStringExtra(TITLE_EXTRA)
        val id = intent.getLongExtra(ID_EXTRA,-1)
        val task = intent.getSerializableExtra(GET_TASK_ENTITY)

        activityIntent.putExtra(ID_EXTRA, id)

        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MainActivity::class.java)
        taskStackBuilder.addNextIntent(activityIntent)

        val pendingIntent =
            taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context)
        var channel: NotificationChannel? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel =
                NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH)
        }




        val notification: Notification = builder.setContentTitle("Reminder")
            .setSmallIcon(R.drawable.alarm_clock)
            .setContentText(message).setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setChannelId("my_channel_01")
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel!!)
        }
        notificationManager.notify(1, notification)
    }
}