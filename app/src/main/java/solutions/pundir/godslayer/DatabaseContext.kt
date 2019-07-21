package solutions.pundir.godslayer

import android.content.Context
import android.content.ContextWrapper
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.Log

import java.io.File

internal class DatabaseContext(base: Context) : ContextWrapper(base) {
    override fun getDatabasePath(name: String): File {
        return File("/data/user/0/solutions.pundir.godslayer/cache/0.db")
    }
    override fun openOrCreateDatabase(name: String, mode: Int, factory: SQLiteDatabase.CursorFactory, errorHandler: DatabaseErrorHandler?): SQLiteDatabase {
        return openOrCreateDatabase(name, mode, factory)
    }
}