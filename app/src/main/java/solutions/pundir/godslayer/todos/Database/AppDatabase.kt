package solutions.pundir.godslayer.todos.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import solutions.pundir.godslayer.todos.Dao.*
import solutions.pundir.godslayer.todos.Entity.*


/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(
    entities = [EntityModules::class, EntityLanguages::class, EntityPlatforms::class, EntityPublishers::class, EntityPlaylists::class, EntityEpisodes::class, EntitySources::class, EntityMagnetLinks::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun modulesDao() : DaoModules
    abstract fun languagesDao() : DaoLanguages
    abstract fun platformsDao() : DaoPlatforms
    abstract fun publishersDao() : DaoPublishers
    abstract fun playlistsDao() : DaoPlaylists
    abstract fun episodesDao() : DaoEpisodes
    abstract fun sourcesDao() : DaoSources
    abstract fun magnetLinksDao() : DaoMagnetLinks

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it, or if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"room_database")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                /**
                 * Override the onOpen method to populate the database.
                 * For this sample, we clear the database every time it is created or opened.
                 */
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database -> scope.launch { populateDatabase(database.modulesDao())}}
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(modulesDao: DaoModules) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            // modulesDao.deleteAll()
            // var modulesDao: DaoModules
            var module = EntityModules(1, "demonslayer")
            modulesDao.insert(module)
            module = EntityModules(2, "asdfasdf")
            modulesDao.insert(module)
            module = EntityModules(3, "brgsba")
            modulesDao.insert(module)
        }
    }

}