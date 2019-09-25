package br.edu.ifpr.stiehl.todolist.db.dao

import androidx.room.*
import br.edu.ifpr.stiehl.todolist.entities.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAll(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    fun findById(id: Int): Task?

    @Insert
    fun insert(task: Task): Long

    @Update
    fun update(task: Task)

    @Delete
    fun remove(task: Task)
}