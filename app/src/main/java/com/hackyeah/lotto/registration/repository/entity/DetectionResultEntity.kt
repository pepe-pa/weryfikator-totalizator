package com.hackyeah.lotto.registration.repository.entity

import com.squareup.moshi.Json

data class DetectionResultEntity(
    @field:Json(name = "faceId")  val faceId: String,
    @field:Json(name = "faceRectangle") val faceRectangle: FaceRectangle
)

data class FaceRectangle(
    private val width: Int,
    private val height: Int,
    private val left: Int,
    private val top: Int
)