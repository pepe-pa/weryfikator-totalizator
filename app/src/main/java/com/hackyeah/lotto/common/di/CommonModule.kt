package com.hackyeah.lotto.common.di

import com.hackyeah.lotto.registration.repository.FaceRecognitionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

val commonModule = module {
/*    single {
        FaceRecognitionRepository(androidContext(), get())
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/")
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }*/
}