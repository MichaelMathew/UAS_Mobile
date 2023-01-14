package com.michael.urgalon

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.michael.urgalon.databinding.ActivityDetailHistoryBinding
import com.michael.urgalon.entity.HistoryPemesanan
import java.io.ByteArrayOutputStream

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
            loadImages(history?.depot?.image_depot ?: "aida.jpeg")
        }

    }

    private fun loadImages(name: String) {
        val storage = Firebase.storage("gs://tubesmobile-13f1f.appspot.com")
        val storageRef = storage.reference
        val imagesRef = storageRef.child("Depot/$name")

        Glide.with(this).load(imagesRef).into(binding.ivDepotDetailHistory)
    }
}