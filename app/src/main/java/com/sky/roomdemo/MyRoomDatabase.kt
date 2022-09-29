package com.sky.roomdemo

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sky.roomdemo.dao.StudentDao
import com.sky.roomdemo.entity.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Date：2022/9/23
 * Describe:
 */
@Database(entities = [Student::class], version = 1)
abstract class MyRoomDatabase:RoomDatabase() {
    companion object {
        private val instance by lazy {
            Room.databaseBuilder(App.mApp, MyRoomDatabase::class.java, "room_demo.db").apply {
                allowMainThreadQueries()
                enableMultiInstanceInvalidation()
                fallbackToDestructiveMigration()
                setQueryExecutor { GlobalScope.launch { it.run() } }
            }.build()
        }
        val studentDao get() = instance.studentDao()
    }
    abstract fun studentDao(): StudentDao
}