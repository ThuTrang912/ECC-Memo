package com.example.firebasememo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebasememo.databinding.ActivityLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Login button click
        binding.btLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()



            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmailAndPassword(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Signup button click
        binding.btSignup.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.ic_launcher)
                .setTheme(R.style.Theme_FireBaseMemo)
                .build()
            signInLauncher.launch(signInIntent)

        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    user?.let {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userName", it.displayName)
                        intent.putExtra("email", it.email)

                        // Pass the email as an extra to MainActivity
                        intent.putExtra("userEmail", it.email)

                        startActivity(intent)
                        finish()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("LoginActivity", "signInWithEmailAndPassword:failure", task.exception)
                }
            }
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                //chuyen huong den trang MainActivity va kem theo userid
                val nextIntent = Intent(this, MainActivity::class.java)
                nextIntent.putExtra("userName", it.displayName)
                nextIntent.putExtra("email", it.email)

                // Pass the email as an extra to MainActivity
                nextIntent.putExtra("userEmail", it.email)

                startActivity(nextIntent)
                finish()
            }
        } else {
            val response = result.idpResponse
            if (response == null) {
                Toast.makeText(applicationContext, "Authentication canceled", Toast.LENGTH_SHORT).show()
            } else {
                response.error?.let {
                    Log.e("err", it.toString())
                }
            }
        }
    }
//    override fun onResume() {
//        super.onResume()
//        // Check if the user is already signed in, if yes, go directly to UserActivity
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        if (currentUser != null) {
//            //Tạm thời tắt việc chuyển hướng tự động để debug
//            val intent = Intent(this, UserActivity::class.java)
//            intent.putExtra("userName", currentUser.displayName)
//            intent.putExtra("email", currentUser.email)
//            startActivity(intent)
//            finish()
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        // Check if the user is already signed in, if yes, go directly to UserActivity
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            // Temporarily disable automatic redirection for debugging
//            val intent = Intent(this, UserActivity::class.java)
//            intent.putExtra("userName", currentUser.displayName)
//            intent.putExtra("email", currentUser.email)
//
//            // Pass the email as an extra to MainActivity
//            intent.putExtra("userEmail", currentUser.email)
//
//            startActivity(intent)
//            finish()
//        }
//    }

}