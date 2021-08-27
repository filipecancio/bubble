package dev.cancio.bubble

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.cancio.bubble.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "BUBBLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        with(binding){
            btnNotification.setOnClickListener{
                val title = txtTitleNotification.text.toString()
                val subtitle = txtSubtitleNotification.text.toString()
                val notification = newNotification(title,subtitle)

                notify(1,notification)

            }
        }
    }

    fun newNotification(title:String, subtitle:String) = NotificationCompat
        .Builder(this,CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(title)
        .setContentText(subtitle)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun notify(notificationId:Int,builder: NotificationCompat.Builder) = with(NotificationManagerCompat.from(this)){
        notify(notificationId, builder.build())
    }
}