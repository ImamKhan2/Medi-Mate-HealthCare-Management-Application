package com.example.healthcareapp.DoctorActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.HomeActivity.HomeActivity
import com.example.healthcareapp.R

class FindDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_find_doctor)

        val exit = findViewById<CardView>(R.id.cardFDBack)
        exit.setOnClickListener {
            startActivity(Intent(this@FindDoctorActivity, HomeActivity::class.java))
        }

        val familyPhysician = findViewById<CardView>(R.id.cardFDFamilyPhysician)
        familyPhysician.setOnClickListener {
            val intent = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            intent.putExtra("title", "Family Physicians")
            startActivity(intent)
        }

        val dietitian = findViewById<CardView>(R.id.cardFDDietitian)
        dietitian.setOnClickListener {
            val intent = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            intent.putExtra("title", "Dietitian")
            startActivity(intent)
        }

        val dentist = findViewById<CardView>(R.id.cardFDDentist)
        dentist.setOnClickListener {
            val intent = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            intent.putExtra("title", "Dentist")
            startActivity(intent)
        }

        val surgeon = findViewById<CardView>(R.id.cardFDSurgeon)
        surgeon.setOnClickListener {
            val intent = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            intent.putExtra("title", "Surgeon")
            startActivity(intent)
        }
        val cardiologist = findViewById<CardView>(R.id.cardFDCardiologists)
        cardiologist.setOnClickListener {
            val intent = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            intent.putExtra("title", "Cardiologists")
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}