package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private  lateinit var database: FirebaseDatabase
    private lateinit var referance: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // var database = FirebaseDatabase.getInstance().reference

        database= FirebaseDatabase.getInstance();
        referance=database.getReference("Checkin")

        button3.setOnClickListener {
            sendData1()
        }

    }

    private fun sendData1() {

        var customername = editTextTextPersonName6.text.toString().trim()
        var roomid = editTextTextPersonName2.text.toString().trim()
        var checkintime = editText2.text.toString().trim()
        var checkouttime = editTextTextPersonName5.text.toString().trim()
        var status = editTextTextPersonName10.text.toString().trim()


        if (customername.isNotEmpty() && roomid.isNotEmpty() && checkintime.isNotEmpty() &&status.isNotEmpty()) {
            var model = Check(customername,roomid, checkintime, checkouttime, status)
            ///var customername = referance.push().key

            referance.child(customername.toString()).setValue(model)
            editTextTextPersonName6.setText("")
            editTextTextPersonName2.setText("")
            editText2.setText("")
            editTextTextPersonName5.setText("")
            editTextTextPersonName10.setText("")


        } else {
            Toast.makeText(this, "All Field Required", Toast.LENGTH_LONG).show()
        }
    }
}