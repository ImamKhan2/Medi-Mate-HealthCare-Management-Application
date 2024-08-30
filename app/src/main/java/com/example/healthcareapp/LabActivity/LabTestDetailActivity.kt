package com.example.healthcareapp.LabActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.R
import com.example.healthcareapp.databinding.ActivityLabTestDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LabTestDetailActivity : AppCompatActivity() {
    private lateinit var tvPackageName: TextView
    private lateinit var tvTotalCost: TextView
    private lateinit var edDetails: EditText
    private lateinit var btnAddToCart: Button
    private lateinit var btnBack: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var  auth: FirebaseAuth

    private val binding:ActivityLabTestDetailBinding by lazy {
        ActivityLabTestDetailBinding.inflate(layoutInflater)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        tvPackageName = findViewById(R.id.textViewPackageName)
        edDetails = findViewById(R.id.textMultilineLB)
        tvTotalCost = findViewById(R.id.textTotalCost)
        btnAddToCart = findViewById(R.id.atcLabTestbutton)
        btnBack = findViewById(R.id.buttonLBDBack)


        edDetails.keyListener= null

        val intent = getIntent()
        tvPackageName.setText(intent.getStringExtra("text1"))
        edDetails.setText(intent.getStringExtra("text2"))
        tvTotalCost.setText(intent.getStringExtra("text3")+"/-")

        btnBack.setOnClickListener {
            startActivity(Intent(this@LabTestDetailActivity, LabTestActivity::class.java))
        }

        btnAddToCart.setOnClickListener {
            val sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "").toString()
            val product = tvPackageName.text.toString()
            val price = intent.getStringExtra("text3")?.toFloatOrNull() ?: 0.0f
            val sanitizedProductName = product.replace(".", "").replace("#", "").replace("$", "").replace("[", "").replace("]", "")


            val cartRef = FirebaseDatabase.getInstance().getReference("carts")

            cartRef.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.child(product).exists()) {
                        Toast.makeText(applicationContext, "Product Already Added", Toast.LENGTH_SHORT).show()
                    } else {
                        val cartItem = hashMapOf(
                            "username" to username,
                            "product" to sanitizedProductName,
                            "price" to price,
                            "otype" to "Lab"
                        )

                        cartRef.child(username).child(sanitizedProductName).setValue(cartItem)

                            .addOnSuccessListener {
                                Toast.makeText(applicationContext, "Record Inserted to Cart", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@LabTestDetailActivity, LabTestActivity::class.java))
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(applicationContext, "Failed to insert record: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("Firebase", "onCancelled: ${databaseError.toException()}")
                }
            })
        }






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}