package com.example.healthcareapp.DoctorActivity

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
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DoctorDetailsActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private lateinit var btn: Button
    private lateinit var doctorDetails: Array<Array<String>>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor_details)

        tv = findViewById(R.id.titleDoctorDetails)
        btn = findViewById(R.id.buttonDDBack)

        val intent = intent
        val title = intent.getStringExtra("title")
        tv.text = title

        /*doctorDetails = when (title) {
            "Family Physician" -> doctorDetail0
            "Dietitian" -> doctorDetail1
            "Dentist" -> doctorDetail2
            "Surgeon" -> doctorDetail3
            "Cardiologist" -> doctorDetail4
            else -> emptyArray() // Provide a default value or handle other cases if needed
        }*/
        doctorDetails = when (title) {
            "Family Physicians" -> doctorDetail0 // Changed from doctorDetail1 to doctorDetail0
            "Dietitian" -> doctorDetail1
            "Dentist" -> doctorDetail2
            "Surgeon" -> doctorDetail3
            "Cardiologists" -> doctorDetail4 // Changed from doctorDetail5 to doctorDetail4
            else -> emptyArray()
        }


        btn.setOnClickListener {
            startActivity(Intent(this@DoctorDetailsActivity, FindDoctorActivity::class.java))
        }

        list = ArrayList()
        for (i in doctorDetails.indices) {
            val item = HashMap<String, String>()
            item["Line1"] = doctorDetails[i][0]
            item["Line2"] = doctorDetails[i][1]
            item["Line3"] = doctorDetails[i][2]
            item["Line4"] = doctorDetails[i][3]
            item["Line5"] = "Consultant Fees: ${doctorDetails[i][4]}/-"
            list.add(item)
        }

        sa = SimpleAdapter(
            this, list,
            R.layout.multi_lines,
            arrayOf("Line1", "Line2", "Line3", "Line4", "Line5"),
            intArrayOf(R.id.Line_a, R.id.Line_b, R.id.Line_c, R.id.Line_d, R.id.Line_e)
        )
        val lst = findViewById<ListView>(R.id.listViewDD)
        lst.adapter = sa
        lst.setOnItemClickListener { adapterView, view, i, l ->
            val it = Intent(this@DoctorDetailsActivity, BookApointmentActivity::class.java)
            it.putExtra("text1", title)
            it.putExtra("text2", doctorDetails[i][0])
            it.putExtra("text3", doctorDetails[i][1])
            it.putExtra("text4", doctorDetails[i][3])
            it.putExtra("text5", doctorDetails[i][4])
            startActivity(it)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private val doctorDetail0 = arrayOf(
            arrayOf(
                "Doctor Name: Nolesh Borate",
                "Hospital Address: Pimpri",
                "Experience: 5 years",
                "Mobile No.: 9324194685",
                "Fee: 1500"
            ),
            arrayOf(
                "Doctor Name: John Doe",
                "Hospital Address: Downtown",
                "Experience: 10 years",
                "Mobile No.: 9876543210",
                "Fee: 2000"
            ),
            arrayOf(
                "Doctor Name: Jane Smith",
                "Hospital Address: Main Street",
                "Experience: 8 years",
                "Mobile No.: 1234567890",
                "Fee: 1800"
            ),
            arrayOf(
                "Doctor Name: David Lee",
                "Hospital Address: City Hospital",
                "Experience: 15 years",
                "Mobile No.: 8765432109",
                "Fee: 2500"
            ),
            arrayOf(
                "Doctor Name: Emily Davis",
                "Hospital Address: County Hospital",
                "Experience: 12 years",
                "Mobile No.: 6789012345",
                "Fee: 2200"
            )
        )
        private val doctorDetail1 = arrayOf(
            arrayOf(
                "Doctor Name: Michael Johnson",
                "Hospital Address: Oakwood Clinic",
                "Experience: 7 years",
                "Mobile No.: 7890123456",
                "Fee: 1900"
            ),
            arrayOf(
                "Doctor Name: Sarah Patel",
                "Hospital Address: Riverside Hospital",
                "Experience: 9 years",
                "Mobile No.: 3456789012",
                "Fee: 2100"
            ),
            arrayOf(
                "Doctor Name: Daniel Brown",
                "Hospital Address: Hilltop Medical Center",
                "Experience: 11 years",
                "Mobile No.: 9012345678",
                "Fee: 2300"
            ),
            arrayOf(
                "Doctor Name: Olivia Garcia",
                "Hospital Address: Parkview Clinic",
                "Experience: 6 years",
                "Mobile No.: 2345678901",
                "Fee: 1700"
            ),
            arrayOf(
                "Doctor Name: Ethan Nguyen",
                "Hospital Address: Lakeside Hospital",
                "Experience: 14 years",
                "Mobile No.: 4567890123",
                "Fee: 2600"
            )
        )

        private val doctorDetail2 = arrayOf(
            arrayOf(
                "Doctor Name: Jessica Miller",
                "Hospital Address: Sunset Medical Center",
                "Experience: 8 years",
                "Mobile No.: 7890123456",
                "Fee: 2000"
            ),
            arrayOf(
                "Doctor Name: Ryan Wilson",
                "Hospital Address: City Clinic",
                "Experience: 12 years",
                "Mobile No.: 3456789012",
                "Fee: 2200"
            ),
            arrayOf(
                "Doctor Name: Lauren Thompson",
                "Hospital Address: Hilltop General Hospital",
                "Experience: 10 years",
                "Mobile No.: 9012345678",
                "Fee: 2400"
            ),
            arrayOf(
                "Doctor Name: Ethan Turner",
                "Hospital Address: Lakeview Medical Center",
                "Experience: 7 years",
                "Mobile No.: 2345678901",
                "Fee: 1800"
            ),
            arrayOf(
                "Doctor Name: Chloe Baker",
                "Hospital Address: Green Valley Clinic",
                "Experience: 13 years",
                "Mobile No.: 4567890123",
                "Fee: 2500"
            )
        )

        private val doctorDetail3 = arrayOf(
            arrayOf(
                "Doctor Name: Jessica Miller",
                "Hospital Address: Sunset Medical Center",
                "Experience: 8 years",
                "Mobile No.: 7890123456",
                "Fee: 2000"
            ),
            arrayOf(
                "Doctor Name: Ryan Wilson",
                "Hospital Address: City Clinic",
                "Experience: 12 years",
                "Mobile No.: 3456789012",
                "Fee: 2200"
            ),
            arrayOf(
                "Doctor Name: Lauren Thompson",
                "Hospital Address: Hilltop General Hospital",
                "Experience: 10 years",
                "Mobile No.: 9012345678",
                "Fee: 2400"
            ),
            arrayOf(
                "Doctor Name: Ethan Turner",
                "Hospital Address: Lakeview Medical Center",
                "Experience: 7 years",
                "Mobile No.: 2345678901",
                "Fee: 1800"
            ),
            arrayOf(
                "Doctor Name: Chloe Baker",
                "Hospital Address: Green Valley Clinic",
                "Experience: 13 years",
                "Mobile No.: 4567890123",
                "Fee: 2500"
            )
        )

        private val doctorDetail4 = arrayOf(
            arrayOf(
                "Doctor Name: Alexander Clark",
                "Hospital Address: Maple Leaf Hospital",
                "Experience: 9 years",
                "Mobile No.: 7890123456",
                "Fee: 2100"
            ),
            arrayOf(
                "Doctor Name: Sophia Adams",
                "Hospital Address: Riverfront Clinic",
                "Experience: 11 years",
                "Mobile No.: 3456789012",
                "Fee: 2300"
            ),
            arrayOf(
                "Doctor Name: Noah Evans",
                "Hospital Address: Central Hospital",
                "Experience: 8 years",
                "Mobile No.: 9012345678",
                "Fee: 2200"
            ),
            arrayOf(
                "Doctor Name: Mia White",
                "Hospital Address: Skyline Medical Center",
                "Experience: 10 years",
                "Mobile No.: 2345678901",
                "Fee: 2000"
            ),
            arrayOf(
                "Doctor Name: James Parker",
                "Hospital Address: Lakeshore Hospital",
                "Experience: 12 years",
                "Mobile No.: 4567890123",
                "Fee: 2400"
            )
        )
    }
}
