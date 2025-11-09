package com.example.appnote.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.example.appnote.R
import com.example.appnote.ViewPagerAdapter
import com.example.appnote.pref.UserPrefs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var vpPage: ViewPager2
    private lateinit var bnvPage: BottomNavigationView

    private lateinit var btnAddNote: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        setThemeMode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()

        bnvPage.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_all -> vpPage.currentItem = 0
                R.id.nav_favorite -> vpPage.currentItem = 1
                R.id.nav_setting -> vpPage.currentItem = 2
            }
            return@setOnItemSelectedListener true
        }

        vpPage.isUserInputEnabled = false

        btnAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    fun setUpViews() {
        vpPage = findViewById(R.id.vpPage)
        bnvPage = findViewById(R.id.bnvPage)
        adapter = ViewPagerAdapter(this)
        vpPage.adapter = adapter
        btnAddNote = findViewById(R.id.fabAdd)
    }

    fun setThemeMode() {
        val prefs = UserPrefs(this)

        if (prefs.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}