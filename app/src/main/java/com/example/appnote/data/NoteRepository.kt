package com.example.appnote.data

import androidx.lifecycle.LiveData

class NoteRepository(private val dao: NoteDao) {

    fun getAllNotes() : LiveData<List<Note>> = dao.getAllNotes()

    fun getFavoriteNotes() : LiveData<List<Note>> = dao.getFavoriteNotes()

    suspend fun deleteNotes() = dao.deleteAllNotes()

    suspend fun addNote(note: Note) = dao.insertNote(note)

    suspend fun updateNote(note: Note) = dao.updateNote(note)


    suspend fun removeNote(note: Note) = dao.deleteNote(note)

}