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
import kotlinx.android.synthetic.main.fragment_axbahanutyun.*
import kotlinx.android.synthetic.main.fragment_bnutyun.*


class BnutyunFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_bnutyun, container, false)
        var mylist=view.findViewById(R.id.listView_bnapah) as ListView
        var arrNkarner:ArrayList<Nkarner>  = ArrayList()
        arrNkarner.add(Nkarner("Մայրաքաղաքը նախապատրաստվում է մարտյան շաբաթօրյակին:" +
                "Ընթացքում է ծառերի գարնանային էտը, որը կշարունակվի մինչև ապրիլի 20-ը:" +
                "Մայրաքաղաքում սկսվել է սիզամարգերի խոտհունձը:",R.drawable.bnapah))
        arrNkarner.add(Nkarner("Սիզամարգերի բարելավման համար ներկրվում է 10526 խմ բուսահող, պարարտացվում " +
                "են այն տարածքները, որոնցում կակաչներ են տնկված:",R.drawable.bnapah2))
        arrNkarner.add(Nkarner("Նախապատրաստվում է մարտի 31-ին կայանալիք ծառատունկ-շաբաթօրյակը:" +
                "Տնկվելու են ակացիա, հացենի, սոսի, կաղնի, կատալպա, բարդի, նոճի, սյունաձև գիհի ծառատեսակներ և ծաղկող թփեր:",R.drawable.bnapah3))
        arrNkarner.add(Nkarner("Մայրաքաղաքի վարչական շրջանների կանաչ տարածքների խնամքի ու բարեկարգման ծրագրով " +
                "իրականացվում են վթարային ծառերի էտման աշխատանքները," +
                " որոնց նպատակն է ծառերը ձևավորել և տալ դեկորատիվ գեղեցիկ արտաքին տեսք:",R.drawable.bnapah4))
        arrNkarner.add(Nkarner("Այս տարի ներկրված և Երևանի քաղաքային ջերմատանն աճեցվող շուրջ 1 մլն 200 հազար և " +
                "վարչական շրջաններում աճեցված ևս 300 հազ. ծաղկասածիլները արդեն իսկ տեղադրվել են սիզամարգերում: ",R.drawable.bnapah5))


        mylist.adapter= context?.let { CustomAdapter(it,arrNkarner) }
        return view


    }


    class CustomAdapter(var context: Context, var images:ArrayList<Nkarner>): BaseAdapter(){

        private class ViewHolder(row: View?){
            var textName: TextView
            var ivImage: ImageView
            init {
                this.textName=row?.findViewById(R.id.bnapah_text) as TextView
                this.ivImage=row?.findViewById(R.id.bnapah_imageView) as ImageView
            }

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view:View?
            var viewHolder: ViewHolder
            if (convertView==null){
                var layout=LayoutInflater.from(context)
                view=layout.inflate(R.layout.bnapah_item_list,parent,false)
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
