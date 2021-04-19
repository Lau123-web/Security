package com.example.security

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_main.*

class HomepageActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@HomepageActivity, LoginActivity::class.java))
            finish()
        }

        bookingButton.setOnClickListener {
            startActivity(Intent(this@HomepageActivity, booking::class.java))
        }
    }
}