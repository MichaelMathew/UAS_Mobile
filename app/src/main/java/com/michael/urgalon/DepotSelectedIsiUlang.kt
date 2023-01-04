package com.michael.urgalon

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.michael.urgalon.databinding.FragmentDepotSelectedGalonBinding
import com.michael.urgalon.databinding.FragmentDepotSelectedIsiUlangBinding
import com.michael.urgalon.entity.Layanan

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DepotSelectedIsiUlang.newInstance] factory method to
 * create an instance of this fragment.
 */
class DepotSelectedIsiUlang : Fragment() {
    private lateinit var depotsisiulangBinding: FragmentDepotSelectedIsiUlangBinding
    var isiulangair = 0
    var hargaisi = 8000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        depotsisiulangBinding = FragmentDepotSelectedIsiUlangBinding.inflate(inflater, container, false)
        depotsisiulangBinding.total.visibility = View.INVISIBLE
        val layanan = ArrayList<Layanan>()
        layanan.add(Layanan("Isi ulang"))
        layanan.add(Layanan("Beli Galon"))

        val layananAdapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.simple_spinner_dropdown_item,
                layanan
            )
        }
        depotsisiulangBinding.spinlayanan.adapter = layananAdapter
//        depotsisiulangBinding.spinlayanan.setOnClickListener {
//            val bundle = Bundle()
//            val isiulangFragment:isiulangFragment = isiulangFragment.newInstance()
//            isiulangFragment.arguments = bundle
//            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
//            fragmentTransaction?.replace(com.michael.urgalon.R.id.frameisiulang,isiulangFragment)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
//        }
        depotsisiulangBinding.spinlayanan.setSelection(0, false)
        depotsisiulangBinding.spinlayanan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val bundle = Bundle()
                val beligalon = Home.newInstance()
                beligalon.arguments = bundle
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.replace(com.michael.urgalon.R.id.frame1, beligalon)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        depotsisiulangBinding.isiminus.setOnClickListener {
            isiulangair--
            depotsisiulangBinding.jumlahisi.text = isiulangair.toString()
            if (isiulangair > 0){
                if(depotsisiulangBinding.etLokasi.text.toString() != ""){
                    depotsisiulangBinding.total.visibility = View.VISIBLE
                    depotsisiulangBinding.total.text = "Total Pemesanan: Rp " + isiulangair*hargaisi
                }
            }else {
//                if (depotsisiulangBinding.jmlaqua.text.toString().toInt() == 0){
                depotsisiulangBinding.isiminus.isClickable = false
//                }
                depotsisiulangBinding.total.visibility = View.INVISIBLE
            }
        }

        depotsisiulangBinding.isiplus.setOnClickListener {
            isiulangair++
            depotsisiulangBinding.jumlahisi.text = isiulangair.toString()
            if (isiulangair > 0){
                depotsisiulangBinding.isiminus.isClickable = true
                if(depotsisiulangBinding.etLokasi.text.toString() != ""){
                    depotsisiulangBinding.total.visibility = View.VISIBLE
                    depotsisiulangBinding.total.text = "Total Pemesanan: Rp " + isiulangair*hargaisi
                }
            }else {
                depotsisiulangBinding.total.visibility = View.INVISIBLE
            }
        }
        depotsisiulangBinding.total.setOnClickListener {
            val bundle = Bundle()
            val jumlahisi = depotsisiulangBinding.jumlahisi.text.toString()
            val depotisi = depotsisiulangBinding.tvDepotisi.text.toString()
            val lokasi = depotsisiulangBinding.etLokasi.text.toString()
            bundle.putString("jumlahisi",jumlahisi)
            bundle.putString("depotisi",depotisi)
            bundle.putString("lokasiisi",lokasi)
            val totalisiulang = totalisiulang.newInstance()
            totalisiulang.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(com.michael.urgalon.R.id.framedepotisiulang,totalisiulang)
//            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
            depotsisiulangBinding.total.visibility = View.INVISIBLE
        }

        return depotsisiulangBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DepotSelectedIsiUlang.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DepotSelectedIsiUlang().apply {
                arguments = Bundle().apply {
                }
            }
    }
}