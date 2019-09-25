package br.edu.ifpr.stiehl.todolist.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import br.edu.ifpr.stiehl.aula7_sqlite.ui.TaskAdapterListener
import br.edu.ifpr.stiehl.todolist.R
import br.edu.ifpr.stiehl.todolist.db.AppDatabase
import br.edu.ifpr.stiehl.todolist.db.dao.TaskDao
import br.edu.ifpr.stiehl.todolist.entities.Task
import br.edu.ifpr.stiehl.todolist.ui.TaskAdapter

import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.content_todo.*

class TodoActivity : AppCompatActivity(), TaskAdapterListener {

    lateinit var taskDao: TaskDao
    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        setSupportActionBar(toolbar)

        val db =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "todo.db"
            )
                .allowMainThreadQueries()
                .build()
        taskDao = db.taskDao()

        loadRecyclerView()

        btAdd.setOnClickListener {
            val position = adapter.createTask()
            listTask.scrollToPosition(position)
        }
    }

    private fun loadRecyclerView() {
        val tasks = taskDao.getAll()
        adapter = TaskAdapter(tasks.toMutableList(), this)
        listTask.adapter = adapter
        listTask.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }

    override fun taskSaved(task: Task) {
        if (task.id == 0L) {
            task.id = taskDao.insert(task)
        } else {
            taskDao.update(task)
        }
    }

    override fun taskRemoved(task: Task) {
        taskDao.remove(task)
    }

}