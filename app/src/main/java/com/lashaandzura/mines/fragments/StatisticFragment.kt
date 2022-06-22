package com.lashaandzura.mines.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lashaandzura.mines.App
import com.lashaandzura.mines.R
import com.lashaandzura.mines.adapters.StatisticRecyclerAdapter

class StatisticFragment: Fragment(R.layout.fragment_statistics) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var resultText: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        resultText = view.findViewById(R.id.resultTextView)

        val loses = App.instance.db.getStatisticDao().getSumLose()
        val wins = App.instance.db.getStatisticDao().getSumWin()
        val all = loses + wins
        resultText.text = "Full Results: $wins / $loses out of $all matches"
        val statistics = App.instance.db.getStatisticDao().getAllStatistic()
        recyclerView.adapter = StatisticRecyclerAdapter(statistics)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}