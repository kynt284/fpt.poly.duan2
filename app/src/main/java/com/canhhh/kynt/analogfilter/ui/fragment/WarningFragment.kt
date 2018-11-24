package com.canhhh.kynt.analogfilter.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.ui.dialog.ChoosePagerDialogFragment
import kotlinx.android.synthetic.main.fragment_warning.*


@SuppressLint("ValidFragment")
class WarningFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_warning, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelTextView!!.setOnClickListener { ChoosePagerDialogFragment.share().dismissDialog() }
        exitTextView!!.setOnClickListener { ChoosePagerDialogFragment.share().clickNext() }
    }



}
