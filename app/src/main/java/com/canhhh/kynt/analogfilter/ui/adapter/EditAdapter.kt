package com.canhhh.kynt.analogfilter.ui.adapter


import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.canhhh.kynt.analogfilter.R

import com.canhhh.kynt.analogfilter.bean.MyFilter
import com.canhhh.kynt.analogfilter.event.OnClickAdapter


class EditAdapter(private val mContext: Context, private val mFilters: List<MyFilter>, private val mClickAdapter: OnClickAdapter) : RecyclerView.Adapter<EditAdapter.ViewHolder>() {
    private var selectedIndex = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_edit, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filters = mFilters[holder.adapterPosition]

        holder.mThumbnail.setImageResource(filters.mImageInt)

        holder.mThumbnail.setOnClickListener {
            mClickAdapter.onClick(holder.adapterPosition)
            selectedIndex = holder.adapterPosition
            notifyDataSetChanged()
        }

        holder.mFilterName.text = filters.mNameFilter

        if (selectedIndex == holder.adapterPosition) {
            holder.mFilterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_selected))
        } else {
            holder.mFilterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_normal))
        }


    }


    override fun getItemCount(): Int {
        return mFilters.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mThumbnail: ImageView = view.findViewById(R.id.thumbnail)
        val mFilterName: TextView = view.findViewById(R.id.filter_name)
    }
}
