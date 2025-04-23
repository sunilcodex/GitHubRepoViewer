package com.ameyaa.app.ui.Library.CustomView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.animation.ValueAnimator
import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat
import com.noname.app.R

class GhibliThaliView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var rotationAngle = 0f
    private var thaliDrawable: VectorDrawable? = null

    init {
        thaliDrawable = ContextCompat.getDrawable(context, R.drawable.thali_ic) as VectorDrawable
        startAnimation()
    }

    private fun startAnimation() {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 5000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                rotationAngle = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val drawableSize = width.coerceAtMost(height) * 0.8f
        val left = centerX - drawableSize / 2
        val top = centerY - drawableSize / 2
        val right = centerX + drawableSize / 2
        val bottom = centerY + drawableSize / 2

        canvas.save()
        canvas.rotate(rotationAngle, centerX, centerY)
        thaliDrawable?.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        thaliDrawable?.draw(canvas)
        canvas.restore()
    }
}
