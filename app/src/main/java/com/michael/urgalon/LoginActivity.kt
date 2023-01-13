package com.michael.urgalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.databinding.ActivityLoginBinding
import com.michael.urgalon.entity.User
import com.michael.urgalon.fragment.ProfileFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.trim().isEmpty() || password.trim().isEmpty()) {
                if (email.trim().isEmpty()) {
                    binding.etEmail.error = resources.getString(R.string.message_error_email_empty)
                }
                if (password.trim().isEmpty()) {
                    binding.etPassword.error = resources.getString(R.string.message_error_pw_empty)
                }
                Snackbar.make(
                    this,
                    binding.btnLogin,
                    resources.getString(R.string.msg_failed_login),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (checkUserData(it.result.user!!.uid) != null) {
                            updateUI()
                        } else {
                            saveData(User(it.result.user!!.uid, email, 0))?.addOnSuccessListener {
                                updateUI()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            resources.getString(R.string.err_invalid_login),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart();
        val curUser = firebaseAuth.currentUser;
        curUser?.let {
            updateUI();
        }
    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java);
        startActivity(intent);
        this.finish();
    }

    private fun checkUserData(id: String): User? {
        var user: User? = null
        val database = Firebase.database("https://urgalon-125ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val reference = database.getReference(ProfileFragment.USER_REF)
        reference.child(id).get().addOnSuccessListener {
            user = it.value as User
        }
        return user
    }

    private fun saveData(user: User): Task<Void>? {
        val database = Firebase.database("https://urgalon-125ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val reference = user.id?.let {
            database.getReference("users").child(it)
        }
        return reference?.setValue(user)
    }
}