package com.michael.urgalon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import com.michael.urgalon.databinding.FragmentEditProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfile : Fragment() {
    private lateinit var fragmentEditProfileBinding: FragmentEditProfileBinding
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditProfileBinding = FragmentEditProfileBinding.inflate(inflater, container, false)
        fragmentEditProfileBinding.btnSave.setOnClickListener {
            val bundle = Bundle()
            val editname = fragmentEditProfileBinding.etNama.text.toString()
            bundle.putString("hasilname",editname)
            val profile: Profile = Profile.newInstance()
            profile.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame3,profile)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        fragmentEditProfileBinding.backtoprofile.setOnClickListener {
            val bundle = Bundle()
            val profile: Profile = Profile.newInstance()
            profile.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame3,profile)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        val navbar= requireActivity().findViewById<FrameLayout>(com.michael.urgalon.R.id.bottomNavigationView)
        navbar.visibility = View.VISIBLE
        return fragmentEditProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = arguments?.getString("hasilname").toString()
        val editname = view.findViewById<EditText>(R.id.et_nama)
        editname.setText(name).toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EditProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            EditProfile().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}