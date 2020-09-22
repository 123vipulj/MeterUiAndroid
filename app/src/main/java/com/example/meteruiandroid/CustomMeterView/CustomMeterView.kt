package com.example.meteruiandroid.CustomMeterView

import android.animation.ValueAnimator
import android.content.Context
import android.database.MatrixCursor
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator

class CustomMeterView(context: Context?, attrs: AttributeSet?) : View(context, attrs), ValueAnimator.AnimatorUpdateListener{
    var arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var anglePinPain = Paint(Paint.ANTI_ALIAS_FLAG)
    var textKmPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var rectBackPain = Paint(Paint.ANTI_ALIAS_FLAG)

    var meterTxtTitle : String = ""
    var trainglePinPath = Path()

    var mAnimator = ValueAnimator()

    var pinAngleView : PinAngleView = PinAngleView()

    var mPadding = 16f
    var mSection  = 30
    var mFullArcSliceLength = 360 / mSection
    var mArcSectionGap = mFullArcSliceLength / 2
    var mColorArcLineLength = mFullArcSliceLength - 4 * mArcSectionGap;

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        arcMeterUi(canvas)
        pinAngleView.pinAngleAt(canvas, width.toFloat())
        drawRectForTextField(canvas)
        drawTextField(canvas)
    }

    private fun arcMeterUi(canvas: Canvas?){
        arcPaint.color = Color.BLACK
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 10f

        var rectF = RectF(mPadding,mPadding, width -  mPadding, width -  mPadding)

        for (i in 0..mSection){
            canvas?.drawArc(rectF, (180 +  (i * 6f)),
                1f, false , arcPaint)
        }

    }

    fun drawTextField(canvas: Canvas?){
        var digitalFontType = Typeface.createFromAsset(context.assets, "fonts/monodigital.ttf")
        textKmPaint.strokeWidth = 2f
        textKmPaint.color = Color.CYAN
        textKmPaint.typeface = digitalFontType
        rectBackPain.alpha = 35
        textKmPaint.textSize = 55f
        canvas?.drawText(meterTxtTitle, width - width /3f, width /3f, textKmPaint)
    }

    fun drawRectForTextField(canvas: Canvas?){
        rectBackPain.style = Paint.Style.FILL_AND_STROKE
        rectBackPain.strokeWidth = 12f
        rectBackPain.color = Color.DKGRAY
        rectBackPain.alpha = 200
        rectBackPain.strokeJoin = Paint.Join.ROUND
        canvas?.drawRect(width - width /3f - 5, width / 3f - 50, width - width / 3f + 160, width / 3f+ 20, rectBackPain)
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        val value = animation?.animatedValue as Int
        pinAngleView.updateDegreeRotated(value.toFloat())
        postInvalidateOnAnimation()
    }

    fun startAnim(lastValue: Int, firstValue: Int){
        mAnimator = ValueAnimator.ofInt(lastValue, firstValue)
        mAnimator.duration = 600
        mAnimator.addUpdateListener(this)
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.start()
    }

    fun updatedDegreeValue(degree : Float){
        pinAngleView.updateDegreeRotated(degree)
        postInvalidateOnAnimation()
    }

    fun updateTextTitle(txt: String){
        meterTxtTitle = txt
        postInvalidateOnAnimation()
    }
}