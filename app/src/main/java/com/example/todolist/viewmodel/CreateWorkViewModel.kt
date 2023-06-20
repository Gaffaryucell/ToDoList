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

class CreateTaskViewModel : ViewModel() {

    // Veritabanı nesnesini oluştur
    private lateinit var tasksDao: TaskDao
    var selectedDate = MutableLiveData<String>()
    var tasks = MutableLiveData<List<TaskModel>>()

    init {
        getDate()
    }
    fun createTask(application : Context,title : String,description : String,date : String){
        val db = TaskDatabaseSingleton.getDatabase(application)
        tasksDao = db.studentDao()
        // Task ekle
        CoroutineScope(Dispatchers.IO).launch {
            val task = TaskModel(0, title,description,date,false)
            tasksDao.insertTask(task)
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
        }, year, month, day)
        dpd.show()
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
    fun getDate() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedToday = today.format(formatter)
        selectedDate.value = formattedToday
    }
}