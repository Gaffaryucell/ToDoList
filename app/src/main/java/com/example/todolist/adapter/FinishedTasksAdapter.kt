package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.TaskModel

class FinishedTasksAdapter (
    private val itemList: MutableList<TaskModel>,
) : RecyclerView.Adapter<FinishedTasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        sortDataList()
        return when (viewType) {
            VIEW_TYPE_HIGH_PRIORITY -> {
                val view = inflater.inflate(R.layout.task_row, parent, false)
                ViewHolder(view)
            }
            VIEW_TYPE_MEDIUM_PRIORITY -> {
                val view = inflater.inflate(R.layout.task_row_2, parent, false)
                ViewHolder(view)
            }
            VIEW_TYPE_LOW_PRIORITY -> {
                val view = inflater.inflate(R.layout.task_row_3, parent, false)
                ViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.task_row_3, parent, false)
                ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleText: TextView = itemView.findViewById(R.id.title_textview)
            private val description: TextView = itemView.findViewById(R.id.description_textview)
            private val date: TextView = itemView.findViewById(R.id.date_textview)
            fun bind(item: TaskModel) {
                titleText.text = item.title
                description.text = item.description
                date.text = item.date
            }
    }
    private fun sortDataList() {
        itemList.sortBy { task -> getPriorityValue(task.priority) }
    }

    private fun getPriorityValue(priority: String): Int {
        return when (priority) {
            "High" -> 1
            "Mid" -> 2
            "Low" -> 3
            else -> 3
        }
    }

    private val VIEW_TYPE_HIGH_PRIORITY = 1
    private val VIEW_TYPE_MEDIUM_PRIORITY = 2
    private val VIEW_TYPE_LOW_PRIORITY = 3

    override fun getItemViewType(position: Int): Int {
        val priority = itemList[position].priority
        return when (priority) {
            "High" -> VIEW_TYPE_HIGH_PRIORITY
            "Mid" -> VIEW_TYPE_MEDIUM_PRIORITY
            "Low" -> VIEW_TYPE_LOW_PRIORITY
            else -> VIEW_TYPE_HIGH_PRIORITY
        }
    }
}
