package com.example.appnote.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appnote.NoteAdapter
import com.example.appnote.R
import com.example.appnote.data.AppDatabase
import com.example.appnote.data.NoteRepository

class FavoritesFragment : Fragment() {
    private lateinit var repository: NoteRepository
    private lateinit var adapter: NoteAdapter
    private lateinit var rcvFavoriteNote: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = AppDatabase.getDatabase(requireContext()).noteDao()
        repository = NoteRepository(dao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvFavoriteNote = view.findViewById(R.id.rcvFavoriteNotes)

        adapter = NoteAdapter()
        rcvFavoriteNote.adapter = adapter

        adapter.onItemClick = { note ->
            val intent = Intent(requireContext(), EditNoteActivity::class.java)
            intent.putExtra("note", note)
            startActivity(intent)
        }

        repository.getFavoriteNotes().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}