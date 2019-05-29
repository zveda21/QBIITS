package com.example.rafael.my_application.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.rafael.my_application.R
import kotlinx.android.synthetic.main.activity_comment.*
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.rafael.my_application.db.DatabaseHelper
import kotlinx.android.synthetic.main.activity_google_maps.*
import kotlinx.android.synthetic.main.header.view.*
import org.w3c.dom.Comment
import java.util.ArrayList

class CommentActivity : AppCompatActivity() {
    internal var listComment:List<Comment> = ArrayList<Comment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rafael.my_application.R.layout.activity_comment)
        showUserName()
        val listView: ListView = findViewById(R.id.listviewComment)
        var itemlist = arrayListOf<String>()
        addcomment.setOnClickListener {
            itemlist.add(editUserName.text.toString()+"\n"+editComment.text.toString())

            listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemlist)
            editComment!!.text=null
        }
        listView.setOnItemClickListener{adapterView,view,i,l->
            Toast.makeText(this,"Name"+itemlist.get(i), Toast.LENGTH_SHORT).show()
            //  getSharedPref()
        }
    }

    private fun showUserName(){
        val sharedPref1 = getSharedPreferences("sign_in", Context.MODE_PRIVATE)
        // val email = sharedPref.getString("email","")!!
        val name=sharedPref1.getString("name"," ")!!

        editUserName.setText( name)
    }


/*
          db= DBHelper(this)
        refreshData()
        buttonadd.setOnClickListener {
            val comm= Comment(
                Integer.parseInt(et_id.text.toString()),
                et1.text.toString(),
                et2.text.toString()
            )
            db.addComment(comm)
            et_id.text=null
            et1.text=null
            et2.text=null
            refreshData()

        }

    }
    private fun refreshData() {
        listComment=db.allComment
        val adapter= ListCommentAdapter(this@CommentActivity,listComment,et_id,et1,et2)
        list_person.adapter=adapter
    }*/
}
