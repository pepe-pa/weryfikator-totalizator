package com.hackyeah.lotto.registration.repository.entity

data class VerificationResultEntity(
    private val isIdentical: Boolean,
    private val confidence: Double
)