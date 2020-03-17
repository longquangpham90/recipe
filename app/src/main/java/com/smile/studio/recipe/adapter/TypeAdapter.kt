package com.smile.studio.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.smile.studio.recipe.R
import com.smile.studio.recipe.model.greendao.Type

class TypeAdapter(val mContext: Context, val mData: ArrayList<Type>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = null
        val holder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_type, parent, false)
            holder = ViewHolder(view!!)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        holder.tv_title.text = mData.get(position).title
        return view
    }

    override fun getItem(position: Int): Type {
        return mData.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mData.size
    }

    class ViewHolder(view: View) {
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
    }
}