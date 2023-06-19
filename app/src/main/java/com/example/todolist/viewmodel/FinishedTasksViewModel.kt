package com.example.todolist.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.dao.TaskDao
import com.example.todolist.database.TaskDatabaseSingleton
import com.example.todolist.model.TaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinishedTasksViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private lateinit var tasksDao: TaskDao
    var tasks = MutableLiveData<List<TaskModel>>()

    fun getTasks(application: Context) {
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mylist = tasksDao.getAllFinishedTasks(true) as ArrayList<TaskModel>
                tasks.postValue(mylist) // Verileri MutableLiveData'nin postValue() fonksiyonuyla güncelle
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
    fun deleteTask(application : Context,task : TaskModel){
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tasksDao.deleteTask(task)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
}