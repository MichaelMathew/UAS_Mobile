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
import com.michael.urgalon.databinding.FragmentHomeBinding
import com.michael.urgalon.entity.Layanan

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DepotSelectedGalon.newInstance] factory method to
 * create an instance of this fragment.
 */
class DepotSelectedGalon : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var depotsgalonBinding: FragmentDepotSelectedGalonBinding
    var isiaqua = 0
    var isianidis= 0
    var isivit = 0
    var isiron88 = 0
    var hargaaqua = 16000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        depotsgalonBinding = FragmentDepotSelectedGalonBinding.inflate(inflater, container, false)
        depotsgalonBinding.hargatotalpemesanan.hide()
        val layanan = ArrayList<Layanan>()
        layanan.add(Layanan("Beli Galon"))
        layanan.add(Layanan("Isi ulang"))

        val layananAdapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.simple_spinner_dropdown_item,
                layanan
            )
        }
        depotsgalonBinding.spinlayanan.adapter = layananAdapter
//        depotsgalonBinding.spinlayanan.setOnClickListener {
//            val bundle = Bundle()
//            val isiulangFragment:isiulangFragment = isiulangFragment.newInstance()
//            isiulangFragment.arguments = bundle
//            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
//            fragmentTransaction?.replace(com.michael.urgalon.R.id.frameisiulang,isiulangFragment)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
//        }
        depotsgalonBinding.spinlayanan.setSelection(0, false)
        depotsgalonBinding.spinlayanan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val bundle = Bundle()
                val isiulang = isiulang.newInstance()
                isiulang.arguments = bundle
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.replace(com.michael.urgalon.R.id.frame1, isiulang)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        depotsgalonBinding.minusaqua.setOnClickListener {
            isiaqua--
            depotsgalonBinding.jmlaqua.text = isiaqua.toString()
            if (isiaqua > 0){
                depotsgalonBinding.hargatotalpemesanan.show()
                depotsgalonBinding.hargatotalpemesanan.text = "Total Pemesanan: Rp " + isiaqua*hargaaqua
            }else {
//                if (depotsgalonBinding.jmlaqua.text.toString().toInt() == 0){
                depotsgalonBinding.minusaqua.isClickable = false
//                }
                depotsgalonBinding.hargatotalpemesanan.hide()
            }
        }

        depotsgalonBinding.plusaqua.setOnClickListener {
            isiaqua++
            depotsgalonBinding.jmlaqua.text = isiaqua.toString()
            if (isiaqua > 0){
                depotsgalonBinding.minusaqua.isClickable = true
                depotsgalonBinding.hargatotalpemesanan.show()
                depotsgalonBinding.hargatotalpemesanan.text = "Total Pemesanan: Rp " + isiaqua*hargaaqua
            }else {
                depotsgalonBinding.hargatotalpemesanan.hide()
            }
        }
        depotsgalonBinding.hargatotalpemesanan.setOnClickListener {
            val bundle = Bundle()
            val jumlah = depotsgalonBinding.jmlaqua.text.toString()
            val depot = depotsgalonBinding.tvDepot.text.toString()
            bundle.putString("jumlah",jumlah)
            bundle.putString("depot",depot)
            val totalbeligalon = totalbeligalon.newInstance()
            totalbeligalon.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(com.michael.urgalon.R.id.framedepotsgalon,totalbeligalon)
//            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
            depotsgalonBinding.hargatotalpemesanan.hide()
        }
        return depotsgalonBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DepotSelectedGalon.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DepotSelectedGalon().apply {
                arguments = Bundle().apply {
                }
            }
    }
}