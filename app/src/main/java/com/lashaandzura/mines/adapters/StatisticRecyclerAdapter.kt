package com.lashaandzura.mines.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lashaandzura.mines.R
import com.lashaandzura.mines.room.Statistic

class StatisticRecyclerAdapter(private val resources: List<Statistic>): RecyclerView.Adapter<StatisticRecyclerAdapter.ResourceViewHolder>() {
    class ResourceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val resultView: TextView = itemView.findViewById(R.id.resultText)
        private val dateView: TextView = itemView.findViewById(R.id.dateText)

        fun onBind(statistic: Statistic){
            val color = if (statistic.result) Color.GREEN else Color.RED
            resultView.text = if (statistic.result) "მოგება" else "წაგება"
            resultView.setTextColor(color)
            dateView.text = statistic.date.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_resource, parent, false)
        return ResourceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.onBind(resources[position])
    }

    override fun getItemCount() = resources.size
}