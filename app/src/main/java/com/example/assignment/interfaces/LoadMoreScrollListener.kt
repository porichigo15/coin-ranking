package com.example.assignment.interfaces

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager

abstract class LoadMoreScrollListener(private var mLayoutManager: LinearLayoutManager): NestedScrollView.OnScrollChangeListener {
    private var previousTotalItemCount = 0
    private var isCompleteToLoadMore = false

    // Minimum pixel before load more
    private val visibleThresholdDistance = 10

    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        // Detect last position
        val view: View = v.getChildAt(v.childCount - 1)
        val distanceToEnd: Int = view.bottom - (v.height + v.scrollY)
        val totalItemsCount = mLayoutManager.itemCount

        if (totalItemsCount < previousTotalItemCount) {
            if (totalItemsCount == 0) {
                isCompleteToLoadMore = false
            }
        }

        // Detect item if not reach last index do nothing
        if (totalItemsCount > previousTotalItemCount) {
            previousTotalItemCount = totalItemsCount
            isCompleteToLoadMore = true
        }

        // Load data more if detect minimum end distance
        if (isCompleteToLoadMore && distanceToEnd <= visibleThresholdDistance) {
            onLoadMore()
        }
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore()
}