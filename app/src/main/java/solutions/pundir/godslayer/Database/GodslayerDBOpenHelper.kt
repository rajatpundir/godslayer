package solutions.pundir.godslayer.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GodslayerDBOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?, filename : String) : SQLiteOpenHelper(
    DatabaseContext(
        context
    ), filename, factory, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS MODULES(ID INTEGER PRIMARY KEY, NAME TEXT UNIQUE NOT NULL);")
        db.execSQL("CREATE TABLE IF NOT EXISTS LANGUAGES(MODULE_ID INTEGER NOT NULL, ID INTEGER NOT NULL, NAME TEXT NOT NULL, FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID), PRIMARY KEY(MODULE_ID, ID), UNIQUE(MODULE_ID, NAME));")
        db.execSQL("CREATE TABLE IF NOT EXISTS PLATFORMS(MODULE_ID INTEGER NOT NULL, ID INTEGER NOT NULL, IMAGE_URL TEXT, NAME TEXT NOT NULL, PARENT_ID INTEGER NOT NULL, FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID), PRIMARY KEY(MODULE_ID, ID), FOREIGN KEY(MODULE_ID, PARENT_ID) REFERENCES LANGUAGES(MODULE_ID, ID), UNIQUE(MODULE_ID, PARENT_ID, NAME));")
        db.execSQL("CREATE TABLE IF NOT EXISTS PUBLISHERS(MODULE_ID INTEGER NOT NULL, ID INTEGER NOT NULL, IMAGE_URL TEXT, NAME TEXT NOT NULL, PARENT_ID INTEGER NOT NULL, FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID), PRIMARY KEY(MODULE_ID, ID), FOREIGN KEY(MODULE_ID, PARENT_ID) REFERENCES PLATFORMS(MODULE_ID, ID), UNIQUE(MODULE_ID, PARENT_ID, NAME));")
        db.execSQL("CREATE TABLE IF NOT EXISTS PLAYLISTS(MODULE_ID INTEGER NOT NULL, ID INTEGER NOT NULL, IMAGE_URL TEXT, NAME TEXT NOT NULL, PARENT_ID INTEGER NOT NULL, FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID), PRIMARY KEY(MODULE_ID, ID), FOREIGN KEY(MODULE_ID, PARENT_ID) REFERENCES PUBLISHERS(MODULE_ID, ID), UNIQUE(MODULE_ID, PARENT_ID, NAME));")
        db.execSQL("CREATE TABLE IF NOT EXISTS EPISODES(MODULE_ID INTEGER NOT NULL, ID INTEGER NOT NULL, IMAGE_URL TEXT, NAME TEXT NOT NULL, PARENT_ID INTEGER NOT NULL, FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID), PRIMARY KEY(MODULE_ID, ID), FOREIGN KEY(MODULE_ID, PARENT_ID) REFERENCES PLAYLISTS(MODULE_ID, ID), UNIQUE(MODULE_ID, PARENT_ID, NAME));")
        db.execSQL("CREATE TABLE IF NOT EXISTS MAGNET_LINKS(MODULE_ID INTEGER NOT NULL, ID INTEGER NOT NULL, MAGNET_URL TEXT NOT NULL, FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID), PRIMARY KEY(MODULE_ID, ID), UNIQUE(MODULE_ID, MAGNET_URL));")
        db.execSQL("CREATE TABLE IF NOT EXISTS SOURCES(MODULE_ID INTEGER NOT NULL, ID INTEGER NOT NULL, MAGNET_LINK_ID INTEGER NOT NULL, FILE_NUM INTEGER NOT NULL DEFAULT 0, NAME TEXT NOT NULL, PARENT_ID INTEGER NOT NULL, FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID), PRIMARY KEY(MODULE_ID, ID), FOREIGN KEY(MODULE_ID, PARENT_ID) REFERENCES EPISODES(MODULE_ID, ID), UNIQUE(MODULE_ID, PARENT_ID, NAME), FOREIGN KEY(MODULE_ID, MAGNET_LINK_ID) REFERENCES MAGNET_LINKS(MODULE_ID, ID));")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    fun getModules() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM MODULES ORDER BY ID DESC", null)
    }

    fun getLanguages() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM LANGUAGES ORDER BY ID DESC", null)
    }

    fun getPlatforms() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM PLATFORMS ORDER BY ID DESC", null)
    }

    fun getPublishers() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM PUBLISHERS ORDER BY ID DESC", null)
    }

    fun getPlaylists() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM PLAYLISTS ORDER BY ID ASC", null)
    }

    fun getEpisodes() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM EPISODES ORDER BY ID ASC", null)
    }

    fun getSources() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM SOURCES ORDER BY ID DESC", null)
    }

    fun getMagnetLinks() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM MAGNET_LINKS ORDER BY ID DESC", null)
    }

}