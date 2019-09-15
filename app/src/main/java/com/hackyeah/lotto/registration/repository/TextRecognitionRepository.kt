package com.hackyeah.lotto.registration.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.hackyeah.lotto.registration.repository.entity.TextRecognitionResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException

class TextRecognitionRepository(private val context: Context, private val textRecognitionApi: TextRecognitionApi) {

    suspend fun recognizeText(fileUri: Uri): TextRecognitionResult {
        val content = readBytes(context, fileUri) ?: byteArrayOf()
        val scaledContent = getScaledImage(content)
        val body = scaledContent.toRequestBody(
            "application/octet-stream".toMediaTypeOrNull(),
            0, scaledContent.size
        )

        return textRecognitionApi.recognizeText(body)
    }

    private fun getScaledImage(originalImage: ByteArray): ByteArray {
        // PNG has not losses, it just ignores this field when compressing
        val COMPRESS_QUALITY = 0

        // Get the bitmap from byte array since, the bitmap has the the resize function
        val bitmapImage = BitmapFactory.decodeByteArray(originalImage, 0, originalImage.size)

        // New bitmap with the correct size, may not return a null object
        val mutableBitmapImage = Bitmap.createScaledBitmap(bitmapImage, (bitmapImage.width * 0.2).toInt(), (bitmapImage.height * 0.2).toInt(), false)

        // Get the byte array from tbe bitmap to be returned
        val outputStream = ByteArrayOutputStream()
        mutableBitmapImage.compress(Bitmap.CompressFormat.PNG, 0, outputStream)

        if (mutableBitmapImage != bitmapImage) {
            mutableBitmapImage.recycle()
        } // else they are the same, just recycle once

        bitmapImage.recycle()
        return outputStream.toByteArray()
    }

    @Throws(IOException::class)
    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
}