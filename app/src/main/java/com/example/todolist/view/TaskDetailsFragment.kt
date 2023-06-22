package com.example.todolist.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.todolist.databinding.FragmentTaskDetailsBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.TaskDetailsViewModel

class TaskDetailsFragment : Fragment() {

    private lateinit var viewModel: TaskDetailsViewModel
    private lateinit var binding: FragmentTaskDetailsBinding
    private lateinit var task: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailsBinding.inflate(inflater,container,false)
        val view : View = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskDetailsViewModel::class.java)
        val id = arguments?.getInt("id")
        if (id != null){
            viewModel.getTasks(requireContext(),id)
        }
        observeLiveData()
        binding.btnClose.setOnClickListener {
            viewModel.finishTask(requireContext(),task)
        }
    }
    private fun observeLiveData() {
        viewModel.task.observe(viewLifecycleOwner, Observer {
            task = it
            binding.tvTitle.text = it.title
            binding.tvDescription.text = it.description
            binding.tvDate.text = it.date
            binding.priority.text = it.priority
        })
    }
}