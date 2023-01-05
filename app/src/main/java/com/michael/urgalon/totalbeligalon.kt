package com.michael.urgalon

import android.R
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import com.michael.urgalon.databinding.FragmentTotalbeligalonBinding
import com.michael.urgalon.databinding.FragmentTotalisiulangBinding
import com.michael.urgalon.entity.Metodebayar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [totalbeligalon.newInstance] factory method to
 * create an instance of this fragment.
 */
class totalbeligalon : Fragment() {
    private lateinit var totalbeligalonBinding: FragmentTotalbeligalonBinding
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
        totalbeligalonBinding= FragmentTotalbeligalonBinding.inflate(inflater,container,false)

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
        totalbeligalonBinding.spinbayar.adapter = bayarAdapter

        totalbeligalonBinding.backtobeligalon.setOnClickListener{
            val bundle = Bundle()
            val beligalon = DepotSelectedGalon.newInstance()
            beligalon.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(com.michael.urgalon.R.id.frametotalbeligalon,beligalon)
//            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
            val navbar= requireActivity().findViewById<FrameLayout>(com.michael.urgalon.R.id.bottomNavigationView)
            navbar.visibility = View.VISIBLE
        }
        totalbeligalonBinding.spinbayar.setSelection(0,false)
        totalbeligalonBinding.spinbayar.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var metode = totalbeligalonBinding.spinbayar.selectedItem.toString()
                if (metode == "E-Wallet") {
                    totalbeligalonBinding.kolomewallet.visibility = View.VISIBLE
                }
                if (metode == "COD"){
                    totalbeligalonBinding.gopaytext.setTypeface(null,Typeface.NORMAL)
                    totalbeligalonBinding.shopeepaytext.setTypeface(null,Typeface.NORMAL)
                    totalbeligalonBinding.ovotext.setTypeface(null,Typeface.NORMAL)
                    totalbeligalonBinding.kolomewallet.visibility = View.GONE
                    if (totalbeligalonBinding.etTujuan.text.toString() != ""){
                        totalbeligalonBinding.totpemesanan.visibility = View.VISIBLE
                    }
                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        totalbeligalonBinding.gopayclick.setOnClickListener {
            totalbeligalonBinding.gopaytext.setTypeface(null,Typeface.BOLD)
            totalbeligalonBinding.shopeepaytext.setTypeface(null,Typeface.NORMAL)
            totalbeligalonBinding.ovotext.setTypeface(null,Typeface.NORMAL)
            if (totalbeligalonBinding.etTujuan.text.toString() != ""){
                totalbeligalonBinding.totpemesanan.visibility = View.VISIBLE
            }
        }
        totalbeligalonBinding.ovoclick.setOnClickListener {
            totalbeligalonBinding.ovotext.setTypeface(null,Typeface.BOLD)
            totalbeligalonBinding.gopaytext.setTypeface(null,Typeface.NORMAL)
            totalbeligalonBinding.shopeepaytext.setTypeface(null,Typeface.NORMAL)
            if (totalbeligalonBinding.etTujuan.text.toString() != ""){
                totalbeligalonBinding.totpemesanan.visibility = View.VISIBLE
            }
        }
        totalbeligalonBinding.shopeepayclick.setOnClickListener {
            totalbeligalonBinding.shopeepaytext.setTypeface(null,Typeface.BOLD)
            totalbeligalonBinding.gopaytext.setTypeface(null,Typeface.NORMAL)
            totalbeligalonBinding.ovotext.setTypeface(null,Typeface.NORMAL)
            if (totalbeligalonBinding.etTujuan.text.toString() != ""){
                totalbeligalonBinding.totpemesanan.visibility = View.VISIBLE
            }
        }
        totalbeligalonBinding.totpemesanan.setOnClickListener {
            val bundle = Bundle()
            val history = History.newInstance()
            val merk = totalbeligalonBinding.merkgalon.text.toString()
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
        return totalbeligalonBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jumlah = arguments?.getString("jumlah").toString()
        depot = arguments?.getString("depot").toString()
        val jml = view.findViewById<TextView>(com.michael.urgalon.R.id.jmlisi)
        jml.setText(jumlah+"x").toString()
        totalharga = 16000 * jumlah.toInt()
        totalbeligalonBinding.jmlharga.text = "Rp.$totalharga"
        totalbeligalonBinding.tvTotalharga.text = "Rp.$totalharga"
        totalbeligalonBinding.totpemesanan.text = "Total Pemesanan: Rp. $totalharga"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment totalbeligalon.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            totalbeligalon().apply {
                arguments = Bundle().apply {
                }
            }
    }
}