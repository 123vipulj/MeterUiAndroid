package com.example.meteruiandroid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.meteruiandroid.CustomMeterView.CustomMeterView
import com.example.meteruiandroid.CustomProgressBar.CustomProgressArc


class MainActivity : AppCompatActivity() {
    var firstValue : Int = 0
    var lastValue : Int = 270

    lateinit var mainHandler: Handler

    lateinit var customMeterView : CustomMeterView
    lateinit var customProgressArc: CustomProgressArc

    private val updateTask = object : Runnable{
        override fun run() {
            firstValue = (270..450).random()
            customMeterView.startAnim(lastValue, firstValue)
            customMeterView.updateTextTitle("$firstValue KM/HR")
            // customMeterView.updatedDegreeValue(firstValue.toFloat())
            lastValue = firstValue
            mainHandler.postDelayed(this, 1000)
        }
    }

    private val updateAngle = object : Runnable{
        override fun run() {
            firstValue = (0..180).random()
            customProgressArc.startAnim(lastValue, firstValue)
            lastValue = firstValue
            mainHandler.postDelayed(this, 1000)
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
        customProgressArc = findViewById(R.id.customProgressArc)

        mainHandler = Handler(Looper.getMainLooper())

        mainHandler.postDelayed(updateAngle, 700)

       customMeterView.setOnClickListener {
           mainHandler.removeCallbacks(updateTask)
       }

        customProgressArc.setOnClickListener {
            mainHandler.removeCallbacks(updateAngle)
        }
    }
}