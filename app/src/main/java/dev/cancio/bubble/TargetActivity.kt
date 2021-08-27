package dev.cancio.bubble

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.cancio.bubble.databinding.ActivityTargetBinding

class TargetActivity : AppCompatActivity() {

    companion object{
        fun getStartIntent(context: Context) =
            Intent(context, TargetActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnTargetBack.setOnClickListener {
                val context = this@TargetActivity
                context.startActivity(
                    MainActivity.getStartIntent(context)
                )
            }
            btnTargetCode.setOnClickListener {
                var clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val code = txtTargetCode.text
                val clip = ClipData.newPlainText("RANDOM UUID",code)
                clipboard.setPrimaryClip(clip)
            }
        }

    }
}