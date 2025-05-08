package app.myquizapp.co.uk.di

import app.myquizapp.co.uk.data.remote.QuizApi
import app.myquizapp.co.uk.data.remote.ScoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuizApi(): QuizApi {
        return Retrofit.Builder()
            .baseUrl("https://app.myquizapp.co.uk")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideScoreApi(): ScoreApi {
        return Retrofit.Builder()
            .baseUrl("https://app.myquizapp.co.uk")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

}