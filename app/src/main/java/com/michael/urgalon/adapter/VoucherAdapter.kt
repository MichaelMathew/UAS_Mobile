package com.michael.urgalon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.urgalon.R
import com.michael.urgalon.databinding.VoucherItemBinding
import com.michael.urgalon.entity.Voucher

class VoucherAdapter(private val vouchers: ArrayList<Voucher>) : RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>() {

    private lateinit var listener: VoucherListener

    inner class VoucherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: VoucherItemBinding
        init {
            binding = VoucherItemBinding.bind(itemView)
        }
        fun setVoucherData(voucher: Voucher){
            binding.tvDate.text = voucher.tanggal_voucher.toString()
            binding.tvDiskonrp.text = voucher.diskon_voucher.toString()
            binding.tvPoint.text = voucher.point_voucher.toString()
            binding.btnTukar.setOnClickListener { listener.onClick(voucher) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.voucher_item,parent,false)
        return VoucherViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        holder.setVoucherData(vouchers[position])
//        holder.itemView.setOnClickListener { listener.onClick(vouchers[position]) }
    }

    override fun getItemCount(): Int {
        return vouchers.size
    }

    fun setListener(voucherListener: VoucherListener) {
        listener = voucherListener
    }

    interface VoucherListener {
        fun onClick(voucher: Voucher)
    }
}