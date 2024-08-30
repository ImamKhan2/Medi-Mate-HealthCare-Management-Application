package com.example.healthcareapp.MedicineActivity

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
import com.example.healthcareapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BuyMedicineDetailsActivity : AppCompatActivity() {

    private lateinit var tvPackageName: TextView
    private lateinit var tvTotalCost: TextView
    private lateinit var edDetails: EditText
    private lateinit var btnBack: Button
    private lateinit var btnAddToCart: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buy_medicine_details)

        tvPackageName = findViewById(R.id.textViewBMDPackageName)
        tvTotalCost = findViewById(R.id.textViewTotalCostBMD)
        edDetails.keyListener = null
        btnBack = findViewById(R.id.buttonBMDBack)
        btnAddToCart = findViewById(R.id.atcBMDbutton)

        val intent = intent
        tvPackageName.text = intent.getStringExtra("text1")
        edDetails.setText(intent.getStringExtra("text2"))
        tvTotalCost.text = "Total Cost : ${intent.getStringExtra("text3")}/-"

        btnBack.setOnClickListener {
            startActivity(Intent(this@BuyMedicineDetailsActivity, BuyMedicineActivity::class.java))
        }

        btnAddToCart.setOnClickListener {
            val sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "").toString()
            val product = tvPackageName.text.toString()
            val price = intent.getStringExtra("text3")?.toFloatOrNull() ?: 0.0f

            // Initialize Firebase
            auth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().reference

            val cartRef = databaseReference.child("carts").child(username)

            cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.child(product).exists()) {
                        Toast.makeText(applicationContext, "Product Already Added", Toast.LENGTH_SHORT).show()
                    } else {
                        val cartData = mapOf(
                            "product" to product,
                            "price" to price,
                            "otype" to "medicine"
                        )
                        cartRef.child(product).setValue(cartData)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext, "Record Inserted to Cart", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@BuyMedicineDetailsActivity, BuyMedicineActivity::class.java))
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(applicationContext, "Failed to add to cart: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                    Toast.makeText(applicationContext, "com.example.healthcareapp.database.Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
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
