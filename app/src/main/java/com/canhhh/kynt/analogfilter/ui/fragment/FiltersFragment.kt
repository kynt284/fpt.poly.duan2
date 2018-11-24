package com.canhhh.kynt.analogfilter.ui.fragment


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.event.FiltersListFragmentListener
import com.canhhh.kynt.analogfilter.event.ThumbnailsAdapterListener
import com.canhhh.kynt.analogfilter.ui.activity.MainActivity
import com.canhhh.kynt.analogfilter.ui.adapter.FilterAdapter
import com.canhhh.kynt.analogfilter.utills.BitmapHelper
import com.canhhh.kynt.analogfilter.utills.MyFilterData
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.android.synthetic.main.layout_recycler_view.*
import java.util.*


class FiltersFragment : Fragment(), ThumbnailsAdapterListener {

    private var mListener: FiltersListFragmentListener? = null
    private var mAdapter: FilterAdapter? = null
    private var thumbnailItemList: MutableList<ThumbnailItem>? = null



    fun setListener(listener: FiltersListFragmentListener) {
        mListener = listener
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler_view, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setupRecyclerView()
    }




    private fun initData() {
        thumbnailItemList = ArrayList()
        mAdapter = FilterAdapter(activity!!, thumbnailItemList!!, this)
    }



    private fun setupRecyclerView() {
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = mAdapter
        prepareThumbnail(null)
    }



    fun prepareThumbnail(bitmap: Bitmap?) {
        val r = Runnable {
            val thumbImage: Bitmap = (if (bitmap == null) {
                BitmapHelper().getBitmapFromAssets(activity!!, MainActivity.IMAGE_NAME, 200, 200)
            } else {
                Bitmap.createScaledBitmap(bitmap, 200, 200, true)
            }) ?: return@Runnable


            ThumbnailsManager.clearThumbs()
            thumbnailItemList!!.clear()

            val thumbnailItem = ThumbnailItem()
            thumbnailItem.image = thumbImage
            thumbnailItem.filterName = getString(R.string.filter_normal)
            ThumbnailsManager.addThumb(thumbnailItem)

            val filters = MyFilterData.getFilterPack(activity!!)

            for (i in 0 until filters.size) {
                val tI = ThumbnailItem()
                tI.image = thumbImage
                tI.filter = filters[i]
                tI.filterName = filters[i].name
                ThumbnailsManager.addThumb(tI)
            }

            thumbnailItemList!!.addAll(ThumbnailsManager.processThumbs(activity))

            activity!!.runOnUiThread{ mAdapter!!.notifyDataSetChanged()}
        }

        Thread(r).start()
    }


    override fun onFilterSelected(filter: Filter) {
        if (mListener != null)
            mListener!!.onFilterSelected(filter)
    }
}
