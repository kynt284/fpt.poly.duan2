package com.canhhh.kynt.analogfilter.ui.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.utills.ViewHelper
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_start.*
import java.io.File
import android.widget.TextView


class StartActivity : AppCompatActivity() {
    private var photoURI: Uri? = null
    lateinit var pictureFilePath: String
    private val openCAMERA = 99
    private val openGRARYLE = 98

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Fabric.with(this, Crashlytics())
        mStartActivity = this

        imageView.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this@StartActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
            else openCamera()
        }

        imageView2.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this@StartActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
            else openGra()
        }

        setFont(0, logoTextView)
        setFont(1, cameraTextView)
        setFont(1, galleryTextView)
        setFont(2, clickOpenTextView)
        setFont(1, copyrightTextView)

    }

    fun setFont(font:Int, textView: TextView){
        if (font==0) {
            val type = Typeface.createFromAsset(assets, "fonts/kaushan.ttf")
            textView.typeface = type
        } else if (font == 1){
            val type = Typeface.createFromAsset(assets, "fonts/opensans.ttf")
            textView.typeface = type
        } else {
            val type = Typeface.createFromAsset(assets, "fonts/openransregular.ttf")
            textView.typeface = type
        }
    }

    private fun openCamera(){
        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, false)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                val pictureFile: File?
                pictureFile = ViewHelper().storage2(this@StartActivity)
                photoURI = FileProvider.getUriForFile(this@StartActivity, getString(R.string.str_provider), pictureFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(cameraIntent, openCAMERA)
            }
        }catch (e: java.lang.Exception){
            Toast.makeText(applicationContext, getString(R.string.stringcatch), Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGra(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, openGRARYLE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            openGRARYLE -> if (data != null) {
                val intent = Intent(this@StartActivity, MainActivity::class.java)
                intent.putExtra("GALLERY", "GALLERY")
                intent.data = data.data
                startActivity(intent)
            }

            openCAMERA -> if (data != null || photoURI != null) {
                val imgFile = File(pictureFilePath)
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(imgFile))
                    if (bitmap != null) {
                        val uri = Uri.fromFile(imgFile)
                        val intent = Intent(this@StartActivity, MainActivity::class.java)
                        intent.putExtra("GALLERY", "CAMERA")
                        intent.data = uri
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }

    companion object {
        private lateinit var mStartActivity: StartActivity
        fun share(): StartActivity {
            return mStartActivity
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
