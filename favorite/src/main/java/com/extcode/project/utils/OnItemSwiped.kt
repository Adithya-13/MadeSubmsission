package com.extcode.project.utils

import androidx.recyclerview.widget.RecyclerView

interface OnItemSwiped {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder)
}