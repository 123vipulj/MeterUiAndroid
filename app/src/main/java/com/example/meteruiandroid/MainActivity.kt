package com.example.meteruiandroid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.meteruiandroid.CustomMeterView.CustomMeterView


class MainActivity : AppCompatActivity() {
    var firstValue : Int = 0
    var lastValue : Int = 270
    lateinit var mainHandler: Handler
    lateinit var customMeterView : CustomMeterView
    private val updateTask = object : Runnable{
        override fun run() {
            firstValue = (270..350).random()
            customMeterView.startAnim(lastValue, firstValue)
            customMeterView.updateTextTitle("$firstValue KM")
            // customMeterView.updatedDegreeValue(firstValue.toFloat())
            lastValue = firstValue
            mainHandler.postDelayed(this, 700)
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTask)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mainHandler.removeCallbacks(updateTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainHandler.removeCallbacks(updateTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customMeterView = findViewById<CustomMeterView>(R.id.customViewMeter)

        mainHandler = Handler(Looper.getMainLooper())

        mainHandler.postDelayed(updateTask, 700)

       customMeterView.setOnClickListener {
           mainHandler.removeCallbacks(updateTask)
       }
    }
}