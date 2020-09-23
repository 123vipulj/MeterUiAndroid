package com.example.meteruiandroid.CustomMeterView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

public class PinAngleView(){
    var anglePinPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    var trainglePinPath = Path()
    var degreeRotated : Float = 360f


    fun pinAngleAt(canvas: Canvas?, width: Float){
        canvas?.save()
        drawPinPoint(canvas, width)
        canvas?.restore()
    }

    private fun drawPinPoint(canvas: Canvas?, width: Float){
        anglePinPaint.color = Color.BLACK
        anglePinPaint.style = Paint.Style.FILL_AND_STROKE
        anglePinPaint.strokeWidth = 2f

        circlePaint.color = Color.parseColor("#c22828")
        circlePaint.setShadowLayer(7f, 0f, 0f, Color.parseColor("#6d6767"))

        trainglePinPath.moveTo(width / 2f - 5, width / 2f)
        trainglePinPath.lineTo(width / 2f,  width / 4f)
        trainglePinPath.lineTo(width / 2f + 5, width / 2f )
        trainglePinPath.close()

        canvas?.rotate(degreeRotated, width / 2f - 5, width / 2f)
        canvas?.drawPath(trainglePinPath, anglePinPaint)

        canvas?.drawCircle(width / 2f  , width / 2f,10f, circlePaint)

    }

    fun updateDegreeRotated(degree: Float){
        degreeRotated = degree
    }

}