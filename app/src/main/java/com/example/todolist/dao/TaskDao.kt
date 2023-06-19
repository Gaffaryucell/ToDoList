package com.example.todolist.dao

import androidx.room.*
import com.example.todolist.model.TaskModel

@Dao
interface TaskDao {
    @Insert
    fun insertTask(student: TaskModel)

    @Delete
    fun deleteTask(student: TaskModel)

    @Update
    fun changeStatus(student: TaskModel)

    @Query("DELETE FROM tasks")
    fun deleteAllTask()

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<TaskModel>

    @Query("SELECT * FROM tasks WHERE isComplete = :complate")
    fun getAllFinishedTasks(complate : Boolean): List<TaskModel>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTasksById(id: Int): TaskModel
}