package com.canhhh.kynt.analogfilter.ui.adapter


import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.bean.MyFilter
import com.canhhh.kynt.analogfilter.event.OnClickAdapter
import com.canhhh.kynt.analogfilter.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kynt.fpt.analogfilter.utills.UnlockManager


class GrainAdapter(private val mContext: Context, private val mFilters: List<MyFilter>, private val mClickAdapter: OnClickAdapter) : RecyclerView.Adapter<GrainAdapter.ViewHolder>() {
    private var selectedIndex = 0
    private var checkClick: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_filter_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        MainActivity.share().goneSeekBar()
        val filters = mFilters[holder.adapterPosition]
        holder.mCheckImageView.visibility = View.GONE
        Glide.with(mContext)
                .load(mFilters[position].mImageString)
                .into(holder.mThumbnail)
        holder.mThumbnail.setOnClickListener {
            mClickAdapter.onClick(holder.adapterPosition)
            if (holder.adapterPosition != selectedIndex) {
                checkClick = 1
            } else {
                checkClick++
            }
            selectedIndex = holder.adapterPosition
            notifyDataSetChanged()
        }

        holder.mFilterName.text = filters.mNameFilter


        if (selectedIndex == holder.adapterPosition) {
            holder.mFilterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_selected))
            holder.mCheckImageView.visibility = View.VISIBLE
            holder.mCheckImageView.setImageResource(R.drawable.selected_marker)
        } else {
            holder.mFilterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_normal))
            holder.mCheckImageView.visibility = View.GONE
        }

        if (checkClick % 2 == 0) {
            MainActivity.share().alphaGrainSeekBar.visibility = View.VISIBLE
        } else {
            MainActivity.share().alphaGrainSeekBar.visibility = View.GONE
        }


        if (holder.adapterPosition > UnlockManager.getUnLock(mContext, UnlockManager.UNLOCK_GRAIN)) {
            holder.mLockImageView.visibility = View.VISIBLE
            holder.itemView.isClickable = false
        } else {
            holder.mLockImageView.visibility = View.GONE
            holder.mThumbnail.isClickable = true
            holder.itemView.isClickable = true
        }
    }


    override fun getItemCount(): Int {
        return mFilters.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val mFilterName: TextView = itemView.findViewById(R.id.filter_name)
        val mCheckImageView: ImageView = itemView.findViewById(R.id.checkImageView)
        val mLockImageView: ImageView = itemView.findViewById(R.id.lockImageView)
    }


}
