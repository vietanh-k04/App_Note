package com.example.appnote

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appnote.ui.AllNotesFragment
import com.example.appnote.ui.FavoritesFragment
import com.example.appnote.ui.SettingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AllNotesFragment()
            1 -> FavoritesFragment()
            2 -> SettingFragment()
            else -> AllNotesFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}