package com.michael.urgalon.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.urgalon.R
import com.michael.urgalon.databinding.DepotItemBinding
import com.michael.urgalon.databinding.GalonItemBinding
import com.michael.urgalon.entity.Depot
import com.michael.urgalon.entity.Galon

class HomeGalonAdapter(private val galons : ArrayList<Galon>) : RecyclerView.Adapter<HomeGalonAdapter.HomeGalonViewHolder>() {
    private lateinit var listener: HomeGalonListener
    inner class HomeGalonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: GalonItemBinding
        init {
            binding = GalonItemBinding.bind(itemView)
        }
        fun setData(galon: Galon) {
            binding.ivGalonItem.setImageURI(Uri.parse(galon.image_galon))
            binding.tvNameGalonItem.text = galon.nama_galon
            binding.tvPriceGalonItem.text = galon.harga_galon.toString()
            binding.btnPlus.setOnClickListener {
                binding.tvAmountGalonItem.text = (binding.tvAmountGalonItem.text.toString().toInt() + 1).toString()
                listener.onTambah(galon, binding.tvAmountGalonItem.text.toString().toInt())
            }
            binding.btnMinus.setOnClickListener {
                if (binding.tvAmountGalonItem.text != "0") {
                    binding.tvAmountGalonItem.text = (binding.tvAmountGalonItem.text.toString().toInt() - 1).toString()
                }
                listener.onKurang(galon, binding.tvAmountGalonItem.text.toString().toInt())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGalonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.galon_item,parent,false)
        return HomeGalonViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeGalonViewHolder, position: Int) {
        holder.setData(galons[position])
    }

    override fun getItemCount(): Int {
        return galons.size
    }

    interface HomeGalonListener {
        fun onTambah(galon: Galon, jumlah: Int)
        fun onKurang(galon: Galon, jumlah: Int)
    }

    fun setListener(homeGalonListener: HomeGalonListener){
        listener = homeGalonListener
    }
}