package com.example.sprint01

import android.content.Context
import android.content.SharedPreferences
import com.example.sprint01.domain.repository.TripRepository
import com.example.sprint01.domain.repository.TripRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.sprint01.data.SharedPrefsManager
import dagger.hilt.android.qualifiers.ApplicationContext



@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTripRepository(): TripRepository = TripRepositoryImpl()

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun provideSharedPrefsManager(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context ): SharedPrefsManager =
        SharedPrefsManager(sharedPreferences, context)



}