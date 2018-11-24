package com.canhhh.kynt.analogfilter.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.canhhh.kynt.analogfilter.event.OnClickDialog
import com.canhhh.kynt.analogfilter.ui.dialog.ChooseDialogFragmentMain
import com.canhhh.kynt.analogfilter.ui.fragment.WarningFragment


class WarningPagerAdapter(manager: FragmentManager, private val mOnClickDialog: OnClickDialog) : FragmentPagerAdapter(manager) {


    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> WarningFragment()
            1 -> ChooseDialogFragmentMain(mOnClickDialog)
            else -> null
        }
    }


    override fun getCount(): Int {
        return 2
    }


}
