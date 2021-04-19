package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.rv_layout.view.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_getdata.*
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_getdata.recyclerview
import kotlinx.android.synthetic.main.activity_rv_layout.*

class cbook : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var referance: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getdata)

        database= FirebaseDatabase.getInstance();
        referance = database.getReference("Users")
        getData()

        btn_sClean.setOnClickListener{
            var roomID = ""
            roomID = txt_data_roomID.text.toString().trim()
            var found = 0

            if(roomID.isNotEmpty()){

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
                            var i = 0

                            while(i < list.size){
                                if (list.get(i).roomID== roomID){
                                    var newStatus = "Cleaning"
                                    var model = DatabaseModel(list.get(i).roomID, list.get(i).name, list.get(i).pNo, list.get(i).numG,list.get(i).custR,list.get(i).bDay,list.get(i).tCost,newStatus,list.get(i).startD,list.get(i).endD)
                                    referance.child(roomID).setValue(model)
                                    found = 1
                                }
                                i = i + 1
                            }
                            if(found == 0){
                                Toast.makeText(applicationContext,"The ID You entered is not Exist.", Toast.LENGTH_LONG).show()
                            }else if(found == 1){
                                Toast.makeText(applicationContext,"Start Cleaning.", Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            }
                        }

                    }

                }
                )
                txt_data_roomID.setText("")
                roomID = ""
            }else{
                txt_data_roomID.error = "Please enter the Room ID."
                txt_data_roomID.requestFocus()
            }
        }
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