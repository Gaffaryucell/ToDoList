package com.example.todolist.view

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.adapter.FinishedTasksAdapter
import com.example.todolist.adapter.SwipeToDeleteCallback
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.FragmentFinishedTasksBinding
import com.example.todolist.databinding.FragmentListBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.FinishedTasksViewModel
import java.time.LocalDate
import java.util.Calendar

class FinishedTasksFragment : Fragment() {

    private lateinit var viewModel: FinishedTasksViewModel
    private lateinit var binding: FragmentFinishedTasksBinding
    private lateinit var adapter: FinishedTasksAdapter
    private var selectedDate : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishedTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FinishedTasksViewModel::class.java)

        viewModel.getTasks(requireContext())
        observeLiveData()
        // TaskAdapter'ı oluşturma ve RecyclerView'a bağlama
        binding.taskrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.button.setOnClickListener {

        }
    }
    private fun observeLiveData() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            adapter = FinishedTasksAdapter(it as ArrayList<TaskModel>)
            binding.taskrecyclerview.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }
}