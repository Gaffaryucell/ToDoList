package com.example.todolist.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.adapter.SwipeToDeleteCallback
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.FragmentListBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var mySelectedDate: String
    private lateinit var toDay : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        adapter = TaskAdapter(
            { item ->
                viewModel.deleteTask(requireContext(), item)
            },
            { item ->
                viewModel.finishTask(requireContext(), item)
            }
        )
        viewModel.getTasks(requireContext())
        viewModel.getDate()
        observeLiveData()
        // TaskAdapter'ı oluşturma ve RecyclerView'a bağlama
        binding.taskrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.button.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToCreateWorkFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.button.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                val action = ListFragmentDirections.actionListFragmentToFinishedTasksFragment()
                Navigation.findNavController(p0!!).navigate(action)
                return true
            }
        })
        binding.dateImageView.setOnClickListener{
            viewModel.selectDate(requireContext())
        }
        binding.allImageView.setOnClickListener{
            mySelectedDate = "all"
            viewModel.getTasks(requireContext())
        }
    }
    private fun observeLiveData() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            var newlist = ArrayList<TaskModel>()
            when(mySelectedDate){
                toDay->{ binding.textView.text = "TODAY" }
                else->{ binding.textView.text = mySelectedDate }
            }
            if (!mySelectedDate.equals("all")){
                println("not all ")
                println("selected : "+mySelectedDate)
                it.forEach{
                    println(it.date)
                    if (it.date.equals(mySelectedDate)){
                        newlist.add(it)
                    }
                }
            }else{
                println("in all ")
                newlist = it as ArrayList<TaskModel>
            }
            adapter.itemList = newlist

            binding.taskrecyclerview.adapter = adapter
            adapter.notifyDataSetChanged()
            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter,newlist))
            itemTouchHelper.attachToRecyclerView(binding.taskrecyclerview)
        })
        viewModel.selectedDate.observe(viewLifecycleOwner, Observer {
            mySelectedDate = it
        })
        viewModel.toDay.observe(viewLifecycleOwner, Observer {
            toDay = it
        })
    }
}