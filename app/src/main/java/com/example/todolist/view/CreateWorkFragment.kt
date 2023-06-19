package com.example.todolist.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.todolist.R
import com.example.todolist.dao.TaskDao
import com.example.todolist.database.TaskDatabaseSingleton
import com.example.todolist.databinding.FragmentCreateWorkBinding
import com.example.todolist.databinding.FragmentListBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.CreateTaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateWorkFragment : Fragment() {

    private lateinit var viewModel: CreateTaskViewModel

    private lateinit var binding: FragmentCreateWorkBinding
    private var date: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateWorkBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)
        viewModel.selectDate(requireContext())
        observeLiveData()
        binding.buttonAdd.setOnClickListener {
            val title = binding.editTextTask.text.toString()
            val description = binding.editTextTask.text.toString()
            viewModel.createTask(requireActivity(),title,description,date.toString())
        }
    }
    private fun observeLiveData(){
        viewModel.selectedDate.observe(viewLifecycleOwner, Observer{
            date = it
        })
    }
}