package com.example.todolist.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.TaskModel

class SwipeToDeleteCallback(private val adapter: TaskAdapter,private val list : MutableList<TaskModel>) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView

        if (!isCurrentlyActive && dX == 0f) { // kullanıcı kaydırmayı bıraktığında
            // Arka planı ve ikonu çizme
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, false)
            return
        }

        // Kaydırma yönüne göre ikon ve arka plan rengi belirleniyor
        val icon: Drawable?
        val background: ColorDrawable?

        if (dX > 0) { // sağa kaydırma
            icon = ContextCompat.getDrawable(
                recyclerView.context,
                R.drawable.trash
            ) // çöp kutusu ikonu
            background = ColorDrawable(Color.RED) // kırmızı arka plan

            // İkonun ve arka planın çizilmesi
            val iconSize = 30.dpToPx(recyclerView.context) // 50dp'yi piksel cinsinden al
            val iconMargin = (itemView.height - iconSize) / 2
            val iconLeft = itemView.left + iconMargin
            val iconRight = itemView.left + iconMargin + iconSize

            icon?.setBounds(iconLeft, itemView.top + iconMargin, iconRight, itemView.bottom - iconMargin)
            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)

        } else { // sola kaydırma
            icon = ContextCompat.getDrawable(
                recyclerView.context,
                R.drawable.checkmark
            ) // onay işareti ikonu
            background = ColorDrawable(Color.GREEN) // yeşil arka plan

            // İkonun ve arka planın çizilmesi
            val iconSize = 30.dpToPx(recyclerView.context) // 50dp'yi piksel cinsinden al
            val iconMargin = (itemView.height - iconSize) / 2
            val iconLeft = itemView.right - iconMargin - iconSize
            val iconRight = itemView.right - iconMargin

            icon?.setBounds(iconLeft, itemView.top + iconMargin, iconRight, itemView.bottom - iconMargin)
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        }

        background.draw(c)
        icon?.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
    private fun Int.dpToPx(context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            println("sol"+position)
            adapter.finishedTask(position)
        } else if (direction == ItemTouchHelper.RIGHT) {
            println("sağ"+position)
            adapter.deleteItem(position)
        }
    }
}
