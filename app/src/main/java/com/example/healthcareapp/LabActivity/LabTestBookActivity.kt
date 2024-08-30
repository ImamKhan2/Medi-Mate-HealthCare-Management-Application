package com.example.healthcareapp.LabActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.healthcareapp.Dataclass.Order
import com.google.firebase.database.FirebaseDatabase

class LabTestBookActivity : AppCompatActivity() {
    private lateinit var edname: EditText
    private lateinit var edaddress: EditText
    private lateinit var edcontact: EditText
    private lateinit var edpincode: EditText
    private lateinit var btnBooking: Button
    private lateinit var database: FirebaseDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lab_test_book)

        edname = findViewById(R.id.labBookFullname)
        edaddress = findViewById(R.id.labBookAddress)
        edcontact = findViewById(R.id.textContactnumber)
        edpincode = findViewById(R.id.textPincode)
        btnBooking = findViewById(R.id.buttonBooking)

        database = FirebaseDatabase.getInstance()

        val intent = intent
        val price = intent.getStringExtra("price")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")

        // Splitting price if needed
        val priceParts = price?.split(":") ?: emptyList()

        btnBooking.setOnClickListener {
            val sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "") ?: ""

            val name = edname.text.toString().trim()
            val address = edaddress.text.toString().trim()
            val contact = edcontact.text.toString().trim()

            // Check if any field is empty
            if (name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = database.getReference("orders")
            val order = Order(username, name, address, contact)
            db.push().setValue(order)

            // Clear cart
            val cartDb = database.getReference("cart")
            cartDb.child(username).child("Lab").removeValue()

            Toast.makeText(applicationContext, "Your booking is done successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@LabTestBookActivity, HomeActivity::class.java))
        }

        // You can then use the retrieved values as needed
        findViewById<TextView>(R.id.textViewcartTotalPrice).text = priceParts[0]
        findViewById<TextView>(R.id.buttoncartlabDatePicker).text = date
        findViewById<TextView>(R.id.buttoncartlabTimePicker).text = time

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}



