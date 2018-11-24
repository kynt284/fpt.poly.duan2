package com.canhhh.kynt.analogfilter.ui.dialog


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.event.OnClickDialog
import kotlinx.android.synthetic.main.choose_dialog_fragment.*


@SuppressLint("ValidFragment")
class ChooseDialogFragmentMain(var mOnClickDialog: OnClickDialog) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.choose_dialog_fragment, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraImageView!!.setOnClickListener {
            mOnClickDialog.onClickLeft()
            ChoosePagerDialogFragment.share().dismissDialog()
        }

        galleryImageView!!.setOnClickListener {
            mOnClickDialog.onClickRight()
            ChoosePagerDialogFragment.share().dismissDialog()
        }
    }

}
