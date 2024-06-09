package com.example.firebasememo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.firebasememo.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//class MainActivity : AppCompatActivity() {
//
//
//    // ActivityMainBindingは、このアクティビティで使用するレイアウトのビューバインディングです。
//    // ビューバインディングは、レイアウトXMLのビューに直接アクセスできるようにする機能です。
//    private lateinit var binding: ActivityMainBinding
//
//    // Add this line to initialize Firestore
//    private val firestore = Firebase.firestore
//
//    /**
//     * onCreateは、アクティビティが生成されるときに最初に呼び出されるメソッドです。
//     * ここで、画面の初期設定やリソースの読み込みなどの初期化処理を行います。
//     */
//    override fun onCreate(savedInstanceState: Bundle?) {
//        // 既存のアクティビティのonCreateメソッドを呼び出すことで、
//        // 基本的な初期化処理を行います。
//        super.onCreate(savedInstanceState)
//
//        // ビューバインディングのインスタンスを生成し、
//        // これを使ってレイアウトリソースからビューを取得します。
//        binding = ActivityMainBinding.inflate(layoutInflater)
//
//        // アクティビティのコンテンツビューを、ビューバインディングのルートビューに設定します。
//        setContentView(binding.root)
//
//        // Retrieve user email from intent
//        val userEmail = intent.getStringExtra("userEmail")
//
//        // Use the user's email to create a Firestore collection reference
//        val memosCollection = firestore.collection(userEmail ?: "defaultCollection").document("memos").collection("memos")
//
//        parentFragmentManager.beginTransaction()
//        .replace(R.id.nav_host_fragment, MainFragment())
//        .commit()
//
//
////        // アプリのツールバーを設定します。
//        val toolbar: Toolbar = binding.appToolbar
//        setSupportActionBar(toolbar)
//    }
//}

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private val firestore = Firebase.firestore
//
//    // Add this line to store user email
//    private var userEmail: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Lấy dữ liệu từ Intent
//        val userEmail = intent.getStringExtra("userEmail")
//
//        // Tạo Bundle để truyền dữ liệu vào MainFragment
//        val bundle = Bundle()
//        bundle.putString("userEmail", userEmail)
//
//        // Tạo MainFragment và đặt dữ liệu bằng cách sử dụng setArguments
//        val mainFragment = MainFragment()
//        mainFragment.arguments = bundle
//
//        // Thay thế MainFragment trong MainActivity
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.nav_host_fragment, MainFragment())
//            .commit()
//    }
//}

// MainActivity.kt

//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.firebasememo.databinding.ActivityMainBinding
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private val firestore = Firebase.firestore
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val userEmail = intent.getStringExtra("userEmail")
//
//        if (userEmail != null) {
//            val memosCollection = firestore.collection(userEmail).document("memos").collection("memos")
//
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment, MainFragment())
//                .commit()
//        } else {
//            // Handle case where userEmail is null
//        }
//    }
//}

// MainActivity.kt

//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.firebasememo.databinding.ActivityMainBinding
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive user information from the previous screen
        val myIntent = intent

        val userEmail = myIntent.getStringExtra("userEmail")
        val user = myIntent.getStringExtra("userName")
        val email = myIntent.getStringExtra("email")
        binding.tvUser.text = user
        binding.tvEmail.text = email

        // Logout button click
        binding.btLogout.setOnClickListener {
            AuthUI.getInstance().signOut(this)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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

        if (userEmail != null) {
            val memosCollection = firestore.collection("memos").document(userEmail)

            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, MainFragment.newInstance(userEmail))
                .commit()
        } else {
            // Handle case where userEmail is null
        }

//        // Logout button click
//        binding.btLogout.setOnClickListener {
//            AuthUI.getInstance().signOut(this)
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }



    }
}


