package ru.androidlab.todoproj.adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.databinding.ItemHolderBinding

class Adapter(private val listener: IMovieClick) :
    ListAdapter<TaskEntity, BaseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class Holder(private val binding: ItemHolderBinding) : BaseViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            ItemHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun bind(item: TaskEntity) {
            binding.apply {
                textTitle.text = item.title
                description.text = item.description
                if (item.done) {
                    done.setImageResource(R.drawable.checked)
                    colorImage.setBackgroundColor(Color.parseColor("#FF5C00"))
                    textTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textTitle.paintFlags = Paint.ANTI_ALIAS_FLAG
                    done.setImageResource(R.drawable.unchecked)
                    when (item.priority) {
                        "Low" -> {
                            colorImage.setBackgroundColor(Color.parseColor("#55C000"))
                        }
                        "Medium" -> {
                            colorImage.setBackgroundColor(Color.parseColor("#FFE600"))
                        }
                        else -> {
                            colorImage.setBackgroundColor(Color.parseColor("#F30000"))
                        }
                    }
                }
                root.setOnClickListener { listener.openTask(item) }
                done.setOnClickListener { listener.complete(item) }
            }
        }
    }

    interface IMovieClick {
        fun openTask(task: TaskEntity)
        fun complete(task: TaskEntity)
    }

    object DiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return when {
                oldItem is TaskEntity && newItem is TaskEntity -> {
                    oldItem.title == newItem.title
                }
                else -> true
            }
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }
}
