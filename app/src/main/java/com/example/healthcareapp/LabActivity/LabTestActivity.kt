package com.example.healthcareapp.LabActivity

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
import java.util.ArrayList
import java.util.HashMap

class LabTestActivity : AppCompatActivity() {

    private val packages = arrayOf(
        arrayOf("Package 1 : Full Body Checkup", "", "", "", "999"),
        arrayOf("Package 2 : Blood Glucose Fasting", "", "", "", "999"),
        arrayOf("Package 3 : COVID-19 Antibody - IgG", "", "", "", "999"),
        arrayOf("Package 4 : Thyroid Check", "", "", "", "999"),
        arrayOf("Package 5 : Immunity Check", "", "", "", "999")
    )

    private val packageDetails = arrayOf(
        "Blood Glucose Fasting\n" +
                "Complete Hemogram\n" +
                "HbAlc\n" +
                "Iron Studies\n" +
                "Kidney Function Test\n" +
                "LSH Lactate Dehydrogenase Serum\n" +
                "Lipid Profile\n" +
                "Liver Function Test",
        "Blood Glucose Fasting",
        "COVID-19 Antibody - IgG",
        "Thyroid Progile-Total (T3,T4 & TSH Ultra-sensitive)",
        "Complete Hemogram\n" +
                "CRP (C Reactive Protein) Quantitative, Serum\n" +
                "Iron Studies\n" +
                "KIdney Function Test\n"+
                "Vitamin D Total-25 Hydroxt\n" +
                "Liver Function Test\n" +
                "Lipid Profile"
    )

    private lateinit var item: HashMap<String, String>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter
    private lateinit var gtcLabTestbutton: Button
    private lateinit var buttonLBBack: Button
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lab_test)

        gtcLabTestbutton = findViewById(R.id.gtcLabTestbutton)
        buttonLBBack = findViewById(R.id.buttonLBBack)
        listView = findViewById(R.id.listViewLB)

        buttonLBBack.setOnClickListener {
            startActivity(Intent(this@LabTestActivity, HomeActivity::class.java))
        }

        list = ArrayList()
        for (i in packages.indices) {
            val item = HashMap<String, String>()
            item["Line1"] = packages[i][0]
            item["Line2"] = packages[i][1]
            item["Line3"] = packages[i][2]
            item["Line4"] = packages[i][3]
            item["Line5"] = " Total Cost: ${packages[i][4]}/-"
            list.add(item)
        }

        sa = SimpleAdapter(
            this, list,
            R.layout.multi_lines,
            arrayOf("Line1", "Line2", "Line3", "Line4", "Line5"),
            intArrayOf(R.id.Line_a, R.id.Line_b, R.id.Line_c, R.id.Line_d, R.id.Line_e)
        )
        listView.adapter = sa

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val it = Intent(this@LabTestActivity, LabTestDetailActivity::class.java)
            it.putExtra("text1", packages[i][0])
            it.putExtra("text2", packageDetails[i])
            it.putExtra("text3", packages[i][4])
            startActivity(it)
        }

        gtcLabTestbutton.setOnClickListener {
            startActivity(Intent(this@LabTestActivity, CartLabActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
