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
import kotlinx.android.synthetic.main.fragment_larer.*

class LarerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_larer, container, false)
        var mylist=view.findViewById(R.id.listView_kap) as ListView
        var arrNkarner:ArrayList<Nkarner>  = ArrayList()
        arrNkarner.add(Nkarner("Առաջիկա մի քանի տարիների ընթացքում կորուստները կրճատվելու են," +
                " հասնելով նվազագույն ծավալների, ինչն ամենամեծ նվաճումը կլինի նույնիսկ միջազգային փորձի համեմատ:",R.drawable.kap))
        arrNkarner.add(Nkarner("«Հայաստանի էլեկտրական ցանցեր» փակ բաժնետիրական ընկերությունը տեղեկացնում է, որ ս/թ" +
                " սեպտեմբերի 27-ին պլանային և վերանորոգման աշխատանքներ իրականացնելու նպատակով կհոսանքազրկվեն:",R.drawable.kap2))
        arrNkarner.add(Nkarner("Պահպանման ծառայություններ են մատուցվում և շահագործվում են" +
                " Երևանի քաղաքապետարանի հաշվեկշռում ընդգրկված 37 կամուրջները, ուղեանցերը," +
                "վերգետնյա անցումները և կամրջային կառույցները։",R.drawable.kap3))



        mylist.adapter= context?.let { CustomAdapter(it,arrNkarner) }
        return view


    }


    class CustomAdapter(var context: Context, var images:ArrayList<Nkarner>): BaseAdapter(){

        private class ViewHolder(row: View?){
            var textName: TextView
            var ivImage: ImageView
            init {
                this.textName=row?.findViewById(R.id.kap_text) as TextView
                this.ivImage=row?.findViewById(R.id.kap_imageView) as ImageView
            }

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view:View?
            var viewHolder: ViewHolder
            if (convertView==null){
                var layout=LayoutInflater.from(context)
                view=layout.inflate(R.layout.kap_item_list,parent,false)
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
