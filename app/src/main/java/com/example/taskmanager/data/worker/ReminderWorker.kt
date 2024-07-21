package com.example.taskmanager.data.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskmanager.MainActivity
import com.example.taskmanager.R
import kotlinx.coroutines.delay

//The params parameter provides the configuration and parameters for the worker.
// It contains information and utilities necessary for the worker to perform its task.
class ReminderWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params){
    //When you enqueue a Worker using WorkManager, you don’t explicitly pass these parameters yourself.
    // Instead, WorkManager handles the instantiation and management of your Worker instance internally:
    //WorkManager handles the instantiation of your Worker class (ReminderWorker),
    // providing it with the necessary Context and WorkerParameters

    companion object {
        const val CHANNEL_ID = "reminder_channel"
        const val NOTIFICATION_ID = 1
    }

    //WorkManager handles the execution of doWork() automatically based on the conditions and constraints you specify
    // when enqueuing the WorkRequest
    override suspend fun doWork(): Result{
        val reminderTime = inputData.getLong("reminderTime", 0)

        if(reminderTime>0) delay(reminderTime)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            showNotification("Reminder", "It's time for your task!")
        } else {
            // Handle the case where the permission is not granted
            return Result.failure()
        }

        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val notification = NotificationCompat.Builder(applicationContext, "reminder_channel")
            .setContentTitle(title)
            .setContentText(message)
            //.setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminder Channel"
            val descriptionText = "Channel for reminder notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}