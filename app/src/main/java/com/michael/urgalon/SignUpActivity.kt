package com.michael.urgalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this@SignUpActivity,LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmpassword = binding.etConfirmpassword.text.toString()
            if (username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || confirmpassword.trim().isEmpty()){
                if (password.trim() != confirmpassword.trim()) {
                    binding.etPassword.error = resources.getString(R.string.message_error_pw)
                    binding.etConfirmpassword.error = resources.getString(R.string.message_error_confirmpw)
                }
                if(username.trim().isEmpty()){
                    binding.etUsername.error = resources.getString(R.string.message_error_username_empty)
                }
                if(email.trim().isEmpty()){
                    binding.etEmail.error = resources.getString(R.string.message_error_email_empty)
                }
                if(password.trim().isEmpty()){
                    binding.etPassword.error = resources.getString(R.string.message_error_pw_empty)
                }
                if(confirmpassword.trim().isEmpty()){
                    binding.etConfirmpassword.error = resources.getString(R.string.message_error_confirmpw_empty)
                }
                Snackbar.make(this,binding.btnSignUp,resources.getString(R.string.msg_failed_signUp)
                    , Snackbar.LENGTH_LONG).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateUI()
                    } else {
                        Toast.makeText(this@SignUpActivity, resources.getString(R.string.err_invalid_signup), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun updateUI(){
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}