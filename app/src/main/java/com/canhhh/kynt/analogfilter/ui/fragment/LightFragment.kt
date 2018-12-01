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
import com.canhhh.kynt.analogfilter.ui.adapter.LightAdapter
import com.canhhh.kynt.analogfilter.utills.BitmapHelper
import com.canhhh.kynt.analogfilter.utills.UnlockManager
import com.canhhh.kynt.analogfilter.utills.ViewHelper
import java.util.*

class LightFragment : Fragment(),
    OnClickAdapter,
        OnClickDialog.VideoCompleted,
        OnClickDialog.UnlockAds {

    override fun onUnlock() {

    }


    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: LightAdapter? = null


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
        val filtersList = ArrayList<MyFilter>()
        for (i in 0..29) {
            if (i == 0) {
                filtersList.add(MyFilter(getString(R.string.filter_normal), "file:///android_asset/gradientfilter/gradient0.png"))
            }
            filtersList.add(MyFilter("L" + (i + 1), "file:///android_asset/lightshadowfilter/a_common" + (i + 1) + ".jpg"))
        }
        mAdapter = LightAdapter(activity!!, filtersList, this)
    }


    private fun setupRecyclerView() {
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
    }


    override fun onClick(position: Int) {
        var bm: Bitmap? = null
        val numberUnlock = UnlockManager.getUnLock(activity!!, UnlockManager.UNLOCK_LIGHT)
        if (position == 0) {
            bm = BitmapHelper().getBitmapAsset(activity!!, "gradientfilter/gradient0.png")
        } else {
            if (position > numberUnlock) {
                ViewHelper().showMessageAdsDialog(activity!!,this)
            } else {
                bm = BitmapHelper().getBitmapAsset(activity!!, "lightshadowfilter/a_common$position.jpg")
            }
        }
        MainActivity.share().addLight(bm)
    }





    override fun onReceiveBonus() {
        UnlockManager.setUnlock(activity!!, UnlockManager.UNLOCK_LIGHT, UnlockManager.getUnLock(activity!!, UnlockManager.UNLOCK_LIGHT) + 3)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onFailureBonus() {

    }
}
