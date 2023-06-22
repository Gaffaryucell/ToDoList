package com.example.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class TaskModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,  // Görevin detayları.
    @ColumnInfo(name = "dueDate")
    val date: String,   // Görevin tamamlanması gereken tarih.
    @ColumnInfo(name = "isComplete")
    val isComplete: Boolean,
    @ColumnInfo(name = "priority")
    val priority: String
)