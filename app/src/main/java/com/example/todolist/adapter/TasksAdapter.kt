package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.model.TaskModel

class TaskAdapter(
    private val onItemClick: (TaskModel) -> Unit,
    private val onItemSwiped: (TaskModel) -> Unit,
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    var itemList = mutableListOf<TaskModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)
        fun bind(item: TaskModel) {
            textView.text = item.title
        }
    }
    fun deleteItem(position: Int) {
        try {
            onItemClick(itemList[position])
            notifyItemRemoved(position)
            itemList.removeAt(position)
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }

    fun finishedTask(position: Int) {
        try {
            onItemSwiped(itemList[position])
            notifyItemRemoved(position)
            itemList.removeAt(position)
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
    fun refreshList(newList : MutableList<TaskModel>){
        itemList = newList
    }
}

