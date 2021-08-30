package com.example.moviecataloguev2.utils.shimmerLoading

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

object ShimmerLoading {
    fun ShimmerFrameLayout.start() {
        this.visibility = View.VISIBLE
        this.startShimmer()
    }

    fun ShimmerFrameLayout.stop() {
        this.visibility = View.GONE
        this.stopShimmer()
    }
}