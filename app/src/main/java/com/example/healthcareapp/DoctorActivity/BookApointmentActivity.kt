package com.example.healthcareapp.DoctorActivity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.HomeActivity.HomeActivity
import com.example.healthcareapp.R
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class BookApointmentActivity : AppCompatActivity() {

    private lateinit var ed1: EditText
    private lateinit var ed2: EditText
    private lateinit var ed3: EditText
    private lateinit var ed4: EditText
    private lateinit var tv: TextView
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var btnBook: Button
    private lateinit var btnBack: Button
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_apointment)

        tv = findViewById(R.id.bookAppointment)
        ed1 = findViewById(R.id.fullNameBA)
        ed2 = findViewById(R.id.addressBA)
        ed3 = findViewById(R.id.contactBA)
        ed4 = findViewById(R.id.consultantFeesBA)
        dateButton = findViewById(R.id.buttonDatePicker)
        timeButton = findViewById(R.id.buttonTimePicker)
        btnBook = findViewById(R.id.buttonCheckout)
        btnBack = findViewById(R.id.CLbackbutton)

        ed1.keyListener = null
        ed2.keyListener = null
        ed3.keyListener = null
        ed4.keyListener = null

        val intent = intent
        val title = intent.getStringExtra("text1")
        val fullName = intent.getStringExtra("text2")
        val address = intent.getStringExtra("text3")
        val contact = intent.getStringExtra("text4")
        val fees = intent.getStringExtra("text5")

        tv.text = title
        ed1.setText(fullName)
        ed2.setText(address)
        ed3.setText(contact)
        ed4.setText(fees)

        initDatePicker()
        dateButton.setOnClickListener {
            datePickerDialog.show()
        }

        initTimePicker()
        timeButton.setOnClickListener {
            timePickerDialog.show()
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@BookApointmentActivity, FindDoctorActivity::class.java))
        }

        btnBook.setOnClickListener {
            val sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "").toString()

            val database = FirebaseDatabase.getInstance()
            val dbRef = database.getReference("appointments")

            val appointmentKey = dbRef.push().key

            val appointmentData = HashMap<String, Any>()
            appointmentData["title"] = title ?: ""
            appointmentData["fullName"] = fullName ?: ""
            appointmentData["address"] = address ?: ""
            appointmentData["contact"] = contact ?: ""
            appointmentData["fees"] = fees ?: ""
            appointmentData["date"] = dateButton.text.toString()
            appointmentData["time"] = timeButton.text.toString()

            appointmentKey?.let {
                dbRef.child(it).setValue(appointmentData)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Your appointment is done successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@BookApointmentActivity, HomeActivity::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(applicationContext, "Failed to book appointment", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // Handle the selected date here
            val adjustedMonth = month + 1
            val selectedDate = "$day/$adjustedMonth/$year"
            dateButton.text = selectedDate // Assuming dateButton is the button you want to update
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val style = DatePickerDialog.THEME_HOLO_DARK
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datePickerDialog.datePicker.minDate = cal.timeInMillis + 86400000

        // Show the DatePickerDialog when the button is clicked
        dateButton.setOnClickListener {
            datePickerDialog.show()
        }
    }

    private fun initTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            // Handle the selected time here
            timeButton.text = "$hourOfDay:$minute"
        }

        val cal = Calendar.getInstance()
        val hrs = cal.get(Calendar.HOUR_OF_DAY)
        val mins = cal.get(Calendar.MINUTE)

        val style = TimePickerDialog.THEME_HOLO_DARK
        timePickerDialog = TimePickerDialog(this, style, timeSetListener, hrs, mins, true)
    }
}
