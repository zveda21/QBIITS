package com.example.user.my_application

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.rafael.my_application.R
import java.util.*

class MyService : Service() {

    var string = ""
    var intg = 0
    var bool = false
    val TAG = "My Service"


    lateinit var preferences: SharedPreferences

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        ShowLog("onCreate")
        super.onCreate()

        getSharedPreferences()
        notify1()

    }
        fun getSharedPreferences() {
            preferences=getSharedPreferences("my_data",Context.MODE_PRIVATE)
            string = preferences.getString("string_key", "")
           // intg = preferences.getInt("int_key", 0)
           // bool = preferences.getBoolean("bool_key", false)
        }

    fun notify1() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
        val NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notifications",
                NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.iconn)
            .setTicker("Hearty365")
            .setPriority(Notification.PRIORITY_MAX)
         //   .setContentTitle(intg.toString() + " , " + bool.toString())
            .setContentText(string)
            .setContentInfo("Info");
        notificationManager.notify(/*notification id*/198, notificationBuilder.build());
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ShowLog("onStartCommand")
        val handler = Handler()
        var runnable = Runnable {
            notify1()
        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, 100, 10000000000)

        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        ShowLog("onDestroy")
        super.onDestroy()
    }

    fun ShowLog(message: String) {
        Log.d(TAG, message)
    }
}
