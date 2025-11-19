package com.waterresistantcoder.streakquest.di

import com.waterresistantcoder.streakquest.data.remote.QuizApi
import com.waterresistantcoder.streakquest.data.repository.QuizRepositoryImpl
import com.waterresistantcoder.streakquest.domain.repository.QuizRepository
import com.waterresistantcoder.streakquest.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuizApi(): QuizApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(api: QuizApi): QuizRepository {
        return QuizRepositoryImpl(api)
    }
}