package com.example.rafael.my_application.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.rafael.my_application.R
import com.example.rafael.my_application.db.DatabaseHelper
import kotlinx.android.synthetic.main.activity_login.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val sharedPref = getSharedPreferences("login_data", Context.MODE_PRIVATE)
            val email = sharedPref.getString("email","")!!

            val intent =
                if(email != ""){
//                if (DatabaseHelper(this).checkUser(email, password)) {
                   Intent(this@SplashActivity, GoogleMapsActivity::class.java)
//                }
            } else {
               Intent(this, SiginOrLoginActivity::class.java)
            }
            if(email != "") {
                intent.putExtra("EMAIL", email)
            }
            startActivity(intent)
            finish()
        }, 2000)
    }
}