package com.canhhh.kynt.analogfilter.ui.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.event.OnClickDialog
import com.canhhh.kynt.analogfilter.ui.dialog.ChooseDialogFragmentStart
import com.canhhh.kynt.analogfilter.utills.ViewHelper
import kotlinx.android.synthetic.main.activity_start.*
import java.io.File
import java.io.IOException


class StartActivity : AppCompatActivity(), OnClickDialog {


    private var photoURI: Uri? = null
    lateinit var pictureFilePath: String
    private lateinit var openImageFragment : ChooseDialogFragmentStart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        mStartActivity = this

        openImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@StartActivity,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        2)
            } else {
                showDialog()
            }
        }
    }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            2 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialog()
                }
            }
        }
    }


    private fun showDialog() {
        openImageFragment = ChooseDialogFragmentStart(this)
        openImageFragment.show(supportFragmentManager, "openImageFragment")
    }


    override fun onClickLeft() {
        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, false)

            if (cameraIntent.resolveActivity(packageManager) != null) {
                val pictureFile: File?
                pictureFile = ViewHelper().storage2(this@StartActivity)

                photoURI = FileProvider.getUriForFile(this@StartActivity, getString(R.string.str_provider), pictureFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(cameraIntent, 99)
            }
            openImageFragment.dismiss()
        } catch (ex: IOException) {
            Toast.makeText(applicationContext, "Photo file can't be created, please try again", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onClickRight() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 98)
        openImageFragment.dismiss()
    }


    override fun onClickExit() {

    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            98 -> if (data != null) {
                val intent = Intent(this@StartActivity, MainActivity::class.java)
                intent.putExtra("GALLERY", "GALLERY")
                intent.data = data.data
                startActivity(intent)
            }

            99 -> if (data != null || photoURI != null) {
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
                    Log.d("FF", "onActivityResult: " + e.message)
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
}
