package com.hackyeah.lotto.registration.repository

import android.content.Context
import android.net.Uri
import com.hackyeah.lotto.registration.repository.entity.DetectionResultEntity
import com.hackyeah.lotto.registration.repository.entity.FaceCompareEntity
import com.hackyeah.lotto.registration.repository.entity.VerificationResultEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class FaceRecognitionRepository(private val context: Context, private val faceRecognitionApi: FaceRecognitionApi) {

    suspend fun detectFace(fileUri: Uri): List<DetectionResultEntity> {
        val content = readBytes(context, fileUri) ?: byteArrayOf()
        val body = content.toRequestBody(
            "application/octet-stream".toMediaTypeOrNull(),
            0, content.size
        )

        return faceRecognitionApi.detect(body)
    }

    suspend fun verifyFaces(face1: String, face2: String): VerificationResultEntity {
        return faceRecognitionApi.compare(FaceCompareEntity(face1, face2))
    }

    @Throws(IOException::class)
    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
}