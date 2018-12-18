package com.brook.app.android.supportlibrary.statelayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.brook.app.android.supportlibrary.views.StateLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = RecyclerView(this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = RecyclerViewTestAdapter()

        layout.setSuccessView(recyclerView)
    }

    fun click(view: View) {
        var button = view as Button
        when (button.text.toString()) {
            "成功" -> {
                layout.state = StateLayout.SUCCESS
            }
            "加载中" -> {
                layout.state = StateLayout.LOADING
            }
            "无网络" -> {
                layout.state = StateLayout.NO_NETWORK
            }
            "无数据" -> {
                layout.state = StateLayout.NO_DATA
            }
            "无服务" -> {
                layout.state = StateLayout.ERROR
            }

        }
    }
}
