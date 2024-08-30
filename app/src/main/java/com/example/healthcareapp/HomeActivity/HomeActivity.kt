package com.example.healthcareapp.HomeActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.SignInUPActivity.LoginActivity
import com.example.healthcareapp.DoctorActivity.FindDoctorActivity
import com.example.healthcareapp.HealthArticle.HealthArticleActivity
import com.example.healthcareapp.LabActivity.LabTestActivity
import com.example.healthcareapp.MedicineActivity.BuyMedicineActivity
import com.example.healthcareapp.OrderDetails.OrderDetailsActivity
import com.example.healthcareapp.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        val sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        Toast.makeText(applicationContext, "WELCOME $username", Toast.LENGTH_SHORT).show()

        val exit = findViewById<CardView>(R.id.ExitCard)
        exit.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }

        val findDoctor = findViewById<CardView>(R.id.cardFindDoctor)
        findDoctor.setOnClickListener {
            startActivity(Intent(this@HomeActivity, FindDoctorActivity::class.java))
        }

        val labTest = findViewById<CardView>(R.id.cardLabTest)
        labTest.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LabTestActivity::class.java))
        }

        val orderDetails = findViewById<CardView>(R.id.cardOrderDetails)
        orderDetails.setOnClickListener {
            startActivity(Intent(this@HomeActivity, OrderDetailsActivity::class.java))
        }

        val buyMedicine = findViewById<CardView>(R.id.cardBuyMedicine)
        buyMedicine.setOnClickListener {
            startActivity(Intent(this@HomeActivity, BuyMedicineActivity::class.java))
        }

        val health = findViewById<CardView>(R.id.cardHealthDoctor)
        health.setOnClickListener {
            startActivity(Intent(this@HomeActivity, HealthArticleActivity::class.java))
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}