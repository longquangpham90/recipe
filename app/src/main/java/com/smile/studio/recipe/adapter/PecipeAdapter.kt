package com.smile.studio.recipe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.nekoloop.base64image.Base64Image
import com.nekoloop.base64image.RequestDecode.Decode
import com.smile.studio.recipe.R
import com.smile.studio.recipe.model.OnItemClickListenerRecyclerView
import com.smile.studio.recipe.model.greendao.Pecipe
import java.util.*

class PecipeAdapter(val mContext: Context, val mData: ArrayList<Pecipe>) : RecyclerView.Adapter<PecipeAdapter.ViewHolder>() {

    var onItemClick: OnItemClickListenerRecyclerView? = null

    fun addAll(mData: ArrayList<Pecipe>) {
        this.mData.addAll(mData)
        notifyDataSetChanged()
    }

    fun clear() {
        this.mData.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_pecipe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_title.text = mData.get(position).title
        holder.tv_description.text = mData.get(position).description
        Base64Image.with(mContext)
                .decode(mData.get(position).image.trim())
                .into(object : Decode {
                    override fun onSuccess(bitmap: Bitmap) {
                        Glide.with(mContext)
                                .load(bitmap)
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                        .format(DecodeFormat.PREFER_ARGB_8888).error(R.drawable.ic_image_blank)
                                        .placeholder(R.drawable.ic_image_blank).dontAnimate())
                                .thumbnail(0.5f)
                                .into(holder.thumb)
                    }

                    override fun onFailure() {
                        Log.e("Tag", "--- Error Decode Base64")
                    }
                })

        holder.itemView.setOnClickListener { view ->
            onItemClick?.let {
                it.onClick(view, position)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val thumb = view.findViewById<ImageView>(R.id.thumb)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)
    }
}