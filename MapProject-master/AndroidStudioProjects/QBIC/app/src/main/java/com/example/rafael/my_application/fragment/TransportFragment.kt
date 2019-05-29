package com.example.rafael.my_application.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

import com.example.rafael.my_application.R
import kotlinx.android.synthetic.main.fragment_bnutyun.*
import kotlinx.android.synthetic.main.fragment_transport.*


class TransportFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_transport, container, false)
        var mylist=view.findViewById(R.id.listView_transport) as ListView
        var arrNkarner:ArrayList<Nkarner>  = ArrayList()
        arrNkarner.add(Nkarner("Մայրաքաղաքի բնակչության տրանսպորտային սպասարկումն իրականացվում է ավտոբուսներով," +
                " տրոլեյբուսներով, միկրոավտոբուսներով և մետրոպոլիտենով: ",R.drawable.transport))
        arrNkarner.add(Nkarner("«Մայրաքաղաքի ավտոբուսային հավաքակազմում առկա է 667 ավտոբուս,"+
                "Ավտոբուսային երթուղիները պայմանագրային հիմունքներով սպասարկում են «Երևանի ավտոբուս» ՓԲԸ-ն։",R.drawable.transport2))
        arrNkarner.add(Nkarner("Երևանում փաստացի գործում է 68 միկրոավտոբուսային երթուղի, որոնք համալրված են " +
                "տարբեր մակնիշների մոտ 1220 միավոր շարժակազմով։",R.drawable.transport3))
        arrNkarner.add(Nkarner("Մայրաքաղաքի բնակչության տրանսպորտային սպասարկումը հիմնականում միկրոավտոբուսներով անընդունելի է, հետևաբար միկրոավտոբուսների" +
                " փոխարինումը ավտոբուսներով այլընտրանք չունի:",R.drawable.transport4))



        mylist.adapter= context?.let { CustomAdapter(it,arrNkarner) }
        return view


    }


    class CustomAdapter(var context: Context, var images:ArrayList<Nkarner>): BaseAdapter(){

        private class ViewHolder(row: View?){
            var textName: TextView
            var ivImage: ImageView
            init {
                this.textName=row?.findViewById(R.id.transport_text) as TextView
                this.ivImage=row?.findViewById(R.id.transport_imageView) as ImageView
            }

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view:View?
            var viewHolder: ViewHolder
            if (convertView==null){
                var layout=LayoutInflater.from(context)
                view=layout.inflate(R.layout.trasport_item_list,parent,false)
                viewHolder=ViewHolder(view)
                view.tag=viewHolder
            }
            else{
                view=convertView
                viewHolder=view.tag as ViewHolder

            }
            var images:Nkarner=getItem(position) as Nkarner
            viewHolder.textName.text=images.name
            viewHolder.ivImage.setImageResource(images.image)
            return view as View
        }

        override fun getItem(position: Int): Any {
            return images.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return images.count()
        }

    }
    data  class Nkarner(var name: String,var image:Int){

    }


}
