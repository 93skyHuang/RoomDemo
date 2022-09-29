package com.sky.roomdemo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

/**
 * Date：2022/9/23
 * Describe:  tableName 可选 默认类名  ColumnInfo 列相关信息
 */
@Entity(tableName = "study.db")
class Student {
    @PrimaryKey(autoGenerate = true)  //如果插入的主键没有设置自动增长则需要自己指定主键的值 建议都加上
    var id: Int = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = "名字${Random.nextInt(20)}"

    @ColumnInfo(name = "last_name")
    var lastName: String? = "姓${Random.nextInt(Int.MAX_VALUE)}"
    var age: Int = Random.nextInt(60)

    override fun toString(): String {
        return "Student{id=$id firstName=$firstName lastName=$lastName age=$age}"
    }
}