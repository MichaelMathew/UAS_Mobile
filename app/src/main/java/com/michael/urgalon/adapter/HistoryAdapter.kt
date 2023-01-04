package com.michael.urgalon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.michael.urgalon.R
import com.michael.urgalon.databinding.HistoryItemBinding
import com.michael.urgalon.entity.HistoryPemesanan

class HistoryAdapter(val historys: ArrayList<HistoryPemesanan>) : Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : ViewHolder(itemView) {
        private lateinit var binding: HistoryItemBinding
        init {
            binding = HistoryItemBinding.bind(itemView)
        }
        fun setHistoryData(history: HistoryPemesanan){
            binding.tvDepotname.text = history.depot
            binding.tvDatehistory.text = history.date.toString()
            binding.tvPemesanan.text = history.pesanan
            binding.tvTotal.text = "Total"
            binding.tvPrice.text = history.totalharga.toString()
            binding.tvPluspoint.text = history.point.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item,parent,false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.setHistoryData(historys[position])
    }

    override fun getItemCount(): Int {
        return historys.size
    }
}