package com.michael.urgalon

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.michael.urgalon.databinding.FragmentHomeBinding
import com.michael.urgalon.databinding.FragmentTotalisiulangBinding
import com.michael.urgalon.entity.Layanan
import com.michael.urgalon.entity.Metodebayar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [totalisiulang.newInstance] factory method to
 * create an instance of this fragment.
 */
class totalisiulang : Fragment() {
    private lateinit var totalisiulangBinding: FragmentTotalisiulangBinding
    private lateinit var lokasi: String
    private lateinit var jumlah: String
    private lateinit var depot: String
    var totalharga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        totalisiulangBinding= FragmentTotalisiulangBinding.inflate(inflater,container,false)
        val bayar = ArrayList<Metodebayar>()
        bayar.add(Metodebayar("COD"))
        bayar.add(Metodebayar("E-Wallet"))

        val bayarAdapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.simple_spinner_dropdown_item,
                bayar
            )
        }
        totalisiulangBinding.spinbayar.adapter = bayarAdapter

        totalisiulangBinding.backtoisiulang.setOnClickListener{
            val bundle = Bundle()
            val isiulang = isiulang.newInstance()
            isiulang.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(com.michael.urgalon.R.id.frametotalisiulang,isiulang)
//            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        }
        totalisiulangBinding.spinbayar.setSelection(0,false)
        totalisiulangBinding.spinbayar.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var metode = totalisiulangBinding.spinbayar.selectedItem.toString()
                if (metode == "E-Wallet") {
                    totalisiulangBinding.kolomewallet.visibility = View.VISIBLE
                }
                if (metode == "COD"){
                    totalisiulangBinding.kolomewallet.visibility = View.GONE
                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        totalisiulangBinding.totalpemesanan.setOnClickListener {
            val bundle = Bundle()
            val history = History.newInstance()
            val merk = totalisiulangBinding.txtisiulang.text.toString()
            history.arguments = bundle
            bundle.putString("depot",depot)
            bundle.putString("merk",merk)
            bundle.putString("jumlah",jumlah)
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(com.michael.urgalon.R.id.frame1,history)
//            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
            val navbar= requireActivity().findViewById<FrameLayout>(com.michael.urgalon.R.id.bottomNavigationView)
            navbar.visibility = View.VISIBLE
        }

        val navbar= requireActivity().findViewById<FrameLayout>(com.michael.urgalon.R.id.bottomNavigationView)
        navbar.visibility = View.GONE

        return totalisiulangBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lokasi = arguments?.getString("lokasiisi").toString()
        jumlah = arguments?.getString("jumlahisi").toString()
        depot = arguments?.getString("depotisi").toString()
        val locate = view.findViewById<TextView>(com.michael.urgalon.R.id.tv_lokasi)
        val jml = view.findViewById<TextView>(com.michael.urgalon.R.id.jmlisi)
        locate.setText(lokasi).toString()
        jml.setText(jumlah+"x").toString()
        totalharga = 8000 * jumlah.toInt()
        totalisiulangBinding.jmlharga.text = "Rp.$totalharga"
        totalisiulangBinding.tvTotalharga.text = "Rp.$totalharga"
        totalisiulangBinding.totalpemesanan.text = "Total Pemesanan: Rp " + totalharga
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment totalisiulang.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            totalisiulang().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}