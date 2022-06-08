package com.example.pokdexproject.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.pokdexproject.R
import com.example.pokdexproject.commonCode.dpToPx
import com.example.pokdexproject.commonCode.spToPx
import kotlin.math.sqrt

class HexagonView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {
    var max = 0f
    private var fillColorValue = 0xFF0000FF.toInt()
    private var strokeColorValue = 0xFF000000.toInt()
    private var minorWheels = 0
    var stats = listOf<Float>()
    val labels = listOf("HP", "Attack", "Defense", "Speed", "Sp. Atk", "Sp. Def")

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.HexagonView,
            0, 0
        ).apply {
            try {
                strokeColorValue = getInteger(
                    R.styleable.HexagonView_strokeColorV,
                    resources.getColor(R.color.black)
                )
                fillColorValue = getInteger(
                    R.styleable.HexagonView_fillColorV,
                    resources.getColor(R.color.semi_transparant_blue)
                )
                max = getInteger(R.styleable.HexagonView_max, 100).toFloat()
                minorWheels = getInteger(R.styleable.HexagonView_minorWheels, 4)
            } finally {
                recycle()
            }
        }
    }

    private val majorThreadColor = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = strokeColorValue
        this.style = Paint.Style.STROKE;this.strokeWidth =
        3.dpToPx.toFloat()
    }
    private val minorThreadColor = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = strokeColorValue;this.style =
        Paint.Style.STROKE;this.strokeWidth =
        1.dpToPx.toFloat()
    }
    private val fillColor = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = fillColorValue;this.style = Paint.Style.FILL
    }
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = resources.getColor(R.color.black);this.textSize = 15.spToPx.toFloat()
        this.style = Paint.Style.FILL_AND_STROKE;this.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = heightMeasureSpec
        val radius = (height - paddingTop - paddingBottom / 2f) - 2.dpToPx
        val width = (radius * sqrt(3f) * 1.3).toInt()
        val w = resolveSizeAndState(width, (widthMeasureSpec - paddingStart - paddingEnd), 1)
        height = w / sqrt(3f).toInt()
        val h = resolveSizeAndState(height, heightMeasureSpec, 1)
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        val offsetX = paddingStart + 25.dpToPx
        val offsetY = paddingTop + 100.dpToPx
        val radius = ((height - paddingTop - paddingBottom) / 4f) - 2.dpToPx

        //spokes drawn in counterclockwise rotation,outward then across
        canvas?.drawHexagonWheel(radius, (1.0F), offsetX, offsetY, majorThreadColor)
        canvas?.drawHexagonSpokes(radius, offsetX, offsetY, majorThreadColor)
        for (i in 1..minorWheels) {
            canvas?.drawHexagonWheel(
                radius,
                (1f / (minorWheels + 1) * i),
                offsetX,
                offsetY,
                minorThreadColor
            )
        }
        canvas?.drawHexagonContent(radius, stats, max, offsetX, offsetY, fillColor)
        canvas?.drawLabelsTop(radius, labels, offsetX, offsetY, (1.15f), textPaint)
        super.onDraw(canvas)
    }

    private fun Canvas.drawHexagonWheel(
        radius: Float,
        factor: Float,
        offsetX: Int,
        offsetY: Int,
        paint: Paint
    ) {
        val threadOffsetY = (radius / 2) * factor
        val threadOffsetX = ((sqrt(3.0F) / 2 * radius) * factor)

        val path = Path()
        path.moveTo(offsetX +radius, (((2 * radius) * ((factor + 1) / 2)) + offsetY))
        path.lineTo((offsetX +radius + threadOffsetX), ((radius + threadOffsetY) + offsetY))
        path.lineTo((offsetX +radius + threadOffsetX), ((radius - threadOffsetY) + offsetY))
        path.lineTo(offsetX +radius, ((offsetY).toFloat() + (radius * (1 - factor))))
        path.lineTo((offsetX +radius - threadOffsetX), ((radius - threadOffsetY) + offsetY))
        path.lineTo((offsetX +radius - threadOffsetX), ((radius + threadOffsetY) + offsetY))
        path.lineTo(offsetX +radius, (((2 * radius) * ((factor + 1) / 2)) + offsetY))
        drawPath(path, paint)
    }

    private fun Canvas.drawHexagonSpokes(
        radius: Float,
        offsetX: Int,
        offsetY: Int,
        paint: Paint
    ) {
        val threadOffsetY = radius / 2
        val threadOffsetX = (sqrt(3.0) / 2 * radius).toFloat()
        drawLine(offsetX +radius, ((2 * radius) + offsetY), offsetX +radius, offsetY.toFloat(), paint)
        drawLine(
            offsetX +radius + threadOffsetX,
            ((radius - threadOffsetY) + offsetY),
            offsetX +radius - threadOffsetX,
            ((radius + threadOffsetY) + offsetY),
            paint
        )
        drawLine(
            offsetX +radius + threadOffsetX,
            ((radius + threadOffsetY) + offsetY),
            offsetX +radius - threadOffsetX,
            ((radius - threadOffsetY) + offsetY),
            paint
        )
    }


    private fun Canvas.drawHexagonContent(
        radius: Float,
        stats: List<Float>,
        max: Float,
        offsetX: Int,
        offsetY: Int,
        fillColor: Paint
    ) {
        val factors = stats.map { it / max }
        val threadOffsetsY = factors.map { (radius / 2) * it }
        val threadOffsetsX = factors.map { ((sqrt(3.0F) / 2 * radius) * it) }
        val path = Path().apply {
            moveTo(offsetX +(radius), (((offsetY).toFloat() + (radius * (1 - factors[0])))))
            lineTo((offsetX +radius + threadOffsetsX[1]), (radius - threadOffsetsY[1] + offsetY))
            lineTo((offsetX +radius + threadOffsetsX[2]), (radius + threadOffsetsY[2] + offsetY))
            lineTo(offsetX +radius, (((2 * radius) * ((factors[3] + 1) / 2)) + offsetY))
            lineTo((offsetX +radius - threadOffsetsX[4]), (radius + threadOffsetsY[4] + offsetY))
            lineTo((offsetX +radius - threadOffsetsX[5]), (radius - threadOffsetsY[5] + offsetY))
        }
        drawPath(path, fillColor)
    }
}

private fun Canvas.drawLabelsTop(
    radius: Float,
    labels: List<String>,
    offsetX: Int,
    offsetY: Int,
    textFactor: Float,
    textPaint: Paint
) {
    val threadOffsetY = (radius / 2) * textFactor
    val threadOffsetX = ((sqrt(3.0F) / 2 * radius) * textFactor)
    drawText(
        labels[0],
        radius + offsetX,
        offsetY + (radius * (1 - textFactor)),
        textPaint.apply { this.textAlign = Paint.Align.CENTER })
    drawText(
        labels[1],
        (radius + offsetX + (threadOffsetX * textFactor)),
        (radius - (threadOffsetY * textFactor) + offsetY),
        textPaint.apply { textAlign = Paint.Align.LEFT })
    drawText(
        labels[5],
        (radius + offsetX - (threadOffsetX * textFactor)),
        (radius - (threadOffsetY * textFactor) + offsetY),
        textPaint.apply { textAlign = Paint.Align.RIGHT })
}
