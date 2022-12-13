package com.michael.urgalon

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.michael.urgalon.databinding.FragmentIsiulangBinding
import com.michael.urgalon.entity.Layanan

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [isiulangFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class isiulang : Fragment() {
    private lateinit var isiulangbinding: FragmentIsiulangBinding
    var isi = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        isiulangbinding = FragmentIsiulangBinding.inflate(inflater,container,false)
        val layanan = ArrayList<Layanan>()
        layanan.add(Layanan("Beli Galon"))
        layanan.add(Layanan("Isi ulang"))

        val layananAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                layanan
            )
        }

//        fragmentIsiulangbinding.spinlayanan.setOnItemClickListener { adapterView, view, i, l ->  }{
//            val bundle = Bundle()
//            val home:Home = Home.newInstance()
//            home.arguments = bundle
//            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
//            fragmentTransaction?.replace(R.id.frame1,home)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
//        }
        isiulangbinding.spinlayanan.adapter = layananAdapter
        isiulangbinding.spinlayanan.setSelection(1,false)
        isiulangbinding.spinlayanan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val bundle = Bundle()
                val home:Home = Home.newInstance()
                home.arguments = bundle
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.frameisiulang,home)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        isiulangbinding.isiminus.setOnClickListener {
            isi--
            isiulangbinding.jumlahisi.text = isi.toString()
        }
        isiulangbinding.isiplus.setOnClickListener {
            isi++
            isiulangbinding.jumlahisi.text = isi.toString()
        }
        var lokasi = isiulangbinding.etLokasi.text.toString()
        var total = isiulangbinding.total

        if(TextUtils.isEmpty(lokasi)){
            total.visibility = View.INVISIBLE
        }
        else{
            total.visibility = View.VISIBLE
        }

        return isiulangbinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment isiulangFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            isiulang().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}