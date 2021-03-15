package com.example.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var myService: BoundSer? = null
    var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myConnection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName,
                                            service: IBinder) {
                val binder = service as BoundSer.MyLocalBinder
                myService = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                isBound = false
            }
        }
        val intent = Intent(this, BoundSer::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        val tv_time = findViewById<TextView>(R.id.tv_time)
        val btn_time = findViewById<Button>(R.id.btn_time)
        btn_time.setOnClickListener {
            showTime(tv_time)
        }

    }

    private fun showTime(view: TextView) {
        val currentTime = myService?.getCurrentTime()
        view.text = currentTime.toString()
    }
}