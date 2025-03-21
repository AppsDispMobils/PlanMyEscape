package com.example.sprint01

import com.example.sprint01.domain.repository.TripRepository
import com.example.sprint01.domain.repository.TripRepositoryImpl
import com.example.sprint01.ui.viewmodel.ProgrammedTripsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTripRepository(): TripRepository = TripRepositoryImpl()

    @Provides
    @Singleton
    fun provideTripViewModel(trip: TripRepository): ProgrammedTripsViewModel = ProgrammedTripsViewModel(trip)



}