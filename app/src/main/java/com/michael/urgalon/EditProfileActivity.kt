package com.michael.urgalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.databinding.ActivityEditProfileBinding
import com.michael.urgalon.entity.HistoryPemesanan
import com.michael.urgalon.fragment.HistoryFragment

class EditProfileActivity : AppCompatActivity() {
    private lateinit var editProfileBinding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editProfileBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(editProfileBinding.root)

        editProfileBinding.btnSave.setOnClickListener {
            saveData(
                editProfileBinding.etNama.text.toString(),
                editProfileBinding.etAlamat.text.toString(),
                editProfileBinding.etNoHP.text.toString()
            )
        }
    }

    private fun saveData(name: String, alamat: String, noHp: String) {
        val database = Firebase.database("https://urgalon-125ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database.getReference("users").child(Firebase.auth.currentUser!!.uid).child("name").setValue(name)
        database.getReference("users").child(Firebase.auth.currentUser!!.uid).child("alamat").setValue(alamat)
        database.getReference("users").child(Firebase.auth.currentUser!!.uid).child("noHp").setValue(noHp)
        finish()
    }

//    private fun fetchData() {
//        val database = Firebase.database("https://urgalon-125ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
//        database.getReference("users").child(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
//            editProfileBinding.etNama.text =
//            editProfileBinding.etAlamat.text
//            editProfileBinding.etNoHP.text
//        }
//    }
}