package com.michael.urgalon.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.EditProfileActivity
import com.michael.urgalon.LoginActivity
import com.michael.urgalon.R
import com.michael.urgalon.databinding.FragmentProfileBinding
import com.michael.urgalon.entity.User
import com.michael.urgalon.viewmodel.HomeViewModel

class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var user: User
    private lateinit var firebaseAuth: FirebaseAuth
    private val homeVM: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        profileBinding.btnEditProfile.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    EditProfileActivity::class.java
                )
            )
        }
        profileBinding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        homeVM.loggedUser.observe(viewLifecycleOwner) {
            user = it
            profileBinding.tvUsername.text = it.name
            profileBinding.tvPoint.text = it.point.toString() + " Point"
        }
        return profileBinding.root
    }

    companion object {
        const val USER_REF = "users"
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}