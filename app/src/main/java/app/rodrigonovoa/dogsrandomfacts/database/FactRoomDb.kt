package app.rodrigonovoa.dogsrandomfacts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.rodrigonovoa.dogsrandomfacts.model.Fact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(FactModel::class, FavModel::class, UserModel::class), version = 1, exportSchema = false)
public abstract class FactRoomDb : RoomDatabase() {

    abstract fun factDao(): FactDAO
    abstract fun favDao(): FavDAO
    abstract fun userDao(): UserDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FactRoomDb? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FactRoomDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FactRoomDb::class.java,
                    "facts_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}