package com.hackyeah.lotto.registration.repository.entity

import android.gesture.OrientedBoundingBox
import android.graphics.drawable.GradientDrawable

data class TextRecognitionResult(
    private val language: String,
    private val textAngle: Double,
    private val orientation: String,
    val regions: List<Region>
)

data class Region(
    private val boundingBox: String,
    val lines: List<Line>
)

data class Line(

    private val boundingBox: String,
    val words: List<Word>
)

data class Word(
    private val boundingBox: String,
    val text: String
)