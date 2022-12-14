package com.michael.urgalon

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.michael.urgalon.databinding.FragmentHomeBinding
import com.michael.urgalon.entity.Layanan


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
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
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
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
        homeBinding.spinlayanan.adapter = layananAdapter
//        homeBinding.spinlayanan.setOnClickListener {
//            val bundle = Bundle()
//            val isiulangFragment:isiulangFragment = isiulangFragment.newInstance()
//            isiulangFragment.arguments = bundle
//            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
//            fragmentTransaction?.replace(com.michael.urgalon.R.id.frameisiulang,isiulangFragment)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
//        }
        homeBinding.spinlayanan.setSelection(0,false)
        homeBinding.spinlayanan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val bundle = Bundle()
                val isiulang = isiulang.newInstance()
                isiulang.arguments = bundle
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.replace(com.michael.urgalon.R.id.frame1,isiulang)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        homeBinding.plusaqua.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Silahkan Pilih Depot Terlebih Dahulu")
            builder.setPositiveButton("OK"
            ) { p0, p1 -> p0.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }




        homeBinding.minusron88.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Silahkan Pilih Depot Terlebih Dahulu")
            builder.setPositiveButton("OK"
            ) { p0, p1 -> p0.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }
        homeBinding.plusron88.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Silahkan Pilih Depot Terlebih Dahulu")
            builder.setPositiveButton("OK"
            ) { p0, p1 -> p0.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }


        homeBinding.image1.setOnClickListener {
            val bundle = Bundle()
            val depotsgalon:DepotSelectedGalon = DepotSelectedGalon.newInstance()
            depotsgalon.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(com.michael.urgalon.R.id.frame1,depotsgalon)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }


        val navbar= requireActivity().findViewById<FrameLayout>(com.michael.urgalon.R.id.bottomNavigationView)
        navbar.visibility = View.VISIBLE
        return homeBinding.root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            Home().apply {
                arguments = Bundle().apply {

                }
            }
    }
}