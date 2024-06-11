package com.example.mad3



import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FirstaidActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstaid)


        val button3:Button = findViewById(R.id.btncall)
        val button4: Button = findViewById(R.id.btnsms)
        val button5: Button = findViewById(R.id.btnweb)




        button3.setOnClickListener {
            //implicit intent to open the dialler with a number
            val number = "1990"
            val uri = Uri.parse(String.format("tel:$number"))
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data = uri
            startActivity(intent)
        }
        button4.setOnClickListener {
            //implicit intent to send an sms
            val number = "1990"
            val smsText = "Welcome to MAD 2023"
            val uri = Uri.parse(String.format("smsto:$number"))
            val intent = Intent()
            intent.action = Intent.ACTION_SENDTO
            intent.data = uri
            intent.putExtra("sms_body",smsText)
            startActivity(intent)
        }
        button5.setOnClickListener {
            val url = "https://www.google.com/maps"
            val uri = Uri.parse(url)
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW;
            intent.data = uri
            startActivity(intent)
        }

    }
}