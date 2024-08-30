package com.example.healthcareapp.HealthArticle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.R

class HealthArticleDetailsActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private lateinit var img: ImageView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_health_article_details)

        btnBack = findViewById(R.id.buttonHADBack)
        img = findViewById(R.id.imageViewHAD)
        tv = findViewById(R.id.textViewHADTilte)

        val intent: Intent = intent
        tv.text = intent.getStringExtra("text1")

        val bundle: Bundle? = intent.extras
        bundle?.let {
            val resId: Int = it.getInt("text2")
            img.setImageResource(resId)
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@HealthArticleDetailsActivity, HealthArticleActivity::class.java))
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}