package com.example.moviecataloguev2.utils.recyclerview

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewScroll: RecyclerView.OnScrollListener() {
    private var scrollDist = 0
    private var isVisible = true
    companion object {
        private const val MINIMUM = 25
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (isVisible && scrollDist > MINIMUM) {
            hide()
            scrollDist = 0
            isVisible = false
        } else if (!isVisible && scrollDist < -MINIMUM) {
            show()
            scrollDist = 0
            isVisible = true
        }
        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy
        }
    }

    abstract fun hide()

    abstract fun show()
}