package com.hackyeah.lotto.registration.repository

import com.hackyeah.lotto.registration.repository.entity.DetectionResultEntity
import com.hackyeah.lotto.registration.repository.entity.FaceCompareEntity
import com.hackyeah.lotto.registration.repository.entity.VerificationResultEntity
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FaceRecognitionApi {

    @POST("detect?returnFaceId=true")
    @Headers(
        "Content-Type: application/octet-stream",
        "Ocp-Apim-Subscription-Key: a82d46cc3e36498081d7a733b7254780"
    )
    suspend fun detect(@Body image: RequestBody): List<DetectionResultEntity>

    @POST("verify")
    @Headers(
        "Content-Type: application/json",
        "Ocp-Apim-Subscription-Key: a82d46cc3e36498081d7a733b7254780"
    )
    suspend fun compare(@Body faceCompare: FaceCompareEntity): VerificationResultEntity
}