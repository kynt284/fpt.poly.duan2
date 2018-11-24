package com.canhhh.kynt.analogfilter.ui.dialog


import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.event.OnClickDialog
import com.canhhh.kynt.analogfilter.ui.adapter.WarningPagerAdapter
import kotlinx.android.synthetic.main.choose_pager_dialog_fragment.*


@SuppressLint("ValidFragment")
class ChoosePagerDialogFragment(private val mOnClickDialog: OnClickDialog) : DialogFragment() {
    companion object {
        private lateinit var shared: ChoosePagerDialogFragment

        fun share(): ChoosePagerDialogFragment {
            return shared
        }
    }

    init {
        shared = this
    }


    fun clickNext() {
        noneSwipeViewPager.currentItem = 1
    }


    fun dismissDialog() {
        dismiss()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.choose_pager_dialog_fragment, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }


    private fun setupViewPager() {
        val adapter = WarningPagerAdapter(childFragmentManager, mOnClickDialog)
        noneSwipeViewPager.adapter = adapter
        noneSwipeViewPager.currentItem = 0
    }


    override fun onResume() {
        val window = dialog.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.98).toInt(), (size.y * 0.43).toInt())
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }


}
