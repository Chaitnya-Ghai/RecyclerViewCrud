package cg.tutorials.recyclerviewcrud

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoInterface {

    @Insert
    fun insertTodo(info: Info)

    @Query("Select * from Info")
    fun getList():List<Info>

    @Delete
    fun deleteTodo(info: Info)

    @Update
    fun updateTodo(info: Info)
}