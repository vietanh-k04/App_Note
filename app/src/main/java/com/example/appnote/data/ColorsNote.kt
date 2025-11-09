package com.example.appnote.data

import com.example.appnote.R

object ColorsNote {
    fun getColorNote() : List<Int> {
        val colors = listOf(
            R.color.note_blue,
            R.color.note_green,
            R.color.note_yellow,
            R.color.note_orange,
            R.color.note_pink,
            R.color.note_purple,
            R.color.note_mint,
            R.color.note_red,
            R.color.note_beige,
            R.color.note_teal,
        )
        return colors
    }
}