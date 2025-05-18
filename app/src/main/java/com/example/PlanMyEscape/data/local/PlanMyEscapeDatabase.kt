package com.example.PlanMyEscape.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.PlanMyEscape.data.local.dao.ItineraryItemDao
import com.example.PlanMyEscape.data.local.dao.TripDao
import com.example.PlanMyEscape.data.local.dao.UserDao
import com.example.PlanMyEscape.data.local.entity.ImageEntity
import com.example.PlanMyEscape.data.local.entity.ItineraryItemEntity
import com.example.PlanMyEscape.data.local.entity.TripEntity
import com.example.PlanMyEscape.data.local.entity.UserEntity

@Database(
    entities = [TripEntity::class, ItineraryItemEntity::class, UserEntity::class, ImageEntity::class],
    version = 2,
    exportSchema = false
)
abstract class PlanMyEscapeDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun itineraryItemDao(): ItineraryItemDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: PlanMyEscapeDatabase? = null

        // Define la migración de la versión 1 a la 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Crear la nueva tabla 'image'
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS image (" +
                            "uri TEXT NOT NULL, " + // uri primero
                            "tripId INTEGER NOT NULL, " +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + // id al final
                            "FOREIGN KEY(tripId) REFERENCES trips(id) ON DELETE CASCADE)" // Nombre de tabla en minúsculas y plural
                )

                // Ya tienes un índice definido en la entidad, pero es bueno incluirlo aquí también
                database.execSQL("CREATE INDEX IF NOT EXISTS index_image_tripId ON image (tripId)")
            }
        }

        fun get(context: Context): PlanMyEscapeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PlanMyEscapeDatabase::class.java,
                    "plan_my_escape_db"
                )
                    .addMigrations(MIGRATION_1_2) // Añade la migración aquí
                    .build().also { INSTANCE = it }
            }
    }
}