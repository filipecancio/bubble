package dev.cancio.bubble

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
                MainActivity.getStartIntent(it.context)
            }
        }

    }
}