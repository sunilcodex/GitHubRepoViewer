package com.noname.app.ui.Library.CustomView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.coroutines.*

class ConsoleLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var textList: List<Pair<String, Int>> = emptyList()
    private var textIndex = 0
    private var job: Job? = null

    init {
        typeface = android.graphics.Typeface.MONOSPACE
        textSize = 14f
        setTextColor(Color.GREEN)
        gravity = Gravity.CENTER
    }

    fun setTextList(list: List<Pair<String, Int>>) {
        if (list.isNotEmpty()) {
            textList = list
            textIndex = 0
            startAnimation()
        }
    }

    private fun startAnimation() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive && textList.isNotEmpty()) {
                val (text, color) = textList[textIndex]
                setText(text)
                setTextColor(color)
                textIndex = (textIndex + 1) % textList.size
                delay(500)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job?.cancel()
    }
}