package com.example.rafael.my_application.activity

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.View
import com.example.rafael.my_application.R
import kotlinx.android.synthetic.main.activity_sigin_or_login.*
import java.lang.Exception

class SiginOrLoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_or_login)
        loginbt.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        signinbt.setOnClickListener {
            val intent=Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }



    }


    override fun onBackPressed() {
        try {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setMessage(
                        Html.fromHtml(
                            "<font color='#FFFFFF'>ԴՈՒՐՍ ԳԱԼ</font>",
                            Html.FROM_HTML_MODE_LEGACY
                        )
                    )
                } else {
                    builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>Are You sure You want to exit?</font>"))
                }
                builder.setPositiveButton("Այո") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                  //  val intent=Intent(this,SiginOrLoginActivity::class.java)
                  //  startActivity(intent)
                }
                builder.setNegativeButton("Ոչ") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()

        } catch (e: Exception){
            e.printStackTrace()
        }
    }


}
