package com.example.appnote.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.lifecycleScope
import com.example.appnote.R
import com.example.appnote.data.AppDatabase
import com.example.appnote.data.NoteRepository
import com.example.appnote.pref.UserPrefs
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {
    private lateinit var prefs: UserPrefs

    private lateinit var linearDelete: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = UserPrefs(requireContext())

        val themeSwitch = view.findViewById<SwitchCompat>(R.id.switchDarkMode)

        themeSwitch.isChecked = prefs.isDarkMode()

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.setDarkMode(isChecked)

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        linearDelete = view.findViewById(R.id.linearDelete)

        val dao = AppDatabase.getDatabase(requireContext()).noteDao()
        val repo = NoteRepository(dao)

        linearDelete.setOnClickListener {
            showDeleteDialog(repo)
        }
    }

    fun showDeleteDialog(repository: NoteRepository) {
        val diglogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_delete_note, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(diglogView).create()

        val btnAccept = diglogView.findViewById<Button>(R.id.btn_accept)
        val btnCancel = diglogView.findViewById<Button>(R.id.btn_denied)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnAccept.setOnClickListener {
            lifecycleScope.launch {
                repository.deleteNotes()
            }
            dialog.dismiss()
        }
        dialog.show()
    }
}