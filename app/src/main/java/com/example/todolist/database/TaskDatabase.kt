package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.dao.TaskDao
import com.example.todolist.model.TaskModel

@Database(entities = [TaskModel::class], version = 5)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun studentDao(): TaskDao
}

object TaskDatabaseSingleton {
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "data_base_task_aq"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }