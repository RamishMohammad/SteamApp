package com.example.steamapp.ui


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.steamapp.R


class MyAdapter(context: Context, arrayListImage: ArrayList<Int>, name: Array<String>) : BaseAdapter() {

    //Passing Values to Local Variables

    var context = context
    var arrayListImage = arrayListImage
    var name = name


    //Auto Generated Method
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myView = convertView
        var holder: ViewHolder

        if (myView == null) {

            val mInflater = (context as Activity).layoutInflater
            myView = mInflater.inflate(com.example.steamapp.R.layout.row_item, parent, false)
            holder = ViewHolder()

            holder.mImageView = myView!!.findViewById<ImageView>(com.example.steamapp.R.id.imageView) as ImageView
            holder.mTextView = myView!!.findViewById<TextView>(com.example.steamapp.R.id.textView) as TextView
            myView.tag = holder
        } else {
            holder = myView.tag as ViewHolder

        }

        holder.mImageView!!.setImageResource(arrayListImage.get(position))
        holder.mTextView!!.text = name[position]

        return myView

    }

    override fun getItem(p0: Int): Any {
        return arrayListImage[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return arrayListImage.size
    }
    class ViewHolder {

        var mImageView: ImageView? = null
        var mTextView: TextView? = null

    }

}