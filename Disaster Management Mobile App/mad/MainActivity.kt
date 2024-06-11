package com.example.mad3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    private val disasterFragment = DisasterFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button =findViewById(R.id.bt)
        val button2: Button =findViewById(R.id.btaid)

        button.setOnClickListener {
            loadDisaster()
        }

        button2.setOnClickListener {
            loadAid() // Call the function to load data
        }
    }
    private fun loadDisaster(){
        val fragment = supportFragmentManager.findFragmentById(R.id.fr)
        if (fragment == null){

            supportFragmentManager.beginTransaction().add(R.id.fr,disasterFragment).commit()
        }else{

            supportFragmentManager.beginTransaction().replace(R.id.fr,disasterFragment).commit()
        }
    }

    private fun loadAid() {
        // Create an explicit intent to navigate to the activity where you want to load data
        val intent = Intent(this, FirstaidActivity::class.java)

        // You can also pass any additional data with the intent if needed
        // intent.putExtra("key", "value")

        // Start the new activity
        startActivity(intent)
    }

}