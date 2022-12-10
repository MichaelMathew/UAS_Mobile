package com.michael.urgalon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var name: String
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        profileBinding.btnEditProfile.setOnClickListener {
            val name = profileBinding.tvUsername.text.toString()
            val bundle = Bundle()
            bundle.putString("hasilname",name)
            val editProfile: EditProfile = EditProfile.newInstance()
            editProfile.arguments = bundle
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame2,editProfile)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        firebaseAuth = Firebase.auth
        profileBinding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
        }
        return profileBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = arguments?.getString("hasilname").toString()
        val uername = view.findViewById<TextView>(R.id.tv_username)
        uername.setText(name).toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            Profile().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}