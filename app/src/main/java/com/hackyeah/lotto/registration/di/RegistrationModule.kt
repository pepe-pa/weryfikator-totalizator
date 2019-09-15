package com.hackyeah.lotto.registration.di

import com.hackyeah.lotto.registration.repository.FaceRecognitionApi
import com.hackyeah.lotto.registration.repository.FaceRecognitionRepository
import com.hackyeah.lotto.registration.repository.TextRecognitionApi
import com.hackyeah.lotto.registration.repository.TextRecognitionRepository
import com.hackyeah.lotto.registration.viewmodel.RegistrationViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val registrationModule = module {
    viewModel {
        RegistrationViewModel(get(), get())
    }

    single {
        TextRecognitionRepository(androidContext(), get())
    }

    single {
        FaceRecognitionRepository(androidContext(), get())
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
            .create(FaceRecognitionApi::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
            .create(TextRecognitionApi::class.java)
    }

    single {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }
}