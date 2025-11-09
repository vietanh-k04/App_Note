package com.example.appnote.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appnote.R
import com.example.appnote.data.AppDatabase
import com.example.appnote.data.Note
import com.example.appnote.data.NoteRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {
    private lateinit var edtTitle : TextInputEditText
    private lateinit var edtContent : TextInputEditText
    private lateinit var cbFavorite : CheckBox

    private lateinit var btnAdd : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setUpViews()

        btnAdd.setOnClickListener {
            val note = Note(title = edtTitle.text.toString(), content = edtContent.text.toString(), date = System.currentTimeMillis(), isFavorite = cbFavorite.isChecked)
            lifecycleScope.launch {
                val dao = AppDatabase.getDatabase(this@AddNoteActivity).noteDao()
                val repo = NoteRepository(dao)
                repo.addNote(note)
                finish()
            }
        }
    }

    fun setUpViews() {
        edtTitle = findViewById(R.id.edtTitle)
        edtContent = findViewById(R.id.edtContent)
        cbFavorite = findViewById(R.id.cbFavorite)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.isEnabled = true
    }
}