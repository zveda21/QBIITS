package com.example.rafael.my_application.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.rafael.my_application.R
import com.example.rafael.my_application.adapters.UsersRecyclerAdapter
import com.example.rafael.my_application.db.DatabaseHelper
import com.example.rafael.my_application.model.User

class UsersListActivity : AppCompatActivity() {
    private val activity=this@UsersListActivity
    private lateinit var  textViewName:AppCompatTextView
    private lateinit var recyclerViewUsers:RecyclerView
    private lateinit var listUsers:MutableList<User>
    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter
    private lateinit var databaseHelper:DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
      // supportActionBar!!.title = " "
        initViews()
        initObjects()

    }

    private fun initViews(){
        textViewName = findViewById<View>(R.id.textViewName) as AppCompatTextView
        recyclerViewUsers = findViewById<View>(R.id.recyclerViewUsers) as RecyclerView
    }

    private fun initObjects(){

        listUsers=ArrayList()
        usersRecyclerAdapter=UsersRecyclerAdapter(listUsers)

        val mLayoutManager=LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager=mLayoutManager
        recyclerViewUsers.itemAnimator=DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter=usersRecyclerAdapter
        databaseHelper=DatabaseHelper(activity)

        val emailFromIntent=intent.getStringExtra("EMAIL")
        textViewName.text=emailFromIntent

        var getDataFromSQLite=GetDataFromSQLite()
        getDataFromSQLite.execute()



    }

    inner class GetDataFromSQLite : AsyncTask<Void, Void, List<User>>() {

        override fun doInBackground(vararg p0: Void?): List<User> {
            return databaseHelper.getAllUser()
        }

        override fun onPostExecute(result: List<User>?) {
            super.onPostExecute(result)
            listUsers.clear()
            listUsers.addAll(result!!)
        }

    }
}
