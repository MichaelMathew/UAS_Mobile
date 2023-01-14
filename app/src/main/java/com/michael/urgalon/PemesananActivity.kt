package com.michael.urgalon

import android.R
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.adapter.PemesananGalonAdapter
import com.michael.urgalon.databinding.ActivityPemesananBinding
import com.michael.urgalon.entity.*
import com.michael.urgalon.fragment.HistoryFragment.Companion.HISTORY_REF
import com.michael.urgalon.fragment.VoucherFragment
import com.michael.urgalon.viewmodel.PemesananViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PemesananActivity : AppCompatActivity() {
    private lateinit var pemesananBinding: ActivityPemesananBinding
    private lateinit var galons: ArrayList<Galon>
    private lateinit var galonsAmount: ArrayList<Int>
    private var diskon: Int = 0
    private var diskonPoint: Int = 0
    private val viewModel: PemesananViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pemesananBinding = ActivityPemesananBinding.inflate(layoutInflater)
        setContentView(pemesananBinding.root)

        pemesananBinding.btnBack.setOnClickListener {
            finish()
        }

        var depot: Depot? = null
        var type: Int?
        diskon = 0
        diskonPoint = 0
        var currentPoint = 0

        intent.extras?.let {
            depot = it.getParcelable<Depot>("DEPOT")
            type = it.getInt("TYPE")
            currentPoint = it.getInt("POINT")
            viewModel.selectedVoucher = it.getParcelable("VOUCHER")
            diskon = viewModel.selectedVoucher?.diskon_voucher ?: 0
            diskonPoint = viewModel.selectedVoucher?.point_voucher ?: 0
            galons = it.getParcelableArrayList<Galon>("GALON") as ArrayList<Galon>
            galonsAmount = it.getIntegerArrayList("GALON-AMOUNT") as ArrayList<Int>
        }
        val galonAdapter = PemesananGalonAdapter(galons, galonsAmount)
        pemesananBinding.rvGalonPemesanan.adapter = galonAdapter
        pemesananBinding.rvGalonPemesanan.layoutManager = LinearLayoutManager(this)

        pemesananBinding.btnVoucher.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(pemesananBinding.containerVoucher.id, VoucherFragment.newInstance())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            pemesananBinding.containerVoucher.visibility = View.VISIBLE
        }

        val bayar = ArrayList<Metodebayar>()
        bayar.add(Metodebayar("COD"))
        bayar.add(Metodebayar("E-Wallet"))
        val bayarAdapter = ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                bayar
            )
        pemesananBinding.spinbayar.adapter = bayarAdapter
        pemesananBinding.spinbayar.setSelection(0,false)
        pemesananBinding.spinbayar.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.changeSpinner(p2)
                viewModel.selectedMetodebayar = "cod"
                checkMetodeBayar()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
        viewModel.indexSpinnerPemb.observe(this) {
            if (it == 0) {
                pemesananBinding.kolomewallet.visibility = View.GONE
            } else {
                pemesananBinding.kolomewallet.visibility = View.VISIBLE
            }
        }

        pemesananBinding.gopayclick.setOnClickListener {
            viewModel.selectedMetodebayar = "gopay"
            checkMetodeBayar()
        }
        pemesananBinding.ovoclick.setOnClickListener {
            viewModel.selectedMetodebayar = "ovo"
            checkMetodeBayar()
        }
        pemesananBinding.shopeepayclick.setOnClickListener {
            viewModel.selectedMetodebayar = "shopeepay"
            checkMetodeBayar()
        }

        pemesananBinding.jmldiskon.text = "Rp. ${diskon}"
        pemesananBinding.tvTotalharga.text = "Rp. ${checkTotal() - diskon}"
        pemesananBinding.totpemesanan.text = "Rp. ${checkTotal() - diskon}"

        pemesananBinding.totpemesanan.setOnClickListener {
            val newHistory = HistoryPemesanan()
            newHistory.depot = depot
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val current = formatter.format(time)
            newHistory.date = current
            newHistory.point = "+ " + checkTotal()/8000 + " Point"
            var pesanan = ""
            galons.forEachIndexed { index, galon ->
                pesanan += "Beli " + galon.nama_galon + " " + "x" + galonsAmount[index] + "\n"
            }
            newHistory.pesanan = pesanan
            newHistory.metodebayar = viewModel.selectedMetodebayar
            newHistory.diskon = diskon
            newHistory.totalharga = checkTotal()
            newHistory.lokasi = pemesananBinding.etTujuan.text.toString()
            saveData(newHistory, currentPoint, diskonPoint)
        }
    }

    override fun onBackPressed() {
        if (pemesananBinding.containerVoucher.visibility == View.VISIBLE) {
            pemesananBinding.containerVoucher.visibility = View.GONE
            diskon = viewModel.selectedVoucher?.diskon_voucher ?: 0
            diskonPoint = viewModel.selectedVoucher?.point_voucher ?: 0
            pemesananBinding.jmldiskon.text = "Rp. ${diskon}"
            pemesananBinding.tvTotalharga.text = "Rp. ${checkTotal() - diskon}"
            pemesananBinding.totpemesanan.text = "Rp. ${checkTotal() - diskon}"
        } else {
            super.onBackPressed()
        }
    }

    private fun checkMetodeBayar() {
        when (viewModel.selectedMetodebayar) {
            ("gopay") -> {
                pemesananBinding.gopaytext.setTypeface(null, Typeface.BOLD)
                pemesananBinding.ovotext.setTypeface(null, Typeface.NORMAL)
                pemesananBinding.shopeepaytext.setTypeface(null, Typeface.NORMAL)
            }
            ("ovo") -> {
                pemesananBinding.ovotext.setTypeface(null, Typeface.BOLD)
                pemesananBinding.gopaytext.setTypeface(null, Typeface.NORMAL)
                pemesananBinding.shopeepaytext.setTypeface(null, Typeface.NORMAL)
            }
            ("shopeepay") -> {
                pemesananBinding.shopeepaytext.setTypeface(null, Typeface.BOLD)
                pemesananBinding.gopaytext.setTypeface(null, Typeface.NORMAL)
                pemesananBinding.ovotext.setTypeface(null, Typeface.NORMAL)
            }
            ("cod") -> {
                pemesananBinding.gopaytext.setTypeface(null, Typeface.NORMAL)
                pemesananBinding.ovotext.setTypeface(null, Typeface.NORMAL)
                pemesananBinding.shopeepaytext.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    private fun checkTotal(): Int {
        var total = 0
        galons.forEachIndexed { index, galon ->
            total += galon.harga_galon * galonsAmount[index]
        }
        return total
    }

    private fun saveData(historyPemesanan: HistoryPemesanan, point: Int, diskonPoint: Int) {
        val database = Firebase.database("https://urgalon-125ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val reference = Firebase.auth.currentUser!!.uid.let {
            database.getReference("users").child(it).child(HISTORY_REF)
        }
        reference.push().setValue(historyPemesanan).addOnSuccessListener {
            database.getReference("users").child(Firebase.auth.currentUser!!.uid).child("point").setValue((point - diskonPoint) + (checkTotal()/8000))
            finish()
        }
    }
}