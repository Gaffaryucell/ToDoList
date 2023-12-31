package com.example.todolist.viewmodel

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.dao.TaskDao
import com.example.todolist.database.TaskDatabaseSingleton
import com.example.todolist.model.TaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class ListViewModel : ViewModel() {

    private lateinit var tasksDao: TaskDao
    var tasks = MutableLiveData<List<TaskModel>>()
    var selectedDate = MutableLiveData<String>()
    var toDay = MutableLiveData<String>()

    init {
        getDate()
    }
    fun getTasks(application: Context) {
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mylist = tasksDao.getAllFinishedTasks(false) as ArrayList<TaskModel>
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
    fun deleteAll(application : Context){
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tasksDao.deleteAllTask()
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
    fun finishTask(application : Context,task : TaskModel){
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        val changedTasks = TaskModel(task.id,task.title,task.description,task.date,true,task.priority)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tasksDao.changeStatus(changedTasks)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
    fun selectDate(context : Context) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context, { view, mYear, mMonth, mDay ->
            val formattedMonth = String.format("%02d", mMonth+1) // Ayı iki haneli olacak şekilde biçimlendirme
            val formattedDay = String.format("%02d", mDay) // Günü iki haneli olacak şekilde biçimlendirme
            selectedDate.value = "$formattedDay/$formattedMonth/$mYear"
            Toast.makeText(context, "Seçilen tarih: $selectedDate", Toast.LENGTH_LONG).show()
            getTasks(context)
        }, year, month, day)
        dpd.show()
    }
    fun getDate() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedToday = today.format(formatter)
        selectedDate.value = formattedToday
        toDay.value = formattedToday
    }
}