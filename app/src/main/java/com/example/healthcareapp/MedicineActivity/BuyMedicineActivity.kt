package com.example.healthcareapp.MedicineActivity

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BuyMedicineActivity : AppCompatActivity() {
    private val packages = arrayOf(
        arrayOf("Maris-03 10001 Capse", "", "", "", "50"),
        arrayOf("Healthvit Chromium Picolinate 200mcg Capsule", "", "", "", "305"),
        arrayOf("Vitamin B Complex Capsules", "", "", "", "448"),
        arrayOf("Inlife Vitamin E Wheat Germ Oil Capsule", "", "", "", "539"),
        arrayOf("Dole 650 Tablat", "", "", "", "30"),
        arrayOf("Crocin 650 Advance Tablet", "", "", "", "58"),
        arrayOf("Strepsils Medicated Lozenges for Sore Throat", "", "", "", "0"),
        arrayOf("Tata Ing Calcium Vitamin D3", "", "", "", "30"),
        arrayOf("Feronia -XT Tablet", "", "", "", "130")
    )

    private val packageDetails = arrayOf(
        "Building and keeping the bones & teeth strong\n" +
                "Reducing Fatigue/stress and muscular pains\n" +
                "Boosting immunity and increasing resistance against infection",
        "Chromium is an essential trace mineral that plays an important role in helping insulin regulate\n" +
                "Provides relief from vitamin & deficiencies\n" +
                "Helps in formation of red blood cells\n" +
                "Maintains healthy nervous system",
        "It promotes health as well as skin benefit.\n" +
                "It helps reduce skin blemish and pigmentation.\n" +
                "It acts as safeguard the skin from the harsh UVA and UVB sun rays.",
        "Dolo 650 Tablet helps relieve pain and fever by blocking the release of certain chemical\n" +
                "Helps relieve fever and bring down a high temperature\n" +
                "Suitable for people with a heart condition or high blood pressure",
        "Relieves the symptoms of a bacterial throat infection and soothes the recovery process\n" +
                "Provides a warm and comforting feeling during sore throat",
        "Reduces the risk of calcium deficiency, Rickets, and Osteoporosis\n" +
                "Promotes mobility and flexibility of joints",
        "Helps to reduce the iron deficiency due to chronic blood loss or low intake of iron"
    )

    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter
    private lateinit var lst: ListView
    private lateinit var btnBack: Button
    private lateinit var btnGoToCart: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buy_medicine)

        btnBack = findViewById(R.id.buttonBackBM)
        btnGoToCart = findViewById(R.id.buttonGTCBM)
        lst = findViewById(R.id.listViewBM)

        btnGoToCart.setOnClickListener {
            startActivity(Intent(this@BuyMedicineActivity, CartBuyMedicineActivity::class.java))
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@BuyMedicineActivity, HomeActivity::class.java))
        }

        list = ArrayList()
        for (i in packages.indices) {
            val item = HashMap<String, String>()
            item["Line1"] = packages[i][0]
            item["Line5"] = "Total Cost: ${packages[i][4]}/-"
            list.add(item)
        }
        sa = SimpleAdapter(
            this, list,
            R.layout.multi_lines,
            arrayOf("Line1", "Line5"),
            intArrayOf(R.id.Line_a, R.id.Line_e)
        )
        lst.adapter = sa

        lst.setOnItemClickListener { adapterView, view, i, l ->
            val it = Intent(this@BuyMedicineActivity, BuyMedicineDetailsActivity::class.java)
            it.putExtra("text1", packages[i][0])
            it.putExtra("text2", packageDetails[i])
            it.putExtra("text3", packages[i][4])
            startActivity(it)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
    }
}
