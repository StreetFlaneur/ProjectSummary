package com.library.leftdrawer.contract

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

import com.techidea.library.R

import java.util.ArrayList
import java.util.Arrays

/**
 * Created by sam on 2018/2/6.
 */

class SlideBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

    private var onTouchingLetterChangedListener: OnTouchingLetterChangedListener? = null
    private var letterList: List<String>? = null
    private var choose = -1
    private val paint = Paint()
    private var mTextDialog: TextView? = null

    init {
        init()
    }

    private fun init() {
        setBackgroundColor(Color.parseColor("#00FFFFFF"))
        letterList = Arrays.asList(*INDEX_STRING)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val height = height// 获取对应高度
        val width = width// 获取对应宽度
        val singleHeight = height / letterList!!.size// 获取每一个字母的高度
        for (i in letterList!!.indices) {
            paint.color = Color.parseColor("#606060")
            paint.typeface = Typeface.DEFAULT_BOLD
            paint.isAntiAlias = true
            paint.textSize = 32f
            // 选中的状态
            if (i == choose) {
                paint.color = Color.parseColor("#4F41FD")
                paint.isFakeBoldText = true
            }
            // x坐标等于中间-字符串宽度的一半.
            val xPos = width / 2 - paint.measureText(letterList!![i]) / 2
            val yPos = (singleHeight * i + singleHeight / 2).toFloat()
            canvas.drawText(letterList!![i], xPos, yPos, paint)
            paint.reset()// 重置画笔
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val y = event.y// 点击y坐标
        val oldChoose = choose
        val listener = onTouchingLetterChangedListener
        val c = (y / height * letterList!!.size).toInt()// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        when (action) {
            MotionEvent.ACTION_UP -> {
                setBackgroundColor(Color.parseColor("#00FFFFFF"))
                choose = -1
                invalidate()
                if (mTextDialog != null) {
                    mTextDialog!!.visibility = View.GONE
                }
            }
            else -> {
                setBackgroundColor(context.resources.getColor(R.color.grey))
                if (oldChoose != c) {
                    if (c >= 0 && c < letterList!!.size) {
                        listener?.onTouchingLetterChanged(letterList!![c])
                        if (mTextDialog != null) {
                            mTextDialog!!.text = letterList!![c]
                            mTextDialog!!.visibility = View.VISIBLE
                        }
                        choose = c
                        invalidate()
                    }
                }
            }
        }
        return true
    }

    fun setIndexText(indexStrings: ArrayList<String>) {
        this.letterList = indexStrings
        invalidate()
    }

    /**
     * 为SideBar设置显示当前按下的字母的TextView
     *
     * @param mTextDialog
     */
    fun setTextView(mTextDialog: TextView) {
        this.mTextDialog = mTextDialog
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    fun setOnTouchingLetterChangedListener(
            onTouchingLetterChangedListener: OnTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener
    }

    /**
     * 接口
     */
    interface OnTouchingLetterChangedListener {
        fun onTouchingLetterChanged(s: String)
    }

    companion object {

        var INDEX_STRING = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }
}
