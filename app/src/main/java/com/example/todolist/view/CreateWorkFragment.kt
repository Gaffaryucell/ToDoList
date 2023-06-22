package com.example.todolist.view

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.FinishedTasksAdapter
import com.example.todolist.databinding.FragmentCreateWorkBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.CreateTaskViewModel

class CreateWorkFragment : Fragment() {

    private lateinit var viewModel: CreateTaskViewModel
    private lateinit var adapter: FinishedTasksAdapter
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
        viewModel.getTasks(requireContext())
        observeLiveData()
        binding.buttonAdd.setOnClickListener {
            val priority = binding.spinner.selectedItem.toString()
            val title = binding.etTaskTitle.text.toString()
            val description = binding.etTaskDescription.text.toString()
            viewModel.createTask(requireActivity(),title,description,date.toString(), priority)
        }
        binding.calendarImageView.setOnClickListener{
            viewModel.selectDate(requireContext())
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun observeLiveData(){
        viewModel.selectedDate.observe(viewLifecycleOwner, Observer{
            date = it
        })
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            val newlist = ArrayList<TaskModel>()
            it.forEach {
                if (it.date.equals(date)) {
                    newlist.add(it)
                }
            }
            adapter = FinishedTasksAdapter(newlist)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }
}