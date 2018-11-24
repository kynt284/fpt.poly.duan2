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
import com.canhhh.kynt.analogfilter.event.ThumbnailsAdapterListener
import com.canhhh.kynt.analogfilter.ui.activity.MainActivity
import com.zomato.photofilters.utils.ThumbnailItem
import kotlinx.android.synthetic.main.activity_main.*


class FilterAdapter(private val mContext: Context, private val thumbnailItemList: List<ThumbnailItem>, private val listener: ThumbnailsAdapterListener) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private var selectedIndex = 0
    private var checkClick: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_filter_item, parent, false)

        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thumbnailItem = thumbnailItemList[holder.adapterPosition]
        MainActivity.share().goneSeekBar()

        holder.mThumbnail.setImageBitmap(thumbnailItem.image)
        holder.mThumbnail.scaleType = ImageView.ScaleType.CENTER_CROP
        holder.mThumbnail.setOnClickListener {
            listener.onFilterSelected(thumbnailItem.filter)
            if (holder.adapterPosition != selectedIndex) {
                checkClick = 1
            } else {
                checkClick++
            }
            selectedIndex = holder.adapterPosition
            notifyDataSetChanged()
        }

        holder.mFilterName.text = thumbnailItem.filterName

        if (selectedIndex == holder.adapterPosition) {
            holder.mFilterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_selected))
            holder.mCheckImageView.visibility = View.VISIBLE
            holder.mCheckImageView.setImageResource(R.drawable.selected_marker)
        } else {
            holder.mFilterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_normal))
            holder.mCheckImageView.visibility = View.GONE
        }

        if (checkClick % 2 == 0) {
            MainActivity.share().alphaFilterSeekBar.visibility = View.VISIBLE
        } else {
            MainActivity.share().alphaFilterSeekBar.visibility = View.GONE
        }
    }


    override fun getItemCount(): Int {
        return thumbnailItemList.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val mFilterName: TextView = itemView.findViewById(R.id.filter_name)
        val mCheckImageView: ImageView = itemView.findViewById(R.id.checkImageView)

    }


}
