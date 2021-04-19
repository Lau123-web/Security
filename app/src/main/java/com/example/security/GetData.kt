package com.example.security

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_get_data.*
import android.content.Intent
import android.widget.Toast
import com.example.security.DataAdapter

const val EXTRA_MESSAGE = "com.example.akj_asm.MESSAGE"
class GetData : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var referance: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)

        database= FirebaseDatabase.getInstance();
        referance = database.getReference("Housekeeping")
        getData()


        btn_sClean.setOnClickListener{

            var roomID = txt_data_roomID.text.toString().trim()
            if(roomID.isNotEmpty()){
                val intent = Intent(this, EditItemStatusActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, roomID)
                }
                referance.addListenerForSingleValueEvent(object : ValueEventListener
                {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("cancel",error.toString())
                    }
                    override fun onDataChange(snapshot: DataSnapshot){
                        var list= ArrayList<Room>()
                        for (data in snapshot.children)
                        {
                            val model = data.getValue(Room::class.java)
                            list.add(model as Room)
                        }
                        if(list.size>0)
                        {
                            var i = 0
                            var found = 0
                            while(i < list.size){
                                if (list.get(i).roomID.toString() == roomID){
                                    var newStatus = "Cleaning"
                                    var model = Room(list.get(i).checkin, list.get(i).expcheckout, list.get(i).roomID, newStatus)
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
                var list= ArrayList<Room>()
                for (data in snapshot.children)
                {
                    val model = data.getValue(Room::class.java)
                    list.add(model as Room)
                }
                if(list.size>0)
                {

                    val adapter = DataAdapter(list)
                    recyclerview.adapter = adapter
                }

            }

        }

        val hkeepingQuery: Query = referance.orderByChild("expcheckout")
        hkeepingQuery.addListenerForSingleValueEvent(getHKeepingData)

    }
}