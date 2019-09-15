package com.hackyeah.lotto.registration.repository

import com.hackyeah.lotto.registration.repository.entity.TextRecognitionResult
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TextRecognitionApi {

    @POST("ocr")
    @Headers(
        "Content-Type: application/octet-stream",
        "Ocp-Apim-Subscription-Key: 974e7f0ce38c4475a04e0ed5f8cdbc10"
    )
    suspend fun recognizeText(@Body requestBody: RequestBody): TextRecognitionResult
}
