package com.example.security

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import android.widget.Toast
import com.example.security.DataAdapter
import kotlinx.android.synthetic.main.activity_getdata.btn_sClean
import kotlinx.android.synthetic.main.activity_getdata.recyclerview
import kotlinx.android.synthetic.main.activity_getdata.txt_data_roomID

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

            var roomID = txt_data_roomID.text.toString().trim()
            if(roomID.isNotEmpty()){

                referance.addListenerForSingleValueEvent(object : ValueEventListener
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
                            var found = 0
                            while(i < list.size){
                                if (list.get(i).roomID.toString() == roomID){
                                    var newStatus = "Cleaning"
                                    var model = DatabaseModel(list.get(i).roomID, list.get(i).name, list.get(i).pNo, list.get(i).numG,list.get(i).custR,list.get(i).bDay,list.get(i).tCost,newStatus,list.get(i).startD,list.get(i).endD)
                                    referance.child(roomID).setValue(model)
                                    found = 1
                                    roomID = ""
                                }
                                i = i + 1
                            }
                            if(found == 0){
                                Toast.makeText(applicationContext,"The ID You entered is not Exist.", Toast.LENGTH_LONG).show()
                                txt_data_roomID.setText("")
                            }else if(found == 1){
                                Toast.makeText(applicationContext,"Start Cleaning.", Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            }
                        }

                    }

                }
                )
            }else{
                txt_data_roomID.error = "Please enter the Room ID."
                txt_data_roomID.requestFocus()
            }
        }

    }

    private fun getData(){
        val getHKeepingData = object : ValueEventListener
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

                    val adapter = BookingDataAdapter(list)
                    recyclerview.adapter = adapter
                }

            }

        }

        val hkeepingQuery: Query = referance.orderByChild("name")
        hkeepingQuery.addListenerForSingleValueEvent(getHKeepingData)

    }
}