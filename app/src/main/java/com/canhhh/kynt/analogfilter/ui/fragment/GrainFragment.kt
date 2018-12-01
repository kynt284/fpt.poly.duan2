package com.canhhh.kynt.analogfilter.ui.fragment


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.bean.MyFilter
import com.canhhh.kynt.analogfilter.event.OnClickAdapter
import com.canhhh.kynt.analogfilter.event.OnClickDialog
import com.canhhh.kynt.analogfilter.ui.activity.MainActivity
import com.canhhh.kynt.analogfilter.ui.adapter.GrainAdapter
import com.canhhh.kynt.analogfilter.utills.BitmapHelper
import com.canhhh.kynt.analogfilter.utills.UnlockManager
import com.canhhh.kynt.analogfilter.utills.ViewHelper


class GrainFragment : Fragment(),
    OnClickAdapter,
        OnClickDialog.UnlockAds,
        OnClickDialog.VideoCompleted{



    private var mRecyclerView: RecyclerView? = null
    private lateinit var mAdapter: GrainAdapter
    private var mFiltersList: MutableList<MyFilter>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
        setupRecyclerView()
    }


    private fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }


    private fun initData() {
        mFiltersList = ArrayList()
        for (i in 0..29) {
            if (i == 0) {
                mFiltersList!!.add(MyFilter(getString(R.string.filter_normal), "file:///android_asset/gradientfilter/gradient0.png"))
            }
            mFiltersList!!.add(MyFilter("A" + (i + 1), "file:///android_asset/grain/dust_" + (i + 1) + ".jpg"))
        }

        mAdapter = GrainAdapter(activity!!, mFiltersList!!, this)
    }


    private fun setupRecyclerView() {
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.adapter = mAdapter
    }


    override fun onClick(position: Int) {
        var bm: Bitmap? = null
        if (position == 0) {
            bm = BitmapHelper().getBitmapAsset(activity!!, "gradientfilter/gradient0.png")
        } else {
            if (position > UnlockManager.getUnLock(activity!!, UnlockManager.UNLOCK_GRAIN)) {
                ViewHelper().showMessageAdsDialog(activity!!, this)
            } else {
                bm = BitmapHelper().getBitmapAsset(activity!!, "grain/dust_$position.jpg")
            }

        }
        MainActivity.share().addGrain(bm)
    }


    override fun onUnlock() {
        //ViewHelper().displayAdsRewardedVideo(activity!!, this)
    }

    override fun onReceiveBonus() {
        UnlockManager.setUnlock(activity!!, UnlockManager.UNLOCK_GRAIN, UnlockManager.getUnLock(activity!!, UnlockManager.UNLOCK_GRAIN) + 3)
        mAdapter.notifyDataSetChanged()
    }

    override fun onFailureBonus() {

    }

}
