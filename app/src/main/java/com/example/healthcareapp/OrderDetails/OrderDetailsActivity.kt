package com.example.healthcareapp.OrderDetails

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthcareapp.Dataclass.Order
import com.example.healthcareapp.HomeActivity.HomeActivity
import com.example.healthcareapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList
import java.util.HashMap

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var item: HashMap<String, String>
    private lateinit var sa: SimpleAdapter
    private lateinit var lst: ListView
    private lateinit var btn: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details)

        btn = findViewById(R.id.buttonODBack)
        lst = findViewById(R.id.listViewOD)

        btn.setOnClickListener {
            startActivity(Intent(this@OrderDetailsActivity, HomeActivity::class.java))
        }

        list = ArrayList()
        val sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("orders").child(username)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (snapshot in dataSnapshot.children) {
                    val order = snapshot.getValue(Order::class.java)
                    order?.let {
                        val item = HashMap<String, String>()
                        item["Line1"] = it.orderName
                        item["Line2"] = it.orderType
                        item["Line3"] = "Rs. ${it.orderAmount}"
                        item["Line4"] = "Del: ${it.orderDelivery}"
                        item["Line5"] = it.orderDetails
                        list.add(item)
                    }
                }
                sa.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })

        sa = SimpleAdapter(
            this, list,
            R.layout.multi_lines,
            arrayOf("Line1", "Line2", "Line3", "Line4", "Line5"),
            intArrayOf(R.id.Line_a, R.id.Line_b, R.id.Line_c, R.id.Line_d, R.id.Line_e)
        )
        lst.adapter = sa

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
