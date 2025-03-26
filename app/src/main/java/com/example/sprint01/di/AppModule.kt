package com.example.sprint01.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.sprint01.BuildConfig
import com.example.sprint01.data.SharedPrefsManager
import com.example.sprint01.data.local.PlanMyEscapeDatabase
import com.example.sprint01.data.local.dao.ItineraryItemDao
import com.example.sprint01.data.local.dao.TripDao
import com.example.sprint01.domain.repository.TripRepository
import com.example.sprint01.data.repository.TripRepositoryImpl
import com.example.sprint01.ui.viewmodel.ProgrammedTripsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, itineraryItemDao: ItineraryItemDao): TripRepository = TripRepositoryImpl(tripDao, itineraryItemDao)

    @Provides
    @Singleton
    fun provideTripViewModel(trip: TripRepository): ProgrammedTripsViewModel = ProgrammedTripsViewModel(trip)

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)



    @Provides
    @Singleton
    fun provideSharedPrefsManager(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context
    ): SharedPrefsManager =
        SharedPrefsManager(sharedPreferences, context)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): PlanMyEscapeDatabase {
        return Room.databaseBuilder(
            context,
            PlanMyEscapeDatabase::class.java,
            "plan_my_escape_db"
        ).build()
    }

    @Provides
    fun provideTripDao(db: PlanMyEscapeDatabase) : TripDao = db.tripDao()

    @Provides
    fun provideItineraryItemDao(db: PlanMyEscapeDatabase) : ItineraryItemDao = db.itineraryItemDao()



}