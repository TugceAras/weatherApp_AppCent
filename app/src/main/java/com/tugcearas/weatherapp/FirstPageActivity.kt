package com.tugcearas.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class FirstPageActivity : AppCompatActivity() {

    // splash screen
    private val firstScreenTimeOut :Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        Handler().postDelayed({
            startActivity(Intent(this,MapsActivity::class.java))
            finish()

        },firstScreenTimeOut)

    }

}
