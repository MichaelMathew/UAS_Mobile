package com.michael.urgalon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.urgalon.adapter.HistoryAdapter
import com.michael.urgalon.databinding.FragmentHistoryBinding
import com.michael.urgalon.entity.HistoryPemesanan
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [History.newInstance] factory method to
 * create an instance of this fragment.
 */
class History : Fragment() {
    private lateinit var historybinding: FragmentHistoryBinding
    private lateinit var historys: ArrayList<HistoryPemesanan>
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var jumlah: String
    private lateinit var depot: String
    private lateinit var merk: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historybinding = FragmentHistoryBinding.inflate(inflater,container,false)
        historys = ArrayList()
        if (arguments != null) {
            jumlah = arguments?.getString("jumlah").toString()
            depot = arguments?.getString("depot").toString()
            merk = arguments?.getString("merk").toString()
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val current = formatter.format(time)
            val historypesan = HistoryPemesanan()
            val totalharga = 16000 * jumlah.toInt()
            historypesan.depot = depot
            historypesan.date = current
            historypesan.pesanan = "Beli " + merk + jumlah + "x"
            historypesan.totalharga = "Rp. " + totalharga
            historypesan.point = "+ " + totalharga/8000 + " Point"
            historys.add(historypesan)
        }
        historyAdapter = HistoryAdapter(historys)
        historybinding.rvPemesanan.adapter = historyAdapter
        historybinding.rvPemesanan.layoutManager = LinearLayoutManager(context)
        return historybinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment History.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            History().apply {
                arguments = Bundle().apply {

                }
            }
    }
}