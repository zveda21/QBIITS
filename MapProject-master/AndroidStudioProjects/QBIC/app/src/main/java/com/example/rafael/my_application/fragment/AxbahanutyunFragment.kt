package com.example.rafael.my_application.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.rafael.my_application.R
import kotlinx.android.synthetic.main.fragment_axbahanutyun.*

class AxbahanutyunFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_axbahanutyun, container, false)
       // var text= arrayOf("afasf","guinu")
       // val myList=view.findViewById(R.id.listView_axb) as ListView
       // myList!!.adapter=ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,text)
        var mylist=view.findViewById(R.id.listView_axb) as ListView
        var arrNkarner:ArrayList<Nkarner>  = ArrayList()
        arrNkarner.add(Nkarner("«Sanitek» ընկերությունը աղբահանության և սանմաքրման գործառույթներ է իրականացնում քաղաք\n" +
                " Երևանի բոլոր վարչական շրջաններում:",R.drawable.sanitek))
        arrNkarner.add(Nkarner("Երևանի ավագանին ընդունեց «Երևանի աղբահանություն և սանիտարական մաքրում» համայնքային հիմնարկ\n" +
                "ստեղծելու մասին որոշումը:",R.drawable.qaxaq))
        arrNkarner.add(Nkarner("Հիմնարկի գործունեության հիմնական նպատակը Երևանի վարչական\n" +
                "տարածքի աղբահանության և սանիտարական մաքրման (ներառյալ՝ ձմեռային ամիսներին)" +
                " աշխատանքների իրականացման ապահովումն է:",R.drawable.sanitek2))
        arrNkarner.add(Nkarner("Թվով 586 աղբամանները տեղադրվել են ինչպես բազմաբնակարան շենքերի բակերում, " +
                "այնպես էլ մասնավոր տնատիրությամբ պարփակված հատվածներում:",R.drawable.sanitek3))
        arrNkarner.add(Nkarner("Երևանում աղբամանների տեղադրման ծրագրով պատվիրվել է ժամանակակաից դիզայնով " +
                "ու հատուկ ձուլվածքով թվով 400 աղբաման և թվով 100 երկկողմանի աղբաման:",R.drawable.axbaman))
        mylist.adapter= context?.let { CustomAdapter(it,arrNkarner) }
        return view


    }


  class CustomAdapter(var context:Context,var images:ArrayList<Nkarner>):BaseAdapter(){

        private class ViewHolder(row: View?){
            var textName: TextView
            var ivImage:ImageView
            init {
                this.textName=row?.findViewById(R.id.axb_text) as TextView
                this.ivImage=row?.findViewById(R.id.axb_imageView) as ImageView
            }

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view:View?
            var viewHolder: ViewHolder
            if (convertView==null){
                var layout=LayoutInflater.from(context)
                 view=layout.inflate(R.layout.axb_item_list,parent,false)
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
