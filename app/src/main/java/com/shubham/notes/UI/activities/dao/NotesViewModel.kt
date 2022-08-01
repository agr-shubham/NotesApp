package com.shubham.notes.UI.activities.dao

import androidx.lifecycle.*
import com.shubham.notes.UI.activities.entity.Notes
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Notes>> = repository.allWords

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: Notes) : Long {
        return repository.insert(note)
    }

    fun update(notes: Notes) = viewModelScope.launch{
        repository.update(notes)
    }

    fun getNote(id: Long) :Notes{
        return repository.getNote(id)
    }

    fun delete(id: Long) {
        repository.delete(id)
    }
}

class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}