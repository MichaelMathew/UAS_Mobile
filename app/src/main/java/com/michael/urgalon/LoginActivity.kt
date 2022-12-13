package com.michael.urgalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth
        binding.tvSignup.setOnClickListener{
            val intent = Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }


        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(email.trim().isEmpty() || password.trim().isEmpty()){
                if(email.trim().isEmpty()){
                    binding.etEmail.error = resources.getString(R.string.message_error_email_empty)
                }
                if(password.trim().isEmpty()){
                    binding.etPassword.error = resources.getString(R.string.message_error_pw_empty)
                }
                Snackbar.make(this,binding.btnLogin,resources.getString(R.string.msg_failed_login)
                    , Snackbar.LENGTH_LONG).show()
            }else {
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateUI()
                    } else {
                        Toast.makeText(this@LoginActivity, resources.getString(R.string.err_invalid_login), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
//    override fun onStart() {
//        super.onStart();
//        val curUser = firebaseAuth.currentUser;
//        curUser?.let {
//            updateUI();
//        }
//    }

    private fun updateUI(){
        val intent = Intent(this@LoginActivity, HomeActivity::class.java);
        startActivity(intent);
        this.finish();
    }
}