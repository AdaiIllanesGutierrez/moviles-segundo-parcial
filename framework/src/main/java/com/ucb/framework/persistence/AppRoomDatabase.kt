package com.ucb.framework.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ucb.framework.persistence.IBookDAO
import com.ucb.framework.persistence.GitAccount
import com.ucb.framework.persistence.BookEntity

@Database(
    entities = [GitAccount::class, BookEntity::class],
    version = 2,  // Incrementado por la nueva entidad
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun githubDao(): IGitAccountDAO
    abstract fun bookDao(): IBookDAO

    companion object {
        @Volatile
        private var Instance: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "ucb_database"  // Nombre unificado para la base de datos
                )
                    .fallbackToDestructiveMigration()  // Manejo simple de migraciones
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

