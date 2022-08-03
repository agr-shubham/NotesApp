package com.shubham.notes.UI.activities.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shubham.notes.UI.activities.entity.Notes
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    val allNotes: LiveData<List<Notes>> = repository.allNotes

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