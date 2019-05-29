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
import kotlinx.android.synthetic.main.fragment_nstaran.*

class NstaranFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_nstaran, container, false)

        var mylist1=view.findViewById(R.id.listView_nst) as ListView

        var arrNkar:ArrayList<Nkarn>  = ArrayList()
        arrNkar.add(Nkarn("«Երևանում նստարանների  տեղադրման ծրագրով պատվիրվել է ժամանակակաից դիզայնով " +
                "ու «Երևան» ձուլվածքով թվով 500 նստարան:",R.drawable.nstaran))
        arrNkar.add(Nkarn("Զուգահեռաբար վերանորոգվել է նախորդ տարիների ընթացքում տեղադրված " +
                "«Երևան» ձուլվածքով թվով 640 նստարան:",R.drawable.nstaran2))
        arrNkar.add(Nkarn("Երևանի արտաքին տեսքի բարեկարգման ծրագրի շրջանակում արդեն երկրորդ տարին է՝ " +
                "մայրաքաղաքի փողոցներում, այգիներում ու պուրակներում տեղադրվում են «ԵՐԵՎԱՆ» նշագրմամբ նոր հարմարավետ նստարաններ: ",R.drawable.nstaran3))
        arrNkar.add(Nkarn("Մեկ նստարանի արժեքը կկազմի 165 հազար դրամ:",R.drawable.nstaran4))
        mylist1.adapter= context?.let { CustomAdapter(it,arrNkar) }
        return view


    }


    class CustomAdapter(var context: Context, var images1:ArrayList<Nkarn>): BaseAdapter(){

        private class ViewHolder(row: View?){
            var textName1: TextView
            var ivImage1: ImageView
            init {
                this.textName1=row?.findViewById(R.id.nstaran_text) as TextView
                this.ivImage1=row?.findViewById(R.id.nstaran_imageView) as ImageView
            }

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view1:View?
            var viewHolder1: ViewHolder
            if (convertView==null){
                var layout=LayoutInflater.from(context)
                view1=layout.inflate(R.layout.nstaran_item_list,parent,false)
                viewHolder1=ViewHolder(view1)
                view1.tag=viewHolder1
            }
            else{
                view1=convertView
                viewHolder1=view1.tag as ViewHolder

            }
            var images1:Nkarn=getItem(position) as Nkarn
            viewHolder1.textName1.text=images1.name1
            viewHolder1.ivImage1.setImageResource(images1.image)
            return view1 as View
        }

        override fun getItem(position: Int): Any {
            return images1.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return images1.count()
        }

    }
    data  class Nkarn(var name1: String,var image:Int){

    }



}
