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

class TaskDetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private lateinit var tasksDao: TaskDao
    var task = MutableLiveData<TaskModel>()

    fun getTasks(application: Context,id : Int) {
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var myTask = tasksDao.getTasksById(id)
                task.postValue(myTask) // Verileri MutableLiveData'nin postValue() fonksiyonuyla güncelle
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
    fun finishTask(application : Context,task : TaskModel){
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        var changedTasks = TaskModel(task.id,task.title,task.description,"19/06/2023",true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tasksDao.changeStatus(changedTasks)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
}