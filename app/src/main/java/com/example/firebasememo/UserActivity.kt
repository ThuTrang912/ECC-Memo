package com.example.firebasememo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasememo.databinding.ActivityLoginBinding
import com.example.firebasememo.databinding.ActivityUserBinding
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive user information from the previous screen
        val myIntent = intent
        val user = myIntent.getStringExtra("userName")
        val email = myIntent.getStringExtra("email")
        binding.tvUser.text = user
        binding.tvEmail.text = email

        // Logout button click
        binding.btLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Delete button click
        binding.btDeleteAccount.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            user?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Account deleted successfully
                        Toast.makeText(
                            applicationContext,
                            "アカウントが削除されました",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Deletion failed
                        Toast.makeText(
                            applicationContext,
                            "アカウントの削除に失敗しました",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}