package com.example.security

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        setupActionBar()
        submit()

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupActionBar(){

        setSupportActionBar(toolbar_forgetpasswordactivity)

        val actionBar = supportActionBar
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.abc_vector_test)
        }
        toolbar_forgetpasswordactivity.setNavigationOnClickListener {onBackPressed()}

    }

    private fun submit(){
        btnSubmit.setOnClickListener{
            val email: String = et_email.text.toString().trim{ it <= ' '}
            if(TextUtils.isEmpty(et_email.text.toString())){
                et_email.setError("Please enter your email ")

            }else{

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener{ task ->
                            if (task.isSuccessful){
                                Toast.makeText(this@ForgetPasswordActivity, "Email sent Successful. ", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this@ForgetPasswordActivity, "Email sent Failed. ", Toast.LENGTH_LONG).show()
                            }
                        }

            }
        }
    }

}