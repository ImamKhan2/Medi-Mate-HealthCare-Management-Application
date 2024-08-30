package com.example.healthcareapp.LabActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.google.firebase.database.*
import java.util.Calendar

class CartLabActivity : AppCompatActivity() {
    private lateinit var item: HashMap<String, String>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var tvTotal: TextView
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var btnCheckout: Button
    private lateinit var btnBack: Button
    private lateinit var listView: ListView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: SimpleAdapter
    private lateinit var packages: Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart_lab)

        dateButton = findViewById(R.id.buttoncartlabDatePicker)
        btnCheckout = findViewById(R.id.buttonCheckout)
        btnBack = findViewById(R.id.CLbackbutton)
        tvTotal = findViewById(R.id.textViewcartTotalPrice)
        listView = findViewById(R.id.listViewCart)

        databaseReference = FirebaseDatabase.getInstance().reference.child("packages")
        list = ArrayList()

        adapter = SimpleAdapter(
            this, list,
            R.layout.multi_lines,
            arrayOf("Line1", "Line2", "Line3", "Line4", "Line5"),
            intArrayOf(R.id.Line_a, R.id.Line_b, R.id.Line_c, R.id.Line_d, R.id.Line_e)
        )
        listView.adapter = adapter

        initDatePicker()
        dateButton.setOnClickListener {
            datePickerDialog.show()
        }

        initTimePicker()
        timeButton.setOnClickListener {
            timePickerDialog.show()
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@CartLabActivity, LabTestActivity::class.java))
        }

        btnCheckout.setOnClickListener {
            val intent = Intent(this@CartLabActivity, LabTestBookActivity::class.java)
            intent.putExtra("price", tvTotal.text)
            intent.putExtra("date", dateButton.text)
            intent.putExtra("time", timeButton.text)
            startActivity(intent)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()

        val database = FirebaseDatabase.getInstance().reference
        database.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalPrice = 0.0f

                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.child("product").getValue(String::class.java)
                    val price = snapshot.child("price").getValue(Float::class.java)
                    if (product != null && price != null) {
                        val item = HashMap<String, String>()
                        item["Line1"] = product
                        item["Line2"] = "Cost: $price/-"
                        list.add(item)
                        totalPrice += price
                    }
                }

                tvTotal.text = "Total Cost: $totalPrice"
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

        list = java.util.ArrayList()
        for (i in packages.indices) {
            val item = java.util.HashMap<String, String>()
            item["Line1"] = packages[i][0]
            item["Line2"] = packages[i][1]
            item["Line3"] = packages[i][2]
            item["Line4"] = packages[i][3]
            item["Line5"] = " Total Cost: ${packages[i][4]}/-"
            list.add(item)
        }
    }

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

    private fun initTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            timeButton.text = "$hourOfDay:$minute"
        }

        val cal = Calendar.getInstance()
        val hrs = cal.get(Calendar.HOUR_OF_DAY)
        val mins = cal.get(Calendar.MINUTE)

        timePickerDialog = TimePickerDialog(this, timeSetListener, hrs, mins, true)
    }
}
