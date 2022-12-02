package com.ubt.unittestsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_main).apply {
            setOnClickListener {
                this.text = "你好 Espresso"
                Toast.makeText(it.context, "你好 Espresso", Toast.LENGTH_LONG).show()
            }
        }
    }
}