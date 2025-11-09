package com.example.appnote

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appnote.data.ColorsNote
import com.example.appnote.data.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NoteAdapter(var listNote : List<Note> = emptyList(), var onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        holder.onBind(listNote[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listNote[position])
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Note>) {
        listNote = newList
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        private var tvDate = itemView.findViewById<TextView>(R.id.tv_date)
        private var imgFavorite = itemView.findViewById<ImageView>(R.id.img_favorite)

        fun onBind(note: Note) {
            tvTitle.text = note.title
            tvDate.text = formatDate(note.date)
            imgFavorite.visibility = if(note.isFavorite) View.VISIBLE else View.GONE

            val listColors = ColorsNote.getColorNote()
            val colorIndex = note.id % listColors.size
            val colorRes = listColors[colorIndex]

            itemView.setBackgroundColor(itemView.context.getColor(colorRes))
        }

        private fun formatDate(time: Long) : String {
            val d = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return d.format(java.util.Date(time))
        }
    }


}