package com.example.meteruiandroid.CustomProgressBar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import kotlin.math.cos
import kotlin.math.sin

class CustomProgressArc(context: Context?, attrs: AttributeSet?) : View(context, attrs), ValueAnimator.AnimatorUpdateListener {
    var paintArc = Paint(Paint.ANTI_ALIAS_FLAG)
    var updateArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var segmentArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var updateSegmentArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    var updateSegmentValue = 0
    var updateAngleValue = 0
    var mAnimator = ValueAnimator()
    var mPadding = 170f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawArc(canvas)
        updateArcValue(canvas)
        updateSegmentArcValue(canvas)
    }

    private fun drawArc(canvas: Canvas?){
        paintArc.style = Paint.Style.STROKE
        paintArc.strokeWidth = 60f
        paintArc.color = Color.parseColor("#c4c0c0")
        paintArc.strokeCap = Paint.Cap.ROUND
        paintArc.strokeJoin = Paint.Join.ROUND

        segmentArcPaint.style = Paint.Style.STROKE
        segmentArcPaint.strokeWidth = 20f
        segmentArcPaint.color = Color.LTGRAY
        segmentArcPaint.strokeCap = Paint.Cap.ROUND
        segmentArcPaint.strokeJoin = Paint.Join.ROUND

        canvas?.drawArc(100f, 100f, width - 100f, width.toFloat() , 180f, 180f, false, paintArc)

        var rectF = RectF(mPadding, mPadding, width - mPadding, width - mPadding + 100)


        canvas?.drawArc(
            rectF, 180f,
            180f, false, segmentArcPaint
        )

    }

    private fun updateArcValue(canvas: Canvas?){
        updateArcPaint.style = Paint.Style.STROKE
        updateArcPaint.strokeWidth = 60f
        updateArcPaint.color = Color.BLACK
        updateArcPaint.strokeCap = Paint.Cap.ROUND
        updateArcPaint.strokeJoin = Paint.Join.ROUND
        updateArcPaint.setShadowLayer(5.5f,4.0f, 4.0f, Color.parseColor("#928e8e"))
        canvas?.drawArc(100f, 100f, width - 100f, width.toFloat(), 180f,
            updateAngleValue.toFloat(),false, updateArcPaint)
    }

    private fun updateSegmentArcValue(canvas: Canvas?){
        updateSegmentArcPaint.style = Paint.Style.STROKE
        updateSegmentArcPaint.strokeWidth = 20f
        updateSegmentArcPaint.shader =
            LinearGradient(0f,0f,0f, width.toFloat(), Color.LTGRAY, Color.BLUE, Shader.TileMode.MIRROR)
        updateSegmentArcPaint.strokeCap = Paint.Cap.ROUND
        updateSegmentArcPaint.strokeJoin = Paint.Join.ROUND

        var rectF = RectF(mPadding, mPadding, width - mPadding, width - mPadding + 100)


        canvas?.drawArc(
            rectF, 180f,
            updateSegmentValue.toFloat(), false, updateSegmentArcPaint
        )

    }

    private fun updateAngle(upAngleVal : Int){
        updateAngleValue = upAngleVal
    }

    private fun updateSegAngle(upAngleVal: Int){
        updateSegmentValue = upAngleVal
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        val value = animation?.animatedValue as Int
        updateAngle(value)
        updateSegAngle(value)
        postInvalidateOnAnimation()
    }

    fun startAnim(lastValue: Int, firstValue: Int){
        mAnimator = ValueAnimator.ofInt(lastValue, firstValue)
        mAnimator.duration = 600
        mAnimator.addUpdateListener(this)
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.start()
    }

}