package com.michael.urgalon.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.urgalon.R
import com.michael.urgalon.databinding.PemesananGalonItemBinding
import com.michael.urgalon.entity.Galon

class PemesananGalonAdapter(private val galons : ArrayList<Galon>, private val amountGalon: ArrayList<Int>) : RecyclerView.Adapter<PemesananGalonAdapter.PemesananGalonViewHolder>() {
    var totalHarga: Int = 0
    inner class PemesananGalonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: PemesananGalonItemBinding
        init {
            binding = PemesananGalonItemBinding.bind(itemView)
        }
        fun setData(galon: Galon, amount: Int) {
            binding.merkgalon.text = galon.nama_galon
            binding.jmlisi.text = amount.toString()
            binding.jmlharga.text = (amount * galon.harga_galon).toString()
            totalHarga += amount * galon.harga_galon
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemesananGalonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pemesanan_galon_item,parent,false)
        return PemesananGalonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PemesananGalonViewHolder, position: Int) {
        holder.setData(galons[position], amountGalon[position])
    }

    override fun getItemCount(): Int {
        return galons.size
    }
}