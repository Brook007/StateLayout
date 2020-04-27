package com.brook.app.android.supportlibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.brook.app.android.view.StateLayout
import kotlinx.android.synthetic.main.activity_main.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val recyclerView = RecyclerView(this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = RecyclerViewTestAdapter()

        layout.setSuccessView(recyclerView)
    }

    fun click(view: View) {
        var button = view as Button
        when (button.text.toString()) {
            "成功" -> {
                layout.setStateDelayed(StateLayout.SUCCESS, 2000)
            }
            "加载中" -> {
                layout.setStateDelayed(StateLayout.LOADING, 2000)
            }
            "无网络" -> {
                layout.setState(StateLayout.NO_NETWORK)
            }
            "无数据" -> {
                layout.setState(StateLayout.EMPTY)
            }
            "无服务" -> {
                layout.setState(StateLayout.ERROR)
            }

        }
    }
}
