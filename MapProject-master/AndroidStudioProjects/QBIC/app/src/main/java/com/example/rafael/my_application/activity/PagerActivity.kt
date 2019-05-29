package com.example.rafael.my_application.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.DialogTitle
import android.util.AttributeSet
import android.view.View
import com.example.rafael.my_application.R
import com.example.rafael.my_application.fragment.*
import kotlinx.android.synthetic.main.activity_pager.*
import kotlinx.android.synthetic.main.fragment_axbahanutyun.*
import java.text.FieldPosition

class PagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        val adapter= MyViewPager(supportFragmentManager)
        adapter.addFragment(AxbahanutyunFragment(),"Աղբ.")
        adapter.addFragment(NstaranFragment(),"Նստ.")
        adapter.addFragment(BnutyunFragment(),"Բնու.")
        adapter.addFragment(LarerFragment(),"Կապ.")
        adapter.addFragment(TransportFragment(),"Տրա.")
        adapter.addFragment(KomunalFragment(),"Կոմ.")
        viewPager.adapter=adapter
        tabs.setupWithViewPager(viewPager)

    }
    class MyViewPager(manager : FragmentManager):FragmentPagerAdapter(manager){
        private val fragmentList:MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }
    fun addFragment(fragment: Fragment,title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
