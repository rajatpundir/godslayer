package solutions.pundir.godslayer.Database

import android.content.Context
import android.content.ContextWrapper
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.Log

import java.io.File

internal class DatabaseContext(base: Context) : ContextWrapper(base) {
    override fun getDatabasePath(name: String): File {
        println(name)
        println("##################################################################################################")
        return File("/data/user/0/solutions.pundir.godslayer/cache/" + name)
    }
    override fun openOrCreateDatabase(name: String, mode: Int, factory: SQLiteDatabase.CursorFactory, errorHandler: DatabaseErrorHandler?): SQLiteDatabase {
        return openOrCreateDatabase(name, mode, factory)
    }
}