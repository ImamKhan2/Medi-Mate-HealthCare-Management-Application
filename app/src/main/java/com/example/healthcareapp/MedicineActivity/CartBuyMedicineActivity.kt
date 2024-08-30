package com.example.healthcareapp.MedicineActivity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class CartBuyMedicineActivity : AppCompatActivity() {
    private lateinit var item: HashMap<String, String>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var tvTotal: TextView
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateButton: Button
    private lateinit var btnCheckout: Button
    private lateinit var btnBack: Button
    private lateinit var sa: SimpleAdapter
    private lateinit var listView: ListView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart_buy_medicine)

        // Initialize UI elements
        dateButton = findViewById(R.id.buttonCartBMDatePicker)
        btnCheckout = findViewById(R.id.buttonBMCheckout)
        btnBack = findViewById(R.id.CLbackBMbutton)
        tvTotal = findViewById(R.id.textViewCartBMTotalPrice)
        listView = findViewById(R.id.listViewBMCart)

        // Initialize Firebase Realtime com.example.healthcareapp.database.Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("packages")
        list = ArrayList()

        // Fetch username from SharedPreferences
        val sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()

        // Fetch data from Firebase Realtime com.example.healthcareapp.database.Database
        databaseReference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalPrice = 0.0f

                // Iterate through each child node and extract product details
                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.child("product").getValue(String::class.java)
                    val price = snapshot.child("price").getValue(Float::class.java)
                    if (product != null && price != null) {
                        item = HashMap()
                        item["Line1"] = product
                        item["Line2"] = "Cost: $price/-"
                        list.add(item)
                        totalPrice += price
                    }
                }

                // Update total price text view
                tvTotal.text = "Total Cost: $totalPrice"
                sa.notifyDataSetChanged() // Notify adapter of data change
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        // Setup adapter for list view
        sa = SimpleAdapter(
            this, list,
            R.layout.multi_lines,
            arrayOf("Line1", "Line2", "Line3", "Line4", "Line5"),
            intArrayOf(R.id.Line_a, R.id.Line_b, R.id.Line_c, R.id.Line_d, R.id.Line_e)
        )
        listView.adapter = sa

        // Setup date picker dialog
        initDatePicker()
        dateButton.setOnClickListener {
            datePickerDialog.show()
        }

        // Button click listeners
        btnBack.setOnClickListener {
            startActivity(Intent(this@CartBuyMedicineActivity, BuyMedicineActivity::class.java))
        }

        btnCheckout.setOnClickListener {
            val intent = Intent(this@CartBuyMedicineActivity, BuyMedicineBookActivity::class.java)
            intent.putExtra("price", tvTotal.text)
            intent.putExtra("date", dateButton.text)
            startActivity(intent)
        }

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Initialize date picker dialog
    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val adjustedMonth = month + 1
            val selectedDate = "$day/$adjustedMonth/$year"
            dateButton.text = selectedDate
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(this, dateSetListener, year, month, day)
        datePickerDialog.datePicker.minDate = cal.timeInMillis + 86400000
    }
}

