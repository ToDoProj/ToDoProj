package ru.androidlab.todoproj.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.androidlab.todoproj.data.TaskEntity

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: TaskEntity)
}
