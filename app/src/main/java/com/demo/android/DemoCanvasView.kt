package com.demo.android

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class DemoCanvasView(context: Context?) : View(context) {

    constructor(context: Context, attrs: AttributeSet) : this(context)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //bg
        canvas?.drawRect(Rect(0, 0, width, height), Paint().apply {
            isAntiAlias = true
            color = Color.BLUE
            strokeWidth = 10f
            style = Paint.Style.STROKE
        })
        val lineHP = Paint()
        lineHP?.apply {
            isAntiAlias = true
            color = Color.BLUE
            textSize = 5f
        }
        canvas?.drawLine(0f, height / 2f, width.toFloat(), height / 2f, lineHP)
        canvas?.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), lineHP)

        base(canvas!!)

/*        //drawText,绘制居中文字
        drawCenter(canvas)

        //drawText,绘制居左文字
        drawLeft(canvas)

        //drawText,绘制居右边文字
        drawRight(canvas)*/

        //绘制路径
        drawPath(canvas)

        //绘制弧形
        drawPathArc(canvas)
    }


    fun drawPath(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, height / 2f)
        path.lineTo(width.toFloat() / 2f, height / 2f)
        path.lineTo(width.toFloat() / 2f, height / 2f - 200)
        /**
         * path y坐标的位置为baseline
         * hOffset 水平平移
         * vOffset 垂直平移，文字要居中要用到
         */
        canvas.drawTextOnPath("三三fgasdfasdgssadg我二", path, 0f, 20f, Paint().apply {
            isAntiAlias = true
            color = Color.DKGRAY
            textSize = 50f
            textAlign = Paint.Align.RIGHT //文字右对齐
//            textAlign = Paint.Align.CENTER //文字中间对齐
//            textAlign = Paint.Align.LEFT //文字左对齐
        })
    }

    fun drawPathArc(canvas: Canvas) {
        val path = Path()
        path.arcTo(RectF(200f, 200f, 600f, 600f), 180f, 180f)
        /**
         * path y坐标的位置为baseline
         * hOffset 水平平移
         * vOffset 垂直平移，文字要居中要用到
         */
        canvas.drawTextOnPath("三三fgasdfasdsadg我二", path, 0f, 0f, Paint().apply {
            isAntiAlias = true
            color = Color.DKGRAY
            textSize = 50f
            textAlign = Paint.Align.RIGHT //文字右对齐
//            textAlign = Paint.Align.CENTER //文字中间对齐
//            textAlign = Paint.Align.LEFT //文字左对齐
        })
    }

    fun drawCenter(canvas: Canvas) {
        val p1 = Paint().apply {
            isAntiAlias = true
            color = Color.RED
            textSize = 50f
            textAlign = Paint.Align.CENTER // x-文字中心坐标与x坐标对齐,y-baseline
        }

//        canvas?.drawText("大ffggH", width / 2f, height / 2f, p1)
        val textHeight = (p1.fontMetrics.descent + (-p1.fontMetrics.ascent))
        canvas?.drawText("大ffggH", width / 2f, height / 2f + (textHeight / 2 - p1.fontMetrics.descent), p1)
    }

    fun drawLeft(canvas: Canvas) {
        val p1 = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = 50f
            textAlign = Paint.Align.LEFT // x-文字最左边坐标与x坐标对齐,y-baseline 默认
        }

        val textHeight = (p1.fontMetrics.descent + (-p1.fontMetrics.ascent))
        canvas?.drawText("大ffggH", 0, 4, 0f, height / 2f + (textHeight / 2 - p1.fontMetrics.descent), p1)
    }

    fun drawRight(canvas: Canvas) {
        val p1 = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = 50f
            textAlign = Paint.Align.RIGHT // x-文字最右边坐标为x坐标对齐,y-baseline
        }
        val textHeight = (p1.fontMetrics.descent + (-p1.fontMetrics.ascent))
        canvas?.drawText("大ffggH", width.toFloat(), height / 2f + (textHeight / 2 - p1.fontMetrics.descent), p1)
    }


    //基本线
    fun base(canvas: Canvas) {
        val p = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = 80f
            textAlign = Paint.Align.LEFT
        }
        // p.measureText("十多个粉丝地方") 宽度
        val rect = Rect()
        val txt = "Helloyf1gF,安卓"
        p.getTextBounds(txt, 0, txt.length, rect) //文字矩形区域
        val y = 200f
        canvas?.drawText(txt, 150f, y, p)

        Log.i(
            "DemoCanvasView",
            "top=${p.fontMetrics.top},ascent=${p.fontMetrics.ascent},descent=${p.fontMetrics.descent},bottom=${p.fontMetrics.bottom},bottomToTop=${p.fontMetrics.bottom - p.fontMetrics.top},ascentTodescent=${p.fontMetrics.descent - p.fontMetrics.ascent},bToTop=${rect.height()}"
        )
        //top 负数
        canvas?.drawLine(120f, y - (-p.fontMetrics.top), 800f, y - (-p.fontMetrics.top), Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            strokeWidth = 5f
        })

        //ascent 负数
        canvas?.drawLine(120f, y - (-p.fontMetrics.ascent), 800f, y - (-p.fontMetrics.ascent), Paint().apply {
            isAntiAlias = true
            color = Color.BLUE
            strokeWidth = 5f
        })

        //中线 ?到底怎么计算文字高度比较好
        val textHeight = (p.fontMetrics.descent + (-p.fontMetrics.ascent))
//        val textHeight = (p.fontMetrics.bottom + (-p.fontMetrics.top))
//        val textHeight = rect.height()
        val centerToBaseLine = textHeight / 2 - p.fontMetrics.bottom
        canvas?.drawLine(120f, y - centerToBaseLine, 800f, y - centerToBaseLine, Paint().apply {
            isAntiAlias = true
            color = Color.CYAN
            strokeWidth = 5f
        })

        //baseline
        canvas?.drawLine(120f, y, 800f, y, Paint().apply {
            isAntiAlias = true
            color = Color.RED
            strokeWidth = 5f
        })

        //baseline
        canvas?.drawLine(120f, y, 800f, y, Paint().apply {
            isAntiAlias = true
            color = Color.RED
            strokeWidth = 5f
        })

        //descent 正
        canvas?.drawLine(120f, y + p.fontMetrics.descent, 800f, y + p.fontMetrics.descent, Paint().apply {
            isAntiAlias = true
            color = Color.GREEN
            strokeWidth = 5f
        })

        //bottom 正
        canvas?.drawLine(120f, y + p.fontMetrics.bottom, 800f, y + p.fontMetrics.bottom, Paint().apply {
            isAntiAlias = true
            color = Color.GRAY
            strokeWidth = 5f
        })
    }

}