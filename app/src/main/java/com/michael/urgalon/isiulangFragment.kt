package com.michael.urgalon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michael.urgalon.databinding.FragmentIsiulangBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [isiulangFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class isiulangFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var fragmentIsiulangbinding: FragmentIsiulangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        fragmentIsiulangbinding.spinlayanan.setOnItemClickListener { adapterView, view, i, l ->  }{
//            val bundle = Bundle()
//            val home:Home = Home.newInstance()
//            home.arguments = bundle
//            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
//            fragmentTransaction?.replace(R.id.frame1,home)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
//        }
        return fragmentIsiulangbinding.root
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
            isiulangFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}