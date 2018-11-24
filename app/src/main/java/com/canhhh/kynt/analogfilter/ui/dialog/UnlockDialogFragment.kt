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
import kotlinx.android.synthetic.main.unlock_dialog_fragment.*

@SuppressLint("ValidFragment")
class UnlockDialogFragment(var onClickDialog: OnClickDialog.UnlockAds) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.unlock_dialog_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unlockTextView.setOnClickListener {
            onClickDialog.onUnlock()
            dismiss()
        }
        cancelTextView.setOnClickListener { dismiss() }
    }

    override fun onResume() {
        val window = dialog.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.98).toInt(), (size.y * 0.42).toInt())
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }
}
