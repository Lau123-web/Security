package com.example.security

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.housekeep.view.*
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_item_status.*

class EditItemStatusActivity : AppCompatActivity() {

    private lateinit var database:FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var hkReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item_status)

        var curRoomID = intent.getStringExtra(EXTRA_MESSAGE)
        tv_hk_roomID.text = curRoomID

        database= FirebaseDatabase.getInstance();
        reference = database.getReference("ItemStatus")
        hkReference = database.getReference("Housekeeping")

        btn_complete.setOnClickListener{
            var fStatus = txt_fs.text.toString().trim()
            var lStatus = txt_ls.text.toString().trim()
            var wStatus = txt_ws.text.toString().trim()
            var other = txt_other.text.toString().trim()
            var insertRID = curRoomID.toString().trim().toInt()

            if(!fStatus.isNullOrEmpty() && !lStatus.isNullOrEmpty() && !wStatus.isNullOrEmpty())
            {
                var model = ItemStatus(fStatus,lStatus,wStatus,other,insertRID)
                var id= reference.push().key
                var found = 0

                reference.child(id!!).setValue(model)
                txt_fs.setText("")
                txt_ls.setText("")
                txt_ws.setText("")
                txt_other.setText("")
                txt_fs.requestFocus()
                val message = "Done cleaning"

                hkReference.addValueEventListener(object : ValueEventListener
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

                            while(i < list.size){
                                if (list.get(i).roomID.toString() == curRoomID){
                                    found = 1
                                    var newStatus = "Done cleaning"
                                    var model = Room(list.get(i).checkin, list.get(i).expcheckout, list.get(i).roomID, newStatus)
                                    hkReference.child(curRoomID.toString()).setValue(model)
                                    curRoomID = ""
                                }
                                i = i + 1
                            }
                            if(found == 0){
                                Toast.makeText(applicationContext,"The ID is not Exist.", Toast.LENGTH_LONG).show()
                            }else if(found == 1){
                                Toast.makeText(applicationContext,"Done.", Toast.LENGTH_LONG).show()
                                finish()
                            }

                        }

                    }

                }
                )
            }else{
                Toast.makeText(applicationContext,"All text fields are required.", Toast.LENGTH_LONG).show()
            }
        }

        btn_cancel.setOnClickListener{
            txt_fs.setText("")
            txt_ls.setText("")
            txt_ws.setText("")
            txt_other.setText("")
            txt_fs.requestFocus()


            hkReference.addValueEventListener(object : ValueEventListener
            {
                override fun onCancelled(error: DatabaseError) {
                    Log.e("error",error.toString())
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
                            if (list.get(i).roomID.toString() == curRoomID){
                                found = 1
                                var newStatus = "Wait for cleaning"
                                var model = Room(list.get(i).checkin, list.get(i).expcheckout, list.get(i).roomID, newStatus)
                                hkReference.child(curRoomID.toString()).setValue(model)
                                curRoomID = ""
                            }
                            i = i + 1
                        }
                        if(found == 1){
                            Toast.makeText(applicationContext,"Cancel.", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }

                }

            }
            )
        }
    }



}