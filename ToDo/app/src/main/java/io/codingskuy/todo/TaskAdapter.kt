package io.codingskuy.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.codingskuy.todo.domain.model.Task
import io.codingskuy.todo.databinding.ItemsTaskBinding

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onCheckedChanged: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val binding = ItemsTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = tasks[position]
        holder.binding.tvTaskTitle.text = task.title
        holder.binding.cbTaskDone.isChecked = task.done
        holder.binding.cbTaskDone.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChanged(task, isChecked)
        }
    }

    override fun getItemCount(): Int = tasks.size

    class TaskViewHolder(val binding: ItemsTaskBinding) : RecyclerView.ViewHolder(binding.root)
}

