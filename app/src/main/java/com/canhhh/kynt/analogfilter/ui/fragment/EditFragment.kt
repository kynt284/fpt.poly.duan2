package com.canhhh.kynt.analogfilter.ui.fragment


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
import com.canhhh.kynt.analogfilter.ui.activity.MainActivity
import com.canhhh.kynt.analogfilter.ui.adapter.EditAdapter
import java.util.*


class EditFragment : Fragment(), OnClickAdapter {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: EditAdapter? = null
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
        mFiltersList!!.add(MyFilter("Rotate", R.drawable.state_rotation_crop))
        mFiltersList!!.add(MyFilter("Contrast", R.drawable.state_constrast))
        mFiltersList!!.add(MyFilter("Saturation", R.drawable.state_palette))
        mFiltersList!!.add(MyFilter("Brightness", R.drawable.state_brightness))
        mFiltersList!!.add(MyFilter("Vignette", R.drawable.state_alpha))

        mAdapter = EditAdapter(activity!!, mFiltersList!!, this)
    }


    private fun setupRecyclerView() {
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
    }


    override fun onClick(position: Int) {
        when (position) {
            0 -> MainActivity.share().cropImage()
            1 -> MainActivity.share().clickEditContrast()
            2 -> MainActivity.share().clickEditSaturation()
            3 -> MainActivity.share().clickEditBrightness()
            4 -> MainActivity.share().clickEditVignette()
        }
    }


}
