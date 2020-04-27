package com.brook.app.android.supportlibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.brook.app.android.view.StateLayout
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
                layout.setState(StateLayout.SUCCESS)
            }
            "加载中" -> {
                layout.setState(StateLayout.LOADING)
            }
            "无网络" -> {
                layout.setState(StateLayout.NO_NETWORK)
            }
            "无数据" -> {
                layout.setState(StateLayout.NO_DATA)
            }
            "无服务" -> {
                layout.setState(StateLayout.ERROR)
            }

        }
    }
}
