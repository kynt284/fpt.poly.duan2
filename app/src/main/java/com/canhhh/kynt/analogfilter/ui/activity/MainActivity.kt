package com.canhhh.kynt.analogfilter.ui.activity


import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import com.canhhh.kynt.analogfilter.R
import com.canhhh.kynt.analogfilter.event.FiltersListFragmentListener
import com.canhhh.kynt.analogfilter.event.OnClickDialog
import com.canhhh.kynt.analogfilter.ui.adapter.ViewPagerAdapter
import com.canhhh.kynt.analogfilter.ui.dialog.ChoosePagerDialogFragment
import com.canhhh.kynt.analogfilter.ui.dialog.ExitDialogFragment
import com.canhhh.kynt.analogfilter.ui.dialog.RateDialogFragment
import com.canhhh.kynt.analogfilter.ui.fragment.*
import com.canhhh.kynt.analogfilter.utills.BitmapHelper
import com.canhhh.kynt.analogfilter.utills.ViewHelper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(),
    FiltersListFragmentListener,
        SeekBar.OnSeekBarChangeListener,
    OnClickDialog {

    private var filtersListFragment: FiltersFragment? = null
    private var mRotate = 0
    private var mFlip = 180
    var alphaFilter = 1
    var mAlphaGradient = 1
    var mAlphaGrain = 1
    var mAlphaLight = 1
    var pictureFilePath: String? = null

    private var mTabPosition: Int = 0
    private var mBrightnessFinal: Int = 0
    private var mVignetteFinal: Int = 0
    private var mSaturationFinal: Float = 0.toFloat()
    private var mContrastFinal: Float = 0.toFloat()

    private var gradientBitmap: Bitmap? = null
    private var grainBitmap: Bitmap? = null
    private var lightBitmap: Bitmap? = null
    private var filterBimap: Bitmap? = null
    private var saturationBitmap: Bitmap? = null
    private var contrastBitmap: Bitmap? = null
    private var vignetteBitmap: Bitmap? = null
    private var brightBitmap: Bitmap? = null
    private var photoURI: Uri? = null
    private var checkRecyclerStartAct = false
    private var isRecyclerBright = false
    private var isRecyclerVignette = false
    private var isRecyclerContrast = false
    private var isRecyclerSaturation = false
    private var isRecyclerFilter = false
    private var isRecyclerLight = false
    private var isRecyclerGrain = false
    private var isRecyclerGradient = false

    private lateinit var mOriginalBitmap: Bitmap
    private lateinit var finalBitmap: Bitmap
    private lateinit var listSeekBar: Array<SeekBar>
    private var exitDialogFragment: ExitDialogFragment? = null

    private val tabIcons = intArrayOf(
        R.drawable.ic_auto_fix, R.drawable.ic_pencil, R.drawable.ic_gradient, R.drawable.ic_grain, R.drawable.ic_flash_circle
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        loadImage()
        setupViewPager()
        setupWidget()
        attachEvent()
        mainShare = this
    }

    private fun initData() {
        listSeekBar = arrayOf(contrastSeekBar, saturationSeekBar, brightnessSeekBar,
            vignetteSeekBar, alphaFilterSeekBar, alphaGradientSeekBar, alphaGrainSeekBar, alphaLightSeekBar)
    }

    private fun loadImage() {
        if (checkRecyclerStartAct) resetBitmap()
        else Log.d("YYY", "loadImage: ")

        if (intent.data != null) {
            if (intent.getStringExtra("GALLERY").equals("GALLERY", ignoreCase = true)) {
                val selectedImgUri: Uri? = intent.data
                val bitmap: Bitmap = BitmapHelper().getBitmapFromGallery(this, selectedImgUri!!, 1080, 1920)
                val scaleBitmap: Bitmap = BitmapHelper().getScaledDownBitmap(bitmap, 1080, false)
                preViewImageView.setImageBitmap(scaleBitmap)

                mOriginalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
                finalBitmap = mOriginalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                preViewImageView.setImageBitmap(finalBitmap)
                checkRecyclerStartAct = true
            }

            if (intent.getStringExtra("GALLERY").equals("CAMERA", ignoreCase = true)) {
                try {
                    val selectedImgUri: Uri? = intent.data
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImgUri)
                    val scaleBitmap: Bitmap = BitmapHelper().getScaledDownBitmap(bitmap, 1080, false)
                    preViewImageView.setImageBitmap(scaleBitmap)

                    mOriginalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
                    finalBitmap = mOriginalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                    preViewImageView.setImageBitmap(finalBitmap)
                    checkRecyclerStartAct = true
                } catch (e: Exception) { e.printStackTrace()}
            }
        } else {
            mOriginalBitmap = BitmapHelper().getBitmapFromAssets(this, IMAGE_NAME, 1080, 1920)!!
            finalBitmap = mOriginalBitmap.copy(Bitmap.Config.ARGB_8888, true)
            preViewImageView.setImageBitmap(mOriginalBitmap)
        }
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        filtersListFragment = FiltersFragment()
        adapter.addFragment(filtersListFragment!!, getString(R.string.tab_filters))
        filtersListFragment!!.setListener(this)

        val editImageFragment = EditFragment()
        adapter.addFragment(editImageFragment, getString(R.string.edit))

        val gradientFragment = GradientFragment()
        adapter.addFragment(gradientFragment, getString(R.string.gradient))

        val grainFragment = GrainFragment()
        adapter.addFragment(grainFragment, getString(R.string.dust))

        val lightFragment = LightFragment()
        adapter.addFragment(lightFragment, getString(R.string.light))

        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)

        setupTabIcons()
    }

    private fun setupTabIcons() {
        tabs.getTabAt(0)!!.setIcon(tabIcons[0])
        tabs.getTabAt(1)!!.setIcon(tabIcons[1])
        tabs.getTabAt(2)!!.setIcon(tabIcons[2])
        tabs.getTabAt(3)!!.setIcon(tabIcons[3])
        tabs.getTabAt(4)!!.setIcon(tabIcons[4])
    }

    private fun setupWidget() {
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mTabPosition = tab.position
                if (mTabPosition == 0) {
                    goneSeekBar()
                    cropLinearLayout.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                goneSeekBar()
                cropLinearLayout.visibility = View.GONE
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        if (mTabPosition == 0) {
            goneSeekBar()
            cropLinearLayout.visibility = View.GONE
        }

        setupSeekBar()
        viewpager.offscreenPageLimit = 5
    }


    private fun setupSeekBar() {
        alphaGrainSeekBar.setOnSeekBarChangeListener(this)
        alphaLightSeekBar.setOnSeekBarChangeListener(this)
        alphaGradientSeekBar.setOnSeekBarChangeListener(this)
        alphaFilterSeekBar.setOnSeekBarChangeListener(this)
        saturationSeekBar.setOnSeekBarChangeListener(this)
        contrastSeekBar.setOnSeekBarChangeListener(this)
        vignetteSeekBar.setOnSeekBarChangeListener(this)
        brightnessSeekBar.setOnSeekBarChangeListener(this)

        alphaGrainSeekBar.progress = 100
        alphaGrainSeekBar.max = 255
        alphaLightSeekBar.progress = 100
        alphaLightSeekBar.max = 255
        alphaGradientSeekBar.progress = 100
        alphaGradientSeekBar.max = 255
        alphaFilterSeekBar.progress = 100
        alphaFilterSeekBar.max = 255
        saturationSeekBar.progress = 10
        saturationSeekBar.max = 30
        contrastSeekBar.progress = 10
        contrastSeekBar.max = 100
        brightnessSeekBar.progress = 100
        brightnessSeekBar.max = 200
        vignetteSeekBar.progress = 0
        vignetteSeekBar.max = 255
    }

    private fun resetDefault() {
        alphaGrainSeekBar.progress = 100
        alphaLightSeekBar.progress = 100
        alphaGradientSeekBar.progress = 100
        alphaFilterSeekBar.progress = 100
        saturationSeekBar.progress = 10
        contrastSeekBar.progress = 10
        brightnessSeekBar.progress = 100
        vignetteSeekBar.progress = 0
    }

    private fun attachEvent() {
        cameraImageView!!.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        2)
            } else showDialog()
        }

        fun rotateLeft() {
            val a = mRotate + 45
            mRotate = a + 45
            if(mRotate == 360) mRotate = 0
        }

        fun rotateRight() {
            val a = mRotate - 45
            mRotate = a - 45
            if(mRotate == 360) mRotate = 0
        }

        fun flipHor() {
            val a = mFlip - 90
            mFlip = a - 90
            if(mFlip == 360) mFlip = 0
        }

        fun flipVer() {
            val a = mFlip + 90
            mFlip = a + 90
            if(mFlip == 360) mFlip = 0
        }

        rotateLeftImageView.setOnClickListener {
            rotateRight()
            preViewImageView.rotation = mRotate.toFloat()
            finalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
            preViewImageView.setImageBitmap(finalBitmap)
        }

        rotateRightImageView.setOnClickListener {
            rotateLeft()
            preViewImageView.rotation = mRotate.toFloat()
            finalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
            preViewImageView.setImageBitmap(finalBitmap)
        }

        flipHorImageView.setOnClickListener {
            flipHor()
            preViewImageView.rotationX = mFlip.toFloat()
            finalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
            preViewImageView.setImageBitmap(finalBitmap)
        }

        flipVerImageView.setOnClickListener {
            flipVer()
            preViewImageView.rotationY = mFlip.toFloat()
            finalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
            preViewImageView.setImageBitmap(finalBitmap)
        }

        moreImageView.setOnClickListener {
            val rateDialogFragment = RateDialogFragment()
            rateDialogFragment.show(supportFragmentManager, getString(R.string.rateDialogFragment))
        }

        shareImageView.setOnClickListener { saveImageToGallery() }
        val l = MyTouchListener()
        compareImageView.setOnTouchListener(l)

    }

    private fun showDialog() {
        val warningDialogFragment = ChoosePagerDialogFragment(this)
        warningDialogFragment.show(supportFragmentManager, getString(R.string.warningDialogFragment))
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GET_IMAGE_GALLERY -> if (data != null) {
                resetBitmap()
                resetDefault()
                val bitmap = BitmapHelper().getBitmapFromGallery(this, data.data!!, 1080, 1920)

                preViewImageView.setImageBitmap(BitmapHelper().getScaledDownBitmap(bitmap, 1080, false))
                Thread(Runnable {
                    mOriginalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
                    finalBitmap = mOriginalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                    preViewImageView.setImageBitmap(finalBitmap)
                    filtersListFragment!!.prepareThumbnail(mOriginalBitmap)
                }).start()

            } else preViewImageView.setImageBitmap(finalBitmap)

            GET_IMAGE_CAMERA -> if (data != null || photoURI != null) {
                val imgFile = File(pictureFilePath!!)
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(imgFile))
                    if (bitmap != null) {
                        resetBitmap()
                        resetDefault()
                        preViewImageView.setImageBitmap(BitmapHelper().getScaledDownBitmap(bitmap, 1080, false))
                        Thread(Runnable {
                            mOriginalBitmap = (preViewImageView.drawable as BitmapDrawable).bitmap
                            finalBitmap = mOriginalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                            preViewImageView.setImageBitmap(finalBitmap)
                            filtersListFragment!!.prepareThumbnail(mOriginalBitmap)
                        }).start()
                    } else preViewImageView.setImageBitmap(finalBitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else preViewImageView.setImageBitmap(finalBitmap)
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onFilterSelected(filter: Filter) {
        finalBitmap = mOriginalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        filterBimap = filter.processFilter(finalBitmap)
        AddFilterUnderground(applicationContext).execute()
    }

    private fun selectPage() {
        tabs.setScrollPosition(0, 0f, true)
        viewpager.currentItem = 0
        filtersListFragment!!.prepareThumbnail(mOriginalBitmap)
    }

    override fun onClickLeft() {
        try {
            selectPage()
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, false)

            if (cameraIntent.resolveActivity(packageManager) != null) {
                val pictureFile: File?
                pictureFile = ViewHelper().storage(applicationContext)

                photoURI = FileProvider.getUriForFile(applicationContext, getString(R.string.str_provider), pictureFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(cameraIntent, GET_IMAGE_CAMERA)
            }
        } catch (ex: IOException) {
            Toast.makeText(applicationContext, getString(R.string.toas1), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClickRight() {
        selectPage()
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GET_IMAGE_GALLERY)
    }

    override fun onClickExit() {
        exitDialogFragment!!.dismiss()
        super@MainActivity.onBackPressed()
    }

    fun cropImage() {
        cropLinearLayout.visibility = View.VISIBLE
        goneSeekBar()
    }

    fun clickEditContrast() {
        cropLinearLayout.visibility = View.GONE
        isVisibleSeekBar(0)
    }

    fun clickEditSaturation() {
        cropLinearLayout.visibility = View.GONE
        isVisibleSeekBar(1)
    }

    fun clickEditBrightness() {
        cropLinearLayout.visibility = View.GONE
        isVisibleSeekBar(2)
    }

    fun clickEditVignette() {
        cropLinearLayout.visibility = View.GONE
        isVisibleSeekBar(3)
    }

    private fun isVisibleSeekBar(positionVisible: Int) {
        for (i in listSeekBar.indices) {
            if (positionVisible == i)
                listSeekBar[positionVisible].visibility = View.VISIBLE
            else
                listSeekBar[i].visibility = View.GONE
        }
    }

    fun goneSeekBar() {
        for (gone: SeekBar in listSeekBar) {
            gone.visibility = View.GONE
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
        var progress: Int = progress

        when (seekBar.id) {
            R.id.contrastSeekBar -> {
                progress += 90
                mContrastFinal = 0.01f * progress
            }

            R.id.saturationSeekBar -> mSaturationFinal = .10f * progress
            R.id.brightnessSeekBar -> mBrightnessFinal = progress - 100
            R.id.vignetteSeekBar -> mVignetteFinal = progress
            R.id.alphaLightSeekBar -> mAlphaLight = progress
            R.id.alphaGradientSeekBar -> mAlphaGradient = progress
            R.id.alphaGrainSeekBar -> mAlphaGrain = progress
            R.id.alphaFilterSeekBar -> alphaFilter = progress
        }
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        onContrastChanged()
        onSaturationChanged()
        onBrightnessChanged()
        onVignetteChanged()
        AddFilterUnderground(applicationContext).execute()
    }

    private fun onContrastChanged() {
        contrastBitmap = mOriginalBitmap
    }

    private fun onSaturationChanged() {
        saturationBitmap = mOriginalBitmap
    }

    private fun onBrightnessChanged() {
        brightBitmap = mOriginalBitmap
    }

    private fun onVignetteChanged() {
        vignetteBitmap = mOriginalBitmap
    }

    fun addGrain(newBitmap: Bitmap?) {
        grainBitmap = newBitmap
        AddFilterUnderground(applicationContext).execute()
    }

    fun addGradient(newBitmap: Bitmap?) {
        gradientBitmap = newBitmap
        AddFilterUnderground(applicationContext).execute()
    }

    fun addLight(newBitmap: Bitmap?) {
        lightBitmap = newBitmap
        AddFilterUnderground(applicationContext).execute()
    }

    private inner class AddFilterUnderground internal constructor(var mContext: Context) : AsyncTask<Void, Bitmap, Bitmap>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg integers: Void): Bitmap? {
            var bitmap = mOriginalBitmap
            val myFilter = Filter()

            if (gradientBitmap != null) {
                bitmap = BitmapHelper().mergeBitmap(bitmap, gradientBitmap!!, mAlphaGradient)
                isRecyclerGradient = true
            }
            if (lightBitmap != null) {
                bitmap = BitmapHelper().mergeBitmap(bitmap, lightBitmap!!, mAlphaLight)
                isRecyclerLight = true
            }
            if (grainBitmap != null) {
                bitmap = BitmapHelper().mergeBitmap(bitmap, grainBitmap!!, mAlphaGrain)
                isRecyclerGrain = true
            }
            if (filterBimap != null) {
                bitmap = BitmapHelper().mergeBitmap(bitmap, filterBimap!!, alphaFilter)
                isRecyclerFilter = true
            }
            if (saturationBitmap != null) {
                myFilter.addSubFilter(SaturationSubfilter(mSaturationFinal))
                bitmap = myFilter.processFilter(bitmap.copy(Bitmap.Config.ARGB_8888, true))
                isRecyclerSaturation = true
            }
            if (contrastBitmap != null) {
                myFilter.addSubFilter(ContrastSubFilter(mContrastFinal))
                bitmap = myFilter.processFilter(bitmap.copy(Bitmap.Config.ARGB_8888, true))
                isRecyclerContrast = true
            }
            if (vignetteBitmap != null) {
                myFilter.addSubFilter(VignetteSubfilter(mContext, mVignetteFinal))
                bitmap = myFilter.processFilter(bitmap.copy(Bitmap.Config.ARGB_8888, true))
                isRecyclerVignette = true
            }
            if (brightBitmap != null) {
                myFilter.addSubFilter(BrightnessSubFilter(mBrightnessFinal))
                bitmap = myFilter.processFilter(bitmap.copy(Bitmap.Config.ARGB_8888, true))
                isRecyclerBright = true
            }
            return bitmap
        }

        override fun onPostExecute(bitmap: Bitmap) {
            super.onPostExecute(bitmap)
            preViewImageView.setImageBitmap(Filter().processFilter(bitmap))
            finalBitmap = bitmap
            progressBar.visibility = View.GONE
        }
    }

    private fun saveImageToGallery() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) SaveImage(this@MainActivity).execute()
                        else Toast.makeText(applicationContext, getString(R.string.permission), Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<com.karumi.dexter.listener.PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    private fun resetBitmap() {
        if (isRecyclerBright) {
            brightBitmap = null
            isRecyclerBright = false
        }
        if (isRecyclerVignette) {
            vignetteBitmap = null
            isRecyclerVignette = false
        }
        if (isRecyclerContrast) {
            contrastBitmap = null
            isRecyclerContrast = false
        }
        if (isRecyclerSaturation) {
            saturationBitmap = null
            isRecyclerSaturation = false
        }
        if (isRecyclerFilter) {
            filterBimap = null
            isRecyclerFilter = false
        }
        if (isRecyclerLight) {
            lightBitmap = null
            isRecyclerLight = false
        }
        if (isRecyclerGrain) {
            grainBitmap = null
            isRecyclerGrain = false
        }
        if (isRecyclerGradient) {
            gradientBitmap = null
            isRecyclerGradient = false
        }
        finalBitmap.recycle()
        mOriginalBitmap.recycle()
    }

    override fun onBackPressed() {
        exitDialogFragment = ExitDialogFragment(this)
        exitDialogFragment!!.show(supportFragmentManager, getString(R.string.exitDialogFragment))
    }

    private inner class SaveImage internal constructor(mContext: Context?) : AsyncTask<Void, String, String>() {
        private val dialog: ProgressDialog = ProgressDialog(mContext)

        override fun onPreExecute() {
            super.onPreExecute()
            dialog.setTitle(getString(R.string.save))
            dialog.setMessage(getString(R.string.wait))
            dialog.max = 100
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            dialog.isIndeterminate = true
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }

        override fun doInBackground(vararg voids: Void): String? {
            val filePath = Environment.getExternalStorageDirectory().toString() + "/Analog Filter"
            try {
                val bitmap = finalBitmap
                val byteOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
                val bitmapData = byteOutputStream.toByteArray()
                val inputStream = ByteArrayInputStream(bitmapData)
                val direct = File(filePath)

                if (!direct.exists()) {
                    val f = File(filePath)
                    f.mkdirs()
                }

                val fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
                val file = File(File(filePath), fileName)
                if (file.exists()) file.delete()

                val outputStream = FileOutputStream(file)
                byteOutputStream.writeTo(outputStream)
                val buffer = ByteArray(2048)
                val lengthOfFile = bitmapData.size
                var totalWritten = 0
                var bufferedBytes = 0

                while (inputStream.read(buffer).also { bufferedBytes = it } >= 0) {
                    outputStream.write(buffer, 0, bufferedBytes)
                    totalWritten += bufferedBytes
                    publishProgress(Integer.toString(totalWritten * 100 / lengthOfFile))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: String) {
            super.onProgressUpdate(*values)
            dialog.isIndeterminate = false
            dialog.progress = Integer.parseInt(values[0])
        }

        override fun onPostExecute(path: String?) {
            super.onPostExecute(path)
            dialog.dismiss()
            doneImageView.visibility = View.VISIBLE
            Handler().postDelayed({ doneImageView.visibility = View.GONE }, 2200)
        }
    }

    private inner class MyTouchListener : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> preViewImageView.setImageBitmap(mOriginalBitmap)
                MotionEvent.ACTION_UP -> preViewImageView.setImageBitmap(finalBitmap)
            }
            return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mOriginalBitmap.recycle()
        finalBitmap.recycle()
        checkRecyclerStartAct = false
    }

    companion object {
        init { System.loadLibrary("NativeImageProcessor") }

        const val IMAGE_NAME = "landscape3.jpg"
        private const val GET_IMAGE_CAMERA = 99
        private const val GET_IMAGE_GALLERY = 98

        private lateinit var mainShare: MainActivity

        fun share(): MainActivity {
            return mainShare
        }
    }
}

