package io.codingskuy.todo.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.codingskuy.todo.TaskAdapter
import io.codingskuy.todo.domain.model.Task
import io.codingskuy.todo.databinding.ActivityRecyclerBinding

class RecyclerActivity : androidx.activity.ComponentActivity() {

    private lateinit var binding: ActivityRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val tasks = mutableListOf(
            Task(1, "Belajar RecyclerView"),
            Task(2, "Paham Adapter & ViewHolder"),
            Task(3, "Bandingkan sama LazyColumn"),
            Task(4, "Push ke GitHub"),
            Task(5, "Jago dua-duanya: XML & Compose"),
        )

        binding.rvTasks.layoutManager = LinearLayoutManager(this)

        binding.rvTasks.adapter = TaskAdapter(tasks) { task, isChecked ->
            val index = tasks.indexOfFirst { it.id == task.id }
            tasks[index] = tasks[index].copy(done = isChecked)
        }


        }
}