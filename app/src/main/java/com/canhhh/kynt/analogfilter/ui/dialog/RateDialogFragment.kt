package com.canhhh.kynt.analogfilter.ui.dialog


import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.canhhh.kynt.analogfilter.R


class RateDialogFragment : DialogFragment() {
    private var mRatingBar: RatingBar? = null
    private var mRateTextView: TextView? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(activity!!)

        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.rate_dialog_fragment, null)
        dialogBuilder.setView(dialogView)

        setupView(dialogView)
        setupWidget()

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        return alertDialog
    }


    private fun setupView(dialogView: View) {
        mRatingBar = dialogView.findViewById(R.id.ratingBar)
        mRateTextView = dialogView.findViewById(R.id.rateTextView)
    }


    private fun setupWidget() {
        mRatingBar!!.numStars = 5
        mRatingBar!!.max = 10
        mRatingBar!!.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, _, _ ->
            mRatingBar!!.visibility = View.GONE
            mRateTextView!!.visibility = View.GONE


            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://play.google.com/store/apps/details?id=" + context!!.packageName)
            startActivity(browserIntent)

            dismiss()
        }
    }


}
