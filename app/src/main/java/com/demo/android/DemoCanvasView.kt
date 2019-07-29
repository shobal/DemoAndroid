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
        drawbg(canvas!!)

//        textBase(canvas!!)

//        drawTextDemo(canvas)

        /*//drawText,绘制居中文字
        drawCenter(canvas)
        //drawText,绘制居左文字
        drawLeft(canvas)
        //drawText,绘制居右边文字
        drawRight(canvas)*/

        //绘制路径
//        drawPath(canvas)
        //绘制弧形
//        drawPathArc(canvas)

        drawBitmap(canvas)

//        drawPicture(canvas)

//        drawBitmapMesh(canvas)
    }

    //绘制时候改变坐标
    fun drawBitmapMesh(canvas: Canvas) {
        if (mbitmap == null) {
            initBitmapMesh()
        }

        for (i in 0 until HEIGHT_SIZE + 1) {
            for (j in 0 until WIDTH_SIZE + 1) {
                //x坐标不变 verts[(i * (WIDTH_SIZE + 1) + j) * 2 + 0] += 0
                //k保证下一次的 对应点offset不一样
                val offset = Math.sin(j.toFloat() / WIDTH_SIZE * (2 * Math.PI) + k).toFloat()
                //y坐标改变，呈现正弦曲线
                verts[(i * (WIDTH_SIZE + 1) + j) * 2 + 1] = origs[(i * (WIDTH_SIZE + 1) + j) * 2 + 1] + offset * 50
            }
        }
        k += 0.3f

//        canvas.drawBitmapMesh(mbitmap, WIDTH_SIZE, HEIGHT_SIZE, verts, 0, colors, 0, null)
        canvas.drawBitmapMesh(mbitmap, WIDTH_SIZE, HEIGHT_SIZE, verts, 0, null, 0, null)
        postInvalidate()
    }

    var k = 0f
    //将图片划分成200*200个小格
    private val WIDTH_SIZE = 250
    private val HEIGHT_SIZE = 165
    //小格相交的总的点数
    private val COUNT = (WIDTH_SIZE + 1) * (HEIGHT_SIZE + 1)
    private val verts = FloatArray(COUNT * 2)
    private val origs = FloatArray(COUNT * 2)
    private val colors = IntArray(COUNT)

    var mbitmap: Bitmap? = null
    //初始化坐标
    fun initBitmapMesh() {
        var index = 0
        mbitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.testdog)
        val bitmapwidth = mbitmap!!.width
        val bitmapheight = mbitmap!!.height

        for (i in 0 until HEIGHT_SIZE + 1) {
            val fy = bitmapheight.toFloat() / HEIGHT_SIZE * i
            for (j in 0 until WIDTH_SIZE + 1) {
                /*if (i * (HEIGHT_SIZE + 1) + j % 3 == 0) {
                    colors[i * (HEIGHT_SIZE + 1) + j] = 0
                } else {
                    colors[i * (HEIGHT_SIZE + 1) + j] = 2
                }*/
                val fx = bitmapwidth.toFloat() / WIDTH_SIZE * j
                //偶数位记录x坐标  奇数位记录Y坐标
                verts[index * 2 + 0] = fx
                origs[index * 2 + 0] = fx
                verts[index * 2 + 1] = fy
                origs[index * 2 + 1] = fy
                index++
            }
        }
    }

    fun drawPicture(ccanvas: Canvas) {
        //使用Picture前请关闭硬件加速
        val picture = Picture()
        val canvas = picture.beginRecording(500, 500)
        canvas.translate(250f, 250f)
        canvas.drawCircle(0f, 0f, 100f, Paint())
        picture.endRecording()

        // 将Picture中的内容绘制在Canvas上,一般不使用
        picture.draw(ccanvas)

        ccanvas.drawPicture(picture, Rect(300, 300, 400, 800))
    }

    fun drawBitmap(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
        /**
         * left 距离左边位置
         * top 距离右边位置
         */
        canvas.drawBitmap(bitmap, 500f, 500f, Paint().apply {

        })

        /**
         * src 需要绘制的图片区域
         * dst 图片绘制位置的区域
         */
        canvas.drawBitmap(bitmap, Rect(0, 0, 80, 80), Rect(300, 300, 400, 400), Paint().apply {

        })


        /**
         *矩阵
         */
        val matrix = Matrix()
        matrix.postScale(0.5f, 0.5f)// 使用后乘
        matrix.postRotate(30f)
        matrix.postTranslate(50f, 50f)
        canvas.drawBitmap(bitmap, matrix, Paint().apply {

        })
    }

    fun drawTextRun(canvas: Canvas) {
        val tt = "sdgsdg史蒂夫"
        canvas?.drawTextRun(
            tt,
            0,
            tt.length - 1,
            0,
            tt.length - 1,
            width.toFloat() / 2f,
            height / 2f,
            false,
            Paint().apply {
                isAntiAlias = true
                color = Color.DKGRAY
                textSize = 50f
            })
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
        path.arcTo(RectF(200f, 200f, 500f, 500f), 180f, 180f)
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

    fun drawTextDemo(canvas: Canvas) {
        canvas?.drawText("大ffggH", width / 2f, height / 2f, Paint().apply {
            isAntiAlias = true
            color = Color.RED
            textSize = 50f
        })
    }

    fun drawCenter(canvas: Canvas) {
        val p1 = Paint().apply {
            isAntiAlias = true
            color = Color.RED
            textSize = 50f
            textAlign = Paint.Align.CENTER // x-文字中心坐标与x坐标对齐,y-baseline
        }

        val textHeight = (p1.fontMetrics.descent + (-p1.fontMetrics.ascent)) //文字高度
        val baselineToCenterLine = (textHeight / 2 - p1.fontMetrics.descent) //baseline与中线距离
        val baseline = height / 2f + baselineToCenterLine //计算baseline
        canvas?.drawText("大ffggH", width / 2f, baseline, p1)
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
    fun textBase(canvas: Canvas) {
        val p = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = 80f
            textAlign = Paint.Align.LEFT
        }
        // p.measureText("十多个粉丝地方") 宽度
        val rect = Rect()
        val txt = "Hyf1gF"
        p.getTextBounds(txt, 0, txt.length, rect) //文字矩形区域
        val y = 200f
        canvas?.drawText(txt, 150f, y, p)

        Log.i(
            "DemoCanvasView",
            "top=${p.fontMetrics.top},ascent=${p.fontMetrics.ascent},descent=${p.fontMetrics.descent},bottom=${p.fontMetrics.bottom},bottomToTop=${p.fontMetrics.bottom - p.fontMetrics.top},ascentTodescent=${p.fontMetrics.descent - p.fontMetrics.ascent},bToTop=${rect.height()}"
        )
        //top 负数
        canvas?.drawLine(120f, y - (-p.fontMetrics.top), 500f, y - (-p.fontMetrics.top), Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            strokeWidth = 5f
        })

        //ascent 负数
        canvas?.drawLine(120f, y - (-p.fontMetrics.ascent), 500f, y - (-p.fontMetrics.ascent), Paint().apply {
            isAntiAlias = true
            color = Color.BLUE
            strokeWidth = 5f
        })

        //中线 ?到底怎么计算文字高度比较好
        val textHeight = (p.fontMetrics.descent + (-p.fontMetrics.ascent))
//        val textHeight = (p.fontMetrics.bottom + (-p.fontMetrics.top))
//        val textHeight = rect.height()
        val centerToBaseLine = textHeight / 2 - p.fontMetrics.bottom
        canvas?.drawLine(120f, y - centerToBaseLine, 500f, y - centerToBaseLine, Paint().apply {
            isAntiAlias = true
            color = Color.CYAN
            strokeWidth = 5f
        })

        //baseline
        canvas?.drawLine(120f, y, 500f, y, Paint().apply {
            isAntiAlias = true
            color = Color.RED
            strokeWidth = 5f
        })


        //descent 正
        canvas?.drawLine(120f, y + p.fontMetrics.descent, 500f, y + p.fontMetrics.descent, Paint().apply {
            isAntiAlias = true
            color = Color.GREEN
            strokeWidth = 5f
        })

        //bottom 正
        canvas?.drawLine(120f, y + p.fontMetrics.bottom, 500f, y + p.fontMetrics.bottom, Paint().apply {
            isAntiAlias = true
            color = Color.GRAY
            strokeWidth = 5f
        })
    }

    fun drawbg(canvas: Canvas) {
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
    }

}