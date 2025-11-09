package com.example.appnote.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appnote.NoteAdapter
import com.example.appnote.R
import com.example.appnote.data.AppDatabase
import com.example.appnote.data.NoteRepository
import kotlinx.coroutines.launch

class AllNotesFragment : Fragment() {
    private lateinit var repository: NoteRepository
    private lateinit var adapter: NoteAdapter
    private lateinit var rcvNote : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = AppDatabase.getDatabase(requireContext()).noteDao()
        repository = NoteRepository(dao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvNote = view.findViewById(R.id.rcvNote)
        adapter = NoteAdapter()
        rcvNote.adapter = adapter
        rcvNote.layoutManager = LinearLayoutManager(requireContext())

        adapter.onItemClick = { note->
            val intent = Intent(requireContext(), EditNoteActivity::class.java)
            intent.putExtra("note", note)
            startActivity(intent)
        }

        repository.getAllNotes().observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        deleteNote()
    }

    fun deleteNote() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.listNote[viewHolder.adapterPosition]

                lifecycleScope.launch {
                    repository.removeNote(note)
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rcvNote)
    }
}