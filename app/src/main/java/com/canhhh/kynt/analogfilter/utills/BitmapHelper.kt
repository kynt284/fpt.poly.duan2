package com.canhhh.kynt.analogfilter.utills

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.IOException
import java.io.InputStream

class BitmapHelper {


    fun getBitmapFromAssets(context: Context, fileName: String, width: Int, height: Int): Bitmap? {
        val assetManager = context.assets

        val istr: InputStream
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            istr = assetManager.open(fileName)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, width, height)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(istr, null, options)
        } catch (e: IOException) {
            Log.e("JJJ", "Exception: " + e.message)
        }

        return null
    }


    fun getBitmapFromGallery(context: Context, path: Uri, width: Int, height: Int): Bitmap {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(path, filePathColumn, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(picturePath, options)

        options.inSampleSize = calculateInSampleSize(options, width, height)

        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(picturePath, options)
    }


    private fun storeThumbnail(
            cr: ContentResolver,
            source: Bitmap,
            id: Long,
            width: Float,
            height: Float,
            kind: Int): Bitmap? {

        val matrix = Matrix()
        val scaleX = width / source.width
        val scaleY = height / source.height

        matrix.setScale(scaleX, scaleY)

        val thumb = Bitmap.createBitmap(source, 0, 0,
                source.width,
                source.height, matrix,
                true
        )

        val values = ContentValues(4)
        values.put(MediaStore.Images.Thumbnails.KIND, kind)
        values.put(MediaStore.Images.Thumbnails.IMAGE_ID, id.toInt())
        values.put(MediaStore.Images.Thumbnails.HEIGHT, thumb.height)
        values.put(MediaStore.Images.Thumbnails.WIDTH, thumb.width)

        val url = cr.insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, values)

        try {
            val thumbOut = cr.openOutputStream(url!!)
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut)
            thumbOut!!.close()
            return thumb
        } catch (ex: IOException) {
            return null
        }

    }

    fun mergeBitmap(originalBitmap: Bitmap, newBitmap: Bitmap, alpha: Int): Bitmap {
        val width = originalBitmap.width
        val height = originalBitmap.height


        val updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val resized = Bitmap.createScaledBitmap(newBitmap, width, height, true)
        //        Bitmap resized = getResizedBitmap(newBitmap, newBitmap.getWidth(), newBitmap.getHeight());

        val canvas = Canvas(updatedBitmap)

        canvas.drawBitmap(originalBitmap, 0f, 0f, null)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.alpha = alpha
        val bitmapShader = BitmapShader(resized, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = bitmapShader
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        resized.recycle()
        return updatedBitmap
    }

    fun getBitmapAsset(context: Context, filePath: String): Bitmap? {
        val assetManager = context.assets

        var bitmap: Bitmap? = null
        try {
            val ims = assetManager.open(filePath)
            bitmap = BitmapFactory.decodeStream(ims)
        } catch (e: IOException) {
            // handle exception
        }

        return bitmap
    }


    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight //chiều cao ảnh gốc
        val width = options.outWidth   //chiều rộng ảnh gốc
        var inSampleSize = 1 // kích thước mẫu

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2 // một nửa chiều cao
            val halfWidth = width / 2   // một nửa chiều rộng

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    fun getScaledDownBitmap(bitmap: Bitmap, threshold: Int, isNecessaryToKeepOrig: Boolean): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        var newWidth = width
        var newHeight = height

        if (width > height && width > threshold) {
            newWidth = threshold
            newHeight = (height * newWidth.toFloat() / width).toInt()
        }

        if (width > height && width <= threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap
        }

        if (width < height && height > threshold) {
            newHeight = threshold
            newWidth = (width * newHeight.toFloat() / height).toInt()
        }

        if (width < height && height <= threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap
        }

        if (width == height && width > threshold) {
            newWidth = threshold
            newHeight = newWidth
        }

        return if (width == height && width <= threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            bitmap
        } else getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig)

    }



    private fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int, isNecessaryToKeepOrig: Boolean): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height

        val matrix = Matrix()

        matrix.postScale(scaleWidth, scaleHeight)

        val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
        if (!isNecessaryToKeepOrig) {
            bm.recycle()
        }
        return resizedBitmap
    }
}