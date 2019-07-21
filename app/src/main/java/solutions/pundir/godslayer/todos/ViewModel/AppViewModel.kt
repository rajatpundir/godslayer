package solutions.pundir.godslayer.todos.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solutions.pundir.godslayer.todos.Repository.AppRepository
import solutions.pundir.godslayer.todos.Database.AppDatabase
import solutions.pundir.godslayer.todos.Entity.EntityModules


/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<EntityModules>>

    init {
        val modulesDao = AppDatabase.getDatabase(application, viewModelScope).modulesDao()
        repository = AppRepository(modulesDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(module: EntityModules) = viewModelScope.launch {
        repository.insert(module)
    }

}
