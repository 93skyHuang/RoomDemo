package com.sky.roomdemo

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sky.roomdemo.entity.Student
import kotlin.random.Random

/**
 * Date：2022/9/24
 * Describe:
 */
class NormalRoomPage : AppCompatActivity() {
    private val database by lazy { MyRoomDatabase.studentDao }
    private lateinit var mAdapter: RoomNormalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_room)
        mAdapter = RoomNormalAdapter()
        findViewById<TextView>(R.id.tv_add).setOnClickListener {
            Log.i("add", "addd--->")
            database.insertAll(
                Student().apply {
                    firstName = "云长"
                    lastName = "关羽"
                    age = Random.nextInt(50)
                },
                Student().apply { age = Random.nextInt(50) },
                Student().apply { age = Random.nextInt(8) },

                Student(),
//                Student().apply {
//                    firstName = "翼德"
//                    lastName = "张飞"
//                    age = 33
//                },
            )

        }
        findViewById<TextView>(R.id.tv_query1).setOnClickListener {
            Log.i("findByName", "${database.findByName("云长", "")}")
            Log.i(
                "loadAllByAge",
                "${database.loadAllByAge(intArrayOf(1, 2, 10, 22, 21, 33, 34)).size}"
            )
        }
        findViewById<TextView>(R.id.tv_query).setOnClickListener {
            mAdapter.data = database.getAll() as MutableList<Student>
            mAdapter.notifyDataSetChanged()
        }
        findViewById<TextView>(R.id.tv_update).setOnClickListener {
            database.update(database.getAll()[0].apply {
                firstName = "名字更新${Random.nextInt(20)}"
            })
            mAdapter.data = database.getAll() as MutableList<Student>
            mAdapter.notifyDataSetChanged()
        }
        findViewById<TextView>(R.id.tv_delete).setOnClickListener {
            database.deleteByFirstName("云长")

        }
        findViewById<RecyclerView>(R.id.rv).run {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@NormalRoomPage)
        }
        findViewById<EditText>(R.id.et_search).addTextChangedListener {
            val searchStr = it?.toString()
            if (!TextUtils.isEmpty(searchStr)) {
                mAdapter.data = database.search(searchStr!!) as MutableList<Student>
                mAdapter.notifyDataSetChanged()
            }
        }
        mAdapter.clickItem = {
            database.delete(it)
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
        }
        initData()
    }

    private fun initData() {
        mAdapter.data = database.getAll() as MutableList<Student>
    }
}


class RoomNormalAdapter() :
    RecyclerView.Adapter<RoomNormalAdapter.ViewHolder>() {
    var data = mutableListOf<Student>()
    var clickItem: (data: Student) -> Unit = {}

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room_demo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = data[position]
        holder.tv.text = "age=${s.age}${s.firstName}${s.lastName}"
        holder.view.setOnLongClickListener {
            data.removeAt(position)
            notifyItemRemoved(position)
            clickItem.invoke(s)
            false
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}