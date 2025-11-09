package com.example.appnote.ui

import android.os.Bundle
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

class EditNoteActivity : AppCompatActivity() {
    private lateinit var edtTitle: TextInputEditText

    private lateinit var edtContent: TextInputEditText

    private lateinit var cbFavorite: CheckBox

    private lateinit var btnUpdate: TextView

    private lateinit var repository: NoteRepository

    private var currentNote : Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        setUpViews()
        btnUpdate.setOnClickListener {
            updateNote()
        }
    }

    fun setUpViews() {
        edtTitle = findViewById(R.id.edtTitle)
        edtContent = findViewById(R.id.edtContent)
        cbFavorite = findViewById(R.id.cbFavorite)
        btnUpdate = findViewById(R.id.btnUpdate)

        btnUpdate.isEnabled = true

        val dao = AppDatabase.getDatabase(this@EditNoteActivity).noteDao()
        repository = NoteRepository(dao)

        currentNote = intent.getParcelableExtra("note")

        if(currentNote != null) {
            edtTitle.setText(currentNote!!.title)
            edtContent.setText(currentNote!!.content)
            cbFavorite.isChecked = currentNote!!.isFavorite
        }
    }

    fun updateNote() {
        val title = edtTitle.text.toString()
        val content = edtContent.text.toString()
        val cbFavorite = cbFavorite.isChecked

        if(title.isEmpty()) {
            edtTitle.error = "Không được để trống"
            return
        }

        lifecycleScope.launch {
            val update = currentNote!!.copy(
                title = title,
                content = content,
                isFavorite = cbFavorite,
                date = System.currentTimeMillis()
            )
            repository.updateNote(update)
        }
        finish()
    }
}