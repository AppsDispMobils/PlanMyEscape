package com.example.PlanMyEscape.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.PlanMyEscape.BuildConfig
import com.example.PlanMyEscape.data.SharedPrefsManager
import com.example.PlanMyEscape.data.local.PlanMyEscapeDatabase
import com.example.PlanMyEscape.data.local.dao.ItineraryItemDao
import com.example.PlanMyEscape.data.local.dao.TripDao
import com.example.PlanMyEscape.data.local.dao.UserDao
import com.example.PlanMyEscape.data.remote.api.HotelApiService
import com.example.PlanMyEscape.data.repository.AuthenticationRepositoryImpl
import com.example.PlanMyEscape.data.repository.HotelRepositoryImpl
import com.example.PlanMyEscape.domain.repository.TripRepository
import com.example.PlanMyEscape.data.repository.TripRepositoryImpl
import com.example.PlanMyEscape.domain.repository.AuthenticationRepository
import com.example.PlanMyEscape.domain.repository.HotelRepository
import com.example.PlanMyEscape.ui.viewmodel.ProgrammedTripsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(firebaseAuth: FirebaseAuth, userDao: UserDao): AuthenticationRepository =
        AuthenticationRepositoryImpl(firebaseAuth, userDao)

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, itineraryItemDao: ItineraryItemDao, authenticationRepository: AuthenticationRepository): TripRepository =
        TripRepositoryImpl(tripDao, itineraryItemDao, authenticationRepository)


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

    @Provides
    fun provideUserDao(db: PlanMyEscapeDatabase) : UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
    @Provides
    @Singleton
    fun provideOkHttpClient( loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHotelApi(
        okHttpClient: OkHttpClient
    ): HotelApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOTELS_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHotelRepository(api: HotelApiService): HotelRepository = HotelRepositoryImpl(api)



}