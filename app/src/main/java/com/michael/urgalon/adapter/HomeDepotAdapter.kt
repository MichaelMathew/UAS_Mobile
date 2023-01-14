package com.michael.urgalon.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.michael.urgalon.R
import com.michael.urgalon.databinding.DepotItemBinding
import com.michael.urgalon.databinding.HistoryItemBinding
import com.michael.urgalon.entity.Depot

class HomeDepotAdapter(private val depots : ArrayList<Depot>) : Adapter<HomeDepotAdapter.HomeDepotViewHolder>() {
//    private var selectedIndex: Int? = null
    private lateinit var listener: HomeDepotListener
    inner class HomeDepotViewHolder(itemView: View) : ViewHolder(itemView) {
        private var binding: DepotItemBinding
        private val storage = Firebase.storage("gs://tubesmobile-13f1f.appspot.com")
        private val storageRef = storage.reference
        init {
            binding = DepotItemBinding.bind(itemView)
        }
        fun setData(depot: Depot) {
            binding.tvDepotItem.text = depot.nama_depot
            val imagesRef = storageRef.child("Depot/${depot.image_depot}")
            Glide.with(itemView).load(imagesRef).into(binding.ivDepotItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDepotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.depot_item,parent,false)
        return HomeDepotViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeDepotViewHolder, position: Int) {
        holder.setData(depots[position])
        holder.itemView.setOnClickListener {
//            selectedIndex = holder.adapterPosition
            listener.onClick(depots[position])
        }
    }

    override fun getItemCount(): Int {
        return depots.size
    }

    interface HomeDepotListener {
        fun onClick(depot: Depot)
    }

    fun setListener(homeDepotListener: HomeDepotListener) {
        listener = homeDepotListener
    }
}