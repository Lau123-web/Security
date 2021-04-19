package com.example.security

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.assignment.DataAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_getdata.*

class BookingGetdata : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var referance: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getdata)

        database= FirebaseDatabase.getInstance();
        referance=database.getReference("Users")
        getData()
    }
    private fun getData(){
        referance.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel",error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot){
                var list= ArrayList<DatabaseModel>()
                for (data in snapshot.children)
                {
                    val model = data.getValue(DatabaseModel::class.java)
                    list.add(model as DatabaseModel)
                }
                if(list.size>0)
                {
                    val adapter = DataAdapter(list)
                    recyclerview.adapter = adapter
                }

            }

        }
        )
    }
}