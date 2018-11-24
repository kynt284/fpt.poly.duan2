package com.canhhh.kynt.analogfilter.utills

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v4.app.FragmentActivity
import android.view.animation.RotateAnimation
import com.canhhh.kynt.analogfilter.event.OnClickDialog
import com.canhhh.kynt.analogfilter.ui.activity.MainActivity
import com.canhhh.kynt.analogfilter.ui.activity.StartActivity
import com.canhhh.kynt.analogfilter.ui.dialog.UnlockDialogFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ViewHelper{
    fun openImage(context: Context, path: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse(path), "image/*")
        context.startActivity(intent)
    }


    fun rotate(degree: Float): RotateAnimation {
        val rotateAnim = RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f)

        rotateAnim.duration = 0
        rotateAnim.fillAfter = true
        return rotateAnim
    }


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun storage(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val pictureFile = "IMG_$timeStamp"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(pictureFile, ".png", storageDir)
        MainActivity.share().pictureFilePath = image.absolutePath
        return image
    }


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun storage2(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val pictureFile = "IMG_$timeStamp"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(pictureFile, ".png", storageDir)
        StartActivity.share().pictureFilePath = image.absolutePath
        return image
    }


    fun showMessageAdsDialog(context: FragmentActivity, onUnLock: OnClickDialog.UnlockAds) {
        val unlockAds = UnlockDialogFragment(onUnLock)
        unlockAds.show(context.supportFragmentManager, "unlockAds")
    }


}