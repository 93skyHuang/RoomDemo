package com.sky.roomdemo.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.sky.roomdemo.entity.Student

/**
 * Date：2022/9/23
 * Describe:
 */
@Dao
interface StudentDao {
    @Query("SELECT * FROM `study.db`")
    fun getAll(): List<Student>

    @Query("SELECT * FROM `study.db` WHERE age IN (:userIds)")
    fun loadAllByAge(userIds: IntArray): List<Student>

    //精确查找
    @Query("SELECT * FROM `study.db` WHERE first_name LIKE :first OR last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Student

    @Query("SELECT * FROM `study.db` WHERE first_name like '%' || :str || '%' OR last_name like '%' || :str || '%'")
    fun search(str: String): List<Student>

    //删除
    @Query("DELETE FROM `study.db` WHERE first_name like :firstName")
    fun deleteByFirstName(firstName: String)

    //如果插入的主键没有设置自动增长则需要自己指定主键的值
    @Insert
    fun insertAll(vararg users: Student)

    @Insert
    fun add(student: Student)

    @Delete
    fun delete(user: Student)

    @Update
    fun update(student: Student)

//    @Query("SELECT * FROM `study.db` ")
//    fun pagingSource(): PagingSource<Int, Student>

}