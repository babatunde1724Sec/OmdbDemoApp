package com.example.omdbdemoapp.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    private var mPreviousTotal = 0
    private var mLoading = true

    override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recycler, dx, dy)
        initScrollListener(recycler)
    }

    private fun initScrollListener(recycler: RecyclerView) {
        val visibleItemCount: Int = recycler.childCount
        val totalItemCount: Int = recycler.layoutManager!!.itemCount
        val firstVisibleItem =
            (recycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }
        val visibleThreshold = 3
        if (!mLoading && totalItemCount - visibleItemCount
            <= firstVisibleItem + visibleThreshold
        ) {
            onLoadMore()
            mLoading = true
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val canScrollDownMore = recyclerView.canScrollVertically(1);
            if (!canScrollDownMore) {
                onScrolled(recyclerView, 0, 1);
            }
        }
    }

    abstract fun onLoadMore()
}