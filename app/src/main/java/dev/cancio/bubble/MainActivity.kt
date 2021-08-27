package dev.cancio.bubble

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import dev.cancio.bubble.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "BUBBLE"

    companion object{
        fun getStartIntent(context: Context) =
            Intent(context, MainActivity::class.java)
    }

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

    private fun getTargetIntent(): PendingIntent{
        val intent = Intent(this, TargetActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(this, 0, intent, 0)
    }

    private fun newNotification(title:String, subtitle:String) = NotificationCompat
        .Builder(this,CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(title)
        .setContentText(subtitle)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(getTargetIntent())
        .setAutoCancel(true)

    private fun newBubbleNotification(title:String, subtitle:String,bubbleData:  NotificationCompat.BubbleMetadata) = NotificationCompat
        .Builder(this,CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(title)
        .setContentText(subtitle)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(getTargetIntent())
        .setBubbleMetadata(bubbleData)
        .setAutoCancel(true)


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

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getBubble(notificationId:Int, builder: NotificationCompat.Builder) = NotificationCompat
        .BubbleMetadata
        .Builder(
            getTargetIntent(),
            IconCompat.createWithResource(this, R.drawable.ic_launcher_background)
        )
        .setAutoExpandBubble(true)
        .setSuppressNotification(true)
        .setDesiredHeight(600)
        .build()

}