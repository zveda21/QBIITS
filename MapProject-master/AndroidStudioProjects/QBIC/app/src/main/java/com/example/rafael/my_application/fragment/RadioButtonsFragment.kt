package com.example.rafael.my_application.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rafael.my_application.R
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_radio_group.*

class RadioButtonsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_radio_group, container, false)
        return view
    }

}
