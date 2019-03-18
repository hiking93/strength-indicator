package com.hiking.strengthindicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import kotlin.math.max
import kotlin.math.roundToInt

class StrengthIndicatorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    data class State(
            val text: String,
            @ColorInt val color: Int
    )

    private val density = resources.displayMetrics.density

    var stateIndex = 0
        set(value) {
            field = value
            requestLayout()
        }

    var barHeight = 4f * density
        set(value) {
            field = value
            requestLayout()
        }

    var barGap = 8f * density
        set(value) {
            field = value
            requestLayout()
        }

    var inactiveBarColor = 0xffe2e7ea.toInt()
        set(value) {
            field = value
            invalidate()
        }

    var textToBarGap = 8f * density
        set(value) {
            field = value
            requestLayout()
            requestLayout()
        }

    var textSize = resources.getDimension(R.dimen.text_caption)
        set(value) {
            field = value
            requestLayout()
            textPaint.textSize = value
            requestLayout()
        }

    val states = listOf(
            State(getFormattedStrengthText(R.string.strength_very_weak),
                    getColor(R.color.strength_very_weak)),
            State(getFormattedStrengthText(R.string.strength_weak),
                    getColor(R.color.strength_weak)),
            State(getFormattedStrengthText(R.string.strength_medium),
                    getColor(R.color.strength_medium)),
            State(getFormattedStrengthText(R.string.strength_strong),
                    getColor(R.color.strength_strong)),
            State(getFormattedStrengthText(R.string.strength_very_strong),
                    getColor(R.color.strength_very_strong))
    )

    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.textSize = this@StrengthIndicatorView.textSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val desiredBarWidth =
                (paddingLeft + paddingRight + barGap * (2 * states.size - 1))
        val desiredTextWidth = textPaint.measureText(states[stateIndex].text)
        val desiredWidth = max(desiredBarWidth, desiredTextWidth).roundToInt()
        val desiredHeight = (paddingTop + paddingBottom + barHeight + textToBarGap -
                textPaint.fontMetrics.top + textPaint.fontMetrics.bottom).roundToInt()

        val widthResult = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                if (widthSize < desiredWidth) widthSize or View.MEASURED_STATE_TOO_SMALL
                else widthSize
            }
            MeasureSpec.AT_MOST -> {
                if (widthSize < desiredWidth) widthSize or View.MEASURED_STATE_TOO_SMALL
                else desiredWidth
            }
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> throw RuntimeException("Unknown width mode $widthMode")
        }
        val heightResult = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                if (heightSize < desiredHeight) heightSize or View.MEASURED_STATE_TOO_SMALL
                else heightSize
            }
            MeasureSpec.AT_MOST -> {
                if (heightSize < desiredHeight) heightSize or View.MEASURED_STATE_TOO_SMALL
                else desiredHeight
            }
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> throw RuntimeException("Unknown width mode $widthMode")
        }
        setMeasuredDimension(widthResult, heightResult)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val stateIndex = stateIndex
        val state = states[stateIndex]

        var left = paddingLeft.toFloat()
        var top = paddingTop.toFloat()

        val activeBarColor = state.color
        val barWidth = (width - paddingLeft - paddingRight
                - (states.size - 1) * barGap) / states.size.toFloat()
        if (barWidth > 0) {
            for (i in states.indices) {
                barPaint.color = if (i <= stateIndex) activeBarColor else inactiveBarColor
                canvas.drawRect(left, top, left + barWidth, top + barHeight, barPaint)
                left += barWidth + barGap
            }
        }

        textPaint.color = activeBarColor
        left = paddingLeft.toFloat()
        top += barHeight + textToBarGap - textPaint.fontMetrics.top
        canvas.drawText(state.text, left, top, textPaint)
    }

    private fun getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(context, colorRes)

    private fun getFormattedStrengthText(@StringRes stringRes: Int) =
            context.getString(R.string.strength_format, context.getString(stringRes))
}