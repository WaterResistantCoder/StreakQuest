package com.waterresistantcoder.streakquest.di

import android.content.Context
import androidx.room.Room
import com.waterresistantcoder.streakquest.data.local.database.QuizModuleDatabase
import com.waterresistantcoder.streakquest.data.remote.QuizApi
import com.waterresistantcoder.streakquest.data.repository.QuizModelRepositoryImpl
import com.waterresistantcoder.streakquest.data.repository.QuizRepositoryImpl
import com.waterresistantcoder.streakquest.domain.repository.QuizModelRepository
import com.waterresistantcoder.streakquest.domain.repository.QuizRepository
import com.waterresistantcoder.streakquest.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideQuizDatabase(@ApplicationContext app: Context): QuizModuleDatabase {
        return Room.databaseBuilder(
            app,
            QuizModuleDatabase::class.java, "quiz-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuizModelDb(@ApplicationContext app: Context): QuizModelRepository {
        return QuizModelRepositoryImpl(Room.databaseBuilder(
            app,
            QuizModuleDatabase::class.java, "quiz-db"
        ).build())
    }
}