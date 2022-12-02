package com.ubt.unittestsample.robolectric.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.ubt.unittestsample.R

class SampleActivity : AppCompatActivity() {
    private lateinit var lifecycleTextView: TextView
    private lateinit var inverseCheckBox: CheckBox
    var isTaskFinish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_simple)
        lifecycleTextView = findViewById(R.id.tv_lifecycle_value)
        inverseCheckBox = findViewById(R.id.checkbox)
        lifecycleTextView.setText(R.string.sample_lifecycle_oncreate)
    }

    fun forward(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun showDialog(view: View) {
        val alertDialog: AlertDialog =
            AlertDialog.Builder(this).setMessage(R.string.sample_dialog_message)
                .setTitle(R.string.sample_dialog_title).create()
        alertDialog.show()
    }

    fun showToast(view: View) {
        Toast.makeText(this, "Dialog unit test", Toast.LENGTH_LONG).show()
    }

    fun inverse(view: View?) {
        inverseCheckBox.setChecked(!inverseCheckBox.isChecked)
    }

    fun showDemo(view: View) {
        forward(view)
    }

    fun executeDelayedTask(view: View?) {
        Handler().postDelayed({ isTaskFinish = true }, 2000)
    }

//    fun callback(view: View?) {
//        startActivity(Intent(this, CallbackActivity::class.java))
//    }

    override fun onResume() {
        super.onResume()
        lifecycleTextView.setText(R.string.sample_lifecycle_onResume)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleTextView.setText(R.string.sample_lifecycle_onDestroy)
    }
}