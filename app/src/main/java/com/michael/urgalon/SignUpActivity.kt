package com.michael.urgalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.michael.urgalon.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this@SignUpActivity,LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmpassword = binding.etConfirmpassword.text.toString()
            if (username.trim().isNotEmpty() && email.trim().isNotEmpty() && password.trim().isNotEmpty() && confirmpassword.trim().isNotEmpty()){
                if (password.trim() != confirmpassword.trim()) {
                    Toast.makeText(this@SignUpActivity, "Password dan Confirm Password tidak sama", Toast.LENGTH_SHORT).show()
                }
                else {
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
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
        }
    }
}