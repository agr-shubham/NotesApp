package com.shubham.notes.UI.activities.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.shubham.notes.R
import com.shubham.notes.UI.activities.MainActivity
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.NoteTitleCardBinding
import java.text.SimpleDateFormat
import java.util.*


class ListNotesAdapter() : RecyclerView.Adapter<ListNotesAdapter.ViewHolder>(){

    private var mContext: Context? = null

    private var notes = ArrayList<Notes>()

    fun filterList(filterllist: ArrayList<Notes>) {
        notes = filterllist
        notifyDataSetChanged()
    }

    fun getNotesSize():Int{
        return notes.size
    }


    inner  class ViewHolder(val binding: NoteTitleCardBinding): RecyclerView.ViewHolder(binding.root)

    fun updateList(newList:List<Notes>)
    {
        Log.i("List Changed", "updateList: $newList")
        notes.clear()

        notes.addAll(newList)

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context
        val binding = NoteTitleCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListNotesAdapter.ViewHolder, position: Int) {

        holder.apply {
            if(notes[position].title.length!=0)
            binding.noteTitle.text = notes[position].title
            else
                binding.noteTitle.text = "Untitled"
            if(notes[position].note.length!=0)
                binding.noteFirstLine.text = notes[position].note
            else
                binding.noteFirstLine.text = "Blank"
            binding.noteLastModified.text =
                "Last Modified: " + SimpleDateFormat("dd-MM-yy HH:mm").format(Date(notes[position].updateTime))
            itemView.setOnClickListener {
                (mContext as MainActivity).loadEditNoteFragment(notes[position].id)
            }
            binding.itemPopupMenu.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, binding.itemPopupMenu)
                popupMenu.menuInflater.inflate(R.menu.list_notes_item_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_delete ->
                            (mContext as MainActivity).deleteButton(notes[position])
                        R.id.menu_share ->{
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, notes[position].title+"\n"+notes[position].note)
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            itemView.context.startActivity(shareIntent)
                        }
                    }
                    true
                })
                popupMenu.show()
            }


        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
