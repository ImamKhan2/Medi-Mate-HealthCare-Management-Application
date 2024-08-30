package com.example.healthcareapp.HealthArticle

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.HomeActivity.HomeActivity
import com.example.healthcareapp.R

class HealthArticleActivity : AppCompatActivity() {

    private val healthDetails = arrayOf(
        arrayOf("Walking Daily", "", "", "", "Click More Details"),
        arrayOf("Home care of COVID-19", "", "", "", "Click More Details"),
        arrayOf("Stop Smoking", "", "", "", "Click More Details"),
        arrayOf("Menstrual Cramps", "", "", "", "Click More Details"),
        arrayOf("Healthy Gut", "", "", "", "Click More Details")
    )

    private val images = intArrayOf(
        R.drawable.health1,
        R.drawable.health2,
        R.drawable.health3,
        R.drawable.health4,
        R.drawable.health5
    )

    private lateinit var btnBack: Button
    private lateinit var HealthArticle: Array<Array<String>>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter
    private lateinit var lst: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_health_article)

        lst = findViewById(R.id.listViewHA)
        btnBack = findViewById(R.id.buttonHABack)

        btnBack.setOnClickListener {
            startActivity(Intent(this@HealthArticleActivity, HomeActivity::class.java))
        }

        list = ArrayList()
        for (i in healthDetails.indices) {
            val item = HashMap<String, String>()
            item["Line1"] = healthDetails[i][0]
            item["Line2"] = healthDetails[i][1]
            item["Line3"] = healthDetails[i][2]
            item["Line4"] = healthDetails[i][3]
            item["Line5"] = healthDetails[i][4]
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
            val it = Intent(this@HealthArticleActivity, HealthArticleDetailsActivity::class.java)
            it.putExtra("text1", HealthArticle[i][0])
            it.putExtra("text2", images[i])
            startActivity(it)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
