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

class KomunalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_komunal, container, false)
        var mylist=view.findViewById(R.id.listView_komunal) as ListView
        var arrNkarner:ArrayList<Nkarner>  = ArrayList()
        arrNkarner.add(Nkarner(" Երևան քաղաքի խմելու ջրի մատակարարումը և ջրահեռացումն իրականացվում է " +
                "«Վեոլիա Ջուր» ՓԲԸ-ի կողմից: ",R.drawable.komunal))
        arrNkarner.add(Nkarner("«Բաժնի և «օպերատիվ շտաբի» աշխատակիցներն ամենօրյա հսկողություն են իրականացրել սանիտարական մաքրման ընկերությունների " +
                "կողմից մայրաքաղաքի փողոցների, մայթերի սանիտարական մաքրման։",R.drawable.komunal2))
        arrNkarner.add(Nkarner("2018թ. ՊԿԱ ծրագրի շրջանակում իրականացվել է Մաշտոցի պողոտայի նոր հեղեղատար համակարգի (մոտ 510 գծմ) կառուցումը:",R.drawable.komunal3))
        arrNkarner.add(Nkarner("Բաժնի մասնագետներն ամեն օր ուսումնասիրում, քննարկում և պատասխանում են բազմաթիվ գրությունների," +
                "քաղաքացիների դիմում-բողոքների,հեռախոսազանգերի,ինչպես նաև «Թեժ գծի» հեռախոսահամարին։",R.drawable.komunal4))



        mylist.adapter= context?.let { CustomAdapter(it,arrNkarner) }
        return view


    }


    class CustomAdapter(var context: Context, var images:ArrayList<Nkarner>): BaseAdapter(){

        private class ViewHolder(row: View?){
            var textName: TextView
            var ivImage: ImageView
            init {
                this.textName=row?.findViewById(R.id.komunal_text) as TextView
                this.ivImage=row?.findViewById(R.id.komunal_imageView) as ImageView
            }

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view:View?
            var viewHolder: ViewHolder
            if (convertView==null){
                var layout=LayoutInflater.from(context)
                view=layout.inflate(R.layout.komunal_item_list,parent,false)
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
