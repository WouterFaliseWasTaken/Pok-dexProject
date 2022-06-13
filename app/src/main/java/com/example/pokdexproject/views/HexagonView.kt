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

@Suppress("DEPRECATION")
class HexagonView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {
    var max = 0f
    var fillColorValue = 0xFF0000FF.toInt()
    var strokeColorValue = 0xFF000000.toInt()
    var minorWheels = 0
    var stats = listOf<Float>()
    var labels = mutableListOf("HP", "Attack", "Defense", "Speed", "Sp. Atk", "Sp. Def")
    var textHeight = 15.spToPx.toFloat()
    private var outerRadius = 200f
    private var innerRadius = 0f
    private var innerOffsetX = 0f
    private var innerOffsetY = 0f
    private var outerOffsetX = 0f
    private var outerOffsetY = 0f
    var textWhiteSpaceFactor = 0.85f

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
        this.color = resources.getColor(R.color.black);this.textSize = textHeight
        this.style = Paint.Style.FILL_AND_STROKE;this.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //resolve flexible layout parameters
        val widthMeasure = resolveSize(widthMeasureSpec, widthMeasureSpec)
        val heightMeasure = resolveSize(heightMeasureSpec, heightMeasureSpec)
        //calculate the amount of room the text takes up

        val textWidth = labels.run {
            listOf(
                this[1],
                this[2],
                this[4],
                this[5]
            ).maxOf { textPaint.measureText(it) }
        }
        //resolve padding
        var width = (widthMeasure - paddingStart - paddingEnd).toFloat()
        var height = (heightMeasure - paddingTop - paddingBottom).toFloat()
        //calculate largest outer hexagon radius
        outerRadius = minOf(
            (((height / 2) - textHeight)),
            ((width - (textWidth * 2)) / sqrt(3f))
        )
        //adjust layout parameters to outer hexagon radius + text labels
        height = (outerRadius + textHeight) * 2
        width = (outerRadius * sqrt(3f)) + (textWidth * 2)
        //verify and implement calculated layout parameters
        val h = resolveSizeAndState(height.toInt(), heightMeasureSpec, 1)
        val w = resolveSizeAndState(width.toInt(), widthMeasureSpec, 1)
        setMeasuredDimension(w, h)
        //calculate parameters for drawing
        //inner radius to allow spacing between text and drawn hexagon
        innerRadius = outerRadius * textWhiteSpaceFactor
        // (0,0) point of invisible hexagon
        outerOffsetX = textWidth
        outerOffsetY = textHeight
        // (0,0) point of drawn hexagon
        innerOffsetX = (textWidth + ((outerRadius - innerRadius) * (sqrt(3f) / 4)))
        innerOffsetY = (textHeight + ((outerRadius - innerRadius)) / 2)
    }

    override fun onDraw(canvas: Canvas?) {
        //spokes drawn in counterclockwise rotation,outward then across
        canvas?.drawHexagonWheel(innerRadius, (1.0F), innerOffsetX, innerOffsetY, majorThreadColor)
        canvas?.drawHexagonSpokes(innerRadius, innerOffsetX, innerOffsetY, majorThreadColor)
        for (i in 1..minorWheels) {
            canvas?.drawHexagonWheel(
                innerRadius,
                (1f / (minorWheels + 1) * i),
                innerOffsetX,
                innerOffsetY,
                minorThreadColor
            )
        }
        canvas?.drawHexagonContent(innerRadius, stats, max, innerOffsetX, innerOffsetY, fillColor)
        canvas?.drawLabels(
            outerRadius,
            (outerRadius - innerRadius),
            labels,
            outerOffsetX,
            outerOffsetY,
            textPaint
        )
        super.onDraw(canvas)
    }

    private fun Canvas.drawHexagonWheel(
        radius: Float,
        factor: Float,
        offsetX: Float,
        offsetY: Float,
        paint: Paint
    ) {
        val threadOffsetY = (innerRadius / 2) * factor
        val threadOffsetX = ((sqrt(3.0F) / 2 * innerRadius) * factor)

        val path = Path()
        path.moveTo(offsetX + innerRadius, (((2 * radius) * ((factor + 1) / 2)) + offsetY))
        path.lineTo((offsetX + radius + threadOffsetX), ((radius + threadOffsetY) + offsetY))
        path.lineTo((offsetX + radius + threadOffsetX), ((radius - threadOffsetY) + offsetY))
        path.lineTo(offsetX + radius, ((offsetY) + (radius * (1 - factor))))
        path.lineTo((offsetX + radius - threadOffsetX), ((radius - threadOffsetY) + offsetY))
        path.lineTo((offsetX + radius - threadOffsetX), ((radius + threadOffsetY) + offsetY))
        path.lineTo(offsetX + radius, (((2 * radius) * ((factor + 1) / 2)) + offsetY))
        drawPath(path, paint)
    }

    private fun Canvas.drawHexagonSpokes(
        radius: Float,
        offsetX: Float,
        offsetY: Float,
        paint: Paint
    ) {
        val threadOffsetY = radius / 2
        val threadOffsetX = (sqrt(3.0) / 2 * radius).toFloat()
        drawLine(
            offsetX + radius,
            ((2 * radius) + offsetY),
            offsetX + radius,
            offsetY,
            paint
        )
        drawLine(
            offsetX + radius + threadOffsetX,
            ((radius - threadOffsetY) + offsetY),
            offsetX + radius - threadOffsetX,
            ((radius + threadOffsetY) + offsetY),
            paint
        )
        drawLine(
            offsetX + radius + threadOffsetX,
            ((radius + threadOffsetY) + offsetY),
            offsetX + radius - threadOffsetX,
            ((radius - threadOffsetY) + offsetY),
            paint
        )
    }


    private fun Canvas.drawHexagonContent(
        radius: Float,
        stats: List<Float>,
        max: Float,
        offsetX: Float,
        offsetY: Float,
        fillColor: Paint
    ) {
        val factors = stats.map { it / max }
        val threadOffsetsY = factors.map { (radius / 2) * it }
        val threadOffsetsX = factors.map { ((sqrt(3.0F) / 2 * radius) * it) }
        val path = Path().apply {
            moveTo(offsetX + (radius), (((offsetY) + (radius * (1 - factors[0])))))
            lineTo((offsetX + radius + threadOffsetsX[1]), (radius - threadOffsetsY[1] + offsetY))
            lineTo((offsetX + radius + threadOffsetsX[2]), (radius + threadOffsetsY[2] + offsetY))
            lineTo(offsetX + radius, (((2 * radius) * ((factors[3] + 1) / 2)) + offsetY))
            lineTo((offsetX + radius - threadOffsetsX[4]), (radius + threadOffsetsY[4] + offsetY))
            lineTo((offsetX + radius - threadOffsetsX[5]), (radius - threadOffsetsY[5] + offsetY))
        }
        drawPath(path, fillColor)
    }


    private fun Canvas.drawLabels(
        radius: Float,
        radiusDelta: Float,
        labels: List<String>,
        offsetX: Float,
        offsetY: Float,
        textPaint: Paint
    ) {
        val threadOffsetY = radius / 2
        val threadOffsetX = sqrt(3.0F) / 2 * radius
        drawText(
            labels[0],
            ((radius + offsetX) - radiusDelta / 2),
            offsetY,
            textPaint.apply { this.textAlign = Paint.Align.CENTER })
        drawText(
            labels[1],
            ((radius + offsetX + threadOffsetX) - radiusDelta),
            (radius - threadOffsetY + offsetY),
            textPaint.apply { textAlign = Paint.Align.LEFT })
        drawText(
            labels[2],
            ((radius + offsetX + threadOffsetX) - radiusDelta),
            (radius + threadOffsetY + offsetY - textHeight),
            textPaint.apply { textAlign = Paint.Align.LEFT })
        drawText(
            labels[3],
            ((radius + offsetX) - radiusDelta / 2),
            (2 * radius + offsetY - textHeight),
            textPaint.apply { textAlign = Paint.Align.CENTER })
        drawText(
            labels[4],
            (radius + offsetX - threadOffsetX),
            (radius + threadOffsetY + offsetY - textHeight),
            textPaint.apply { textAlign = Paint.Align.RIGHT })
        drawText(
            labels[5],
            (radius + offsetX - threadOffsetX),
            (radius - threadOffsetY + offsetY),
            textPaint.apply { textAlign = Paint.Align.RIGHT })
    }
}
