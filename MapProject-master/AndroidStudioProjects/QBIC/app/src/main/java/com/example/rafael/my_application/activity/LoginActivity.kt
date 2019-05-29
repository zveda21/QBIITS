package com.example.rafael.my_application.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.EditText
import com.example.rafael.my_application.R
import com.example.rafael.my_application.db.DatabaseHelper
import com.example.rafael.my_application.helpers.InputValidation
import kotlinx.android.synthetic.main.activity_login.*
    class LoginActivity : AppCompatActivity(),View.OnClickListener  {

        private val activity=this@LoginActivity
        private lateinit var nestedScrollView: NestedScrollView
        private lateinit var textInputEditTextEmail: TextInputEditText
        private lateinit var textInputEditTextPassword: TextInputEditText

        private lateinit var textInputLayoutEmail: TextInputLayout
        private lateinit var textInputLayoutPassword: TextInputLayout
        private lateinit var appCompatButtonLogin: AppCompatButton
        private lateinit var textViewLinkRegister: AppCompatTextView
        private lateinit var inputValidation: InputValidation
        private lateinit var databaseHelper: DatabaseHelper

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            //   supportActionBar!!.hide()
            initViews()
            initListeners()
            initObjects()



        }

        private fun initViews() {

            nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView

            textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
            textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
            textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
            textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText

            appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as AppCompatButton

            textViewLinkRegister = findViewById<View>(R.id.textViewLinkRegister) as AppCompatTextView

        }
        private fun initListeners() {

            appCompatButtonLogin!!.setOnClickListener(this)
            textViewLinkRegister!!.setOnClickListener(this)
        }
        private fun initObjects() {

            databaseHelper = DatabaseHelper(activity)
            inputValidation = InputValidation(activity)

        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.appCompatButtonLogin -> verifyFromSQLite()
                R.id.textViewLinkRegister -> {

                    val intentRegister = Intent(applicationContext, SigninActivity::class.java)
                    startActivity(intentRegister)
                }
            }
        }
        private fun verifyFromSQLite() {

            if (!inputValidation!!.isInputEditTextFilled (textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_noemail))) {
                return
            }
            if (!inputValidation!!.isInputEditTextEmail (textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
                return
            }
            if (!inputValidation!!.isInputEditTextFilled (textInputEditTextPassword!!, textInputLayoutPassword!!, getString(R.string.error_message_nopassword))) {
                return
            }

            if (databaseHelper!!.checkUser(textInputEditTextEmail!!.text.toString().trim { it <= ' ' }, textInputEditTextPassword!!.text.toString().trim { it <= ' ' })) {

                val sharedPref = getSharedPreferences("login_data", Context.MODE_PRIVATE).edit()
                sharedPref.putString("email",textInputEditTextEmail!!.text.toString())
                    .putString("password",textInputEditTextPassword!!.text.toString()).apply()

                val accountsIntent = Intent(activity, GoogleMapsActivity::class.java)
                accountsIntent.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
                emptyInputEditText()
                startActivity(accountsIntent)


            } else {


                Snackbar.make(nestedScrollView!!, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
            }
        }


        private fun emptyInputEditText() {
            textInputEditTextEmail!!.text = null
            textInputEditTextPassword!!.text = null
        }


    }
