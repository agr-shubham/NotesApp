package com.shubham.notes.UI.activities.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.shubham.notes.R
import com.shubham.notes.UI.activities.HomeActivity
import com.shubham.notes.UI.activities.ListNotesActivity
import com.shubham.notes.UI.activities.entity.Notes
import com.shubham.notes.databinding.NoteTitleCardBinding
import java.text.SimpleDateFormat
import java.util.*


class ListNotesAdapter() : RecyclerView.Adapter<ListNotesAdapter.ViewHolder>(){

    private var mContext: Context? = null

    private val notes = ArrayList<Notes>()



    inner  class ViewHolder(val binding: NoteTitleCardBinding): RecyclerView.ViewHolder(binding.root)

    fun updateList(newList:List<Notes>)
    {
        Log.i("List Changed", "updateList: $newList")
        notes.clear()

        notes.addAll(newList)
        //notify data change
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
            binding.noteTitle.text = getTruncatedTitle(notes[position].title)
            binding.noteFirstLine.text = getTruncatedContent(notes[position].note)
            binding.noteLastModified.text =
                "Last Modified: " + SimpleDateFormat("dd-MM-yy HH:mm").format(Date(notes[position].updateTime))
            itemView.setOnClickListener {
                val b = Bundle()
                b.putLong("id", notes[position].id)

                val productIntent = Intent(itemView.context, HomeActivity::class.java)
                productIntent.putExtras(b)
                itemView.context.startActivity(productIntent)

            }
            binding.itemPopupMenu.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, binding.itemPopupMenu)
                popupMenu.menuInflater.inflate(R.menu.list_notes_item_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_delete ->
                            (mContext as ListNotesActivity).deleteButton(notes[position])
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

    private fun getTruncatedTitle(title: String): String {

        if(title.isEmpty())
        {
            return "Untitled"
        }
        if(title.length>30)
        {
            return title.substring(0,30)+"..."
        }
        else
        {
            return title.substring(0,  title.length)
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }
    private fun getTruncatedContent(content : String) : String{
        val length : Int = content.length
        var truncatedContent=""
        if(length==0)
            return "Empty"
        else{
            val posLineBreak: Int = content.indexOf('\n')
            var firstLine:String = content
            var tbc=0
            if(posLineBreak!=-1)
            {
                tbc=1
                firstLine=content.substring(0,posLineBreak)
            }
            if(firstLine.length>35)
            {
                tbc=1
                truncatedContent+= firstLine.substring(0,35)
            }
            else
            {
                truncatedContent+= firstLine.substring(0,  firstLine.length)
            }
            if(tbc==1)
                truncatedContent+="..."
            return truncatedContent
        }
    }
}