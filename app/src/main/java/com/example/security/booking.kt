package com.example.security

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.security.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.check

class booking : AppCompatActivity() {

    private lateinit var database:FirebaseDatabase
    private lateinit var referance: DatabaseReference
    var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)


    @RequiresApi(Build.VERSION_CODES.O)
    val getDate = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.N)
    val year = getDate.get(Calendar.YEAR)
    @RequiresApi(Build.VERSION_CODES.N)
    val month = getDate.get(Calendar.MONTH)
    @RequiresApi(Build.VERSION_CODES.N)
    val day = getDate.get(Calendar.DAY_OF_MONTH)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val buttonS: Button = findViewById<Button>(R.id.btn_send)

        database= FirebaseDatabase.getInstance();
        referance=database.getReference("Users")

        Cin.setOnClickListener {
            val datepicker = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                        Cin.setText(String.format("%02d-%02d-%02d",myear, mmonth+1, mdayOfMonth))
                    },year,month,day
            )

            datepicker.show()
        }
        Cout.setOnClickListener {
            val datepicker2 = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->

                        Cout.setText(String.format("%02d-%02d-%02d",myear, mmonth+1, mdayOfMonth))

                    },year,month,day
            )

            datepicker2.show()
        }

        calTotal.setOnClickListener{
            val start = LocalDate.parse(Cin.text)
            val end = LocalDate.parse(Cout.text)
            val night = start.until(end, ChronoUnit.DAYS)

            testDays.text = "${night.toString()} days"
            totalCost.text = "RM${night * 100}"

        }

        check.setOnClickListener{
            val start = LocalDate.parse(Cin.text)
            val end = LocalDate.parse(Cout.text)
            val night = start.until(end, ChronoUnit.DAYS)
            var Cpayment = payment.text.toString().trim().toInt()

            testDays.text = "${night.toString()} days"
            totalCost.text = "RM${night * 100}"
            balance.text = "RM${Cpayment - (night * 100)}"
        }

        buttonS.setOnClickListener{
            sendData()
        }
        btn_getdata.setOnClickListener{
            startActivity(Intent(applicationContext,cbook::class.java))
        }
        btntest.setOnClickListener{
            val intent = Intent(this@booking, cbook::class.java)
            startActivity(intent);
        }
    }
    @SuppressLint("SetTextI18n")

    private fun sendData()
    {
        var roomID=roomid.text.toString().trim()
        var cName=custName.text.toString().trim()
        var pNo=phoneNo.text.toString().trim().toInt()
        var numberOfGuests = numOfG.text.toString().toInt()
        var cRequest = request.text.toString().trim()
        var startD = Cin.text.toString().trim()
        var endD = Cout.text.toString().trim()
        var testD = testDays.text.toString().trim()
        var totalC = totalCost.text.toString().trim()
        var balance = balance.text.toString().trim()
        var doneP = "done"

        if(cName.isNotEmpty() && cRequest.isNotEmpty()&& balance.isNotEmpty())
        {
            var model = DatabaseModel(roomID,cName,pNo,numberOfGuests,cRequest,testD,totalC,doneP,startD,endD)
            var id= referance.push().key

            referance.child(roomID).setValue(model)
            custName.setText("")
            phoneNo.setText("")
            numOfG.setText("")
            request.setText("")
            testDays.setText("")
            totalCost.setText("")
            payment.setText("")
            Cin.setText("")
            Cout.setText("")
        }
        else{
            Toast.makeText(applicationContext,"All Field Required",Toast.LENGTH_LONG).show()
        }

    }

}