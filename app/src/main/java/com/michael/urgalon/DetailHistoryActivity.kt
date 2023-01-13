package com.michael.urgalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.michael.urgalon.databinding.ActivityDetailHistoryBinding
import com.michael.urgalon.entity.HistoryPemesanan

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        if (intent.hasExtra("HISTORY")) {
            val history = intent.getParcelableExtra<HistoryPemesanan>("HISTORY")

            binding.tvDepotName.text = history?.depot?.nama_depot
            binding.tvDepotAddress.text = history?.depot?.alamat_depot
            binding.etTujuan.text = history?.lokasi
            binding.tvPesanan.text = history?.pesanan
            binding.tvMetodeBayar.text = history?.metodebayar
            binding.jmldiskon.text = history?.diskon.toString()
            binding.tvTotalharga.text = history?.totalharga.toString()
        }
    }
}