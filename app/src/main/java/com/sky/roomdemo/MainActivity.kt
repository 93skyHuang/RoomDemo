package com.sky.roomdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val data = arrayListOf(UiData("普通用法", {
        startActivity(Intent(this, NormalRoomPage::class.java))
    }), UiData("Page Source", {
        startActivity(Intent(this, PagingSourceActivity::class.java))
    }))

    private val mAdapter by lazy { Adapter(data) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.rv).run {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}

data class UiData(val name: String, val click: () -> Unit)


class Adapter(val data: ArrayList<UiData>) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room_demo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = data[position].name
        holder.view.setOnClickListener {
            data[position].click.invoke()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}