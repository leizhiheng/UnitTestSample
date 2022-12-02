package com.ubt.unittestsample.espresso

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ubt.unittestsample.R

class EspressoTestActivity : AppCompatActivity() {

    lateinit var tvMain: TextView
    lateinit var btnMainJump: TextView
    lateinit var btnMain: Button
    lateinit var etMain: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espresso_test)

        btnMainJump = findViewById(R.id.btn_main_jump)
        btnMain = findViewById(R.id.btn_main)
        tvMain = findViewById(R.id.tv_main)
        etMain = findViewById(R.id.et_main)

        btnMainJump.setOnClickListener {
            var intent = Intent(it.context, SubEspressTestActivity::class.java)
            intent.putExtra("name", "lzh")
            startActivity(intent)
        }

        btnMain.setOnClickListener {
            Toast.makeText(it.context, getString(R.string.hello_toast_test), Toast.LENGTH_LONG).show()
        }
    }
}