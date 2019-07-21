package solutions.pundir.godslayer.todos.Repository

import androidx.lifecycle.LiveData
import solutions.pundir.godslayer.todos.Dao.DaoModules
import solutions.pundir.godslayer.todos.Entity.EntityModules


/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class AppRepository(private val modulesDao: DaoModules) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<EntityModules>> = modulesDao.getAll()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    // This ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    suspend fun insert(module: EntityModules) {
        modulesDao.insert(module)
    }

}