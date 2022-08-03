package com.shubham.notes.UI.activities.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shubham.notes.R
import com.shubham.notes.UI.activities.HomeActivity
import com.shubham.notes.UI.activities.ListNotesActivity
import com.shubham.notes.UI.activities.entity.Notes
import java.text.SimpleDateFormat
import java.util.*


class ListNotesAdapter() : RecyclerView.Adapter<ListNotesAdapter.ViewHolder>(){

    private var mContext: Context? = null

    private val notes = ArrayList<Notes>();
    inner  class ViewHolder( itemView : View): RecyclerView.ViewHolder(itemView){
        val titleTv : TextView = itemView.findViewById(R.id.note_title)
        // 11cf  inflated memory

        val noteFirstLineTv: TextView = itemView.findViewById(R.id.note_first_line)

        val lastModified: TextView = itemView.findViewById(R.id.note_last_modified)
        val menu: Button = itemView.findViewById<Button>(R.id.item_popup_menu)
    }

    fun updateList(newList:List<Notes>)
    {
        Log.i("List Changed", "updateList: $newList")
        notes.clear()

        notes.addAll(newList)
        //notify data change
        notifyDataSetChanged()
    }

    // MainActivity ( context ) -> member of mainactivity ViewGroup.context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNotesAdapter.ViewHolder {
        mContext=parent.context
        val v = LayoutInflater.from( parent.context  ).inflate(R.layout.note_title_card,parent,false)
        return ViewHolder( v )
    }

    override fun onBindViewHolder(holder: ListNotesAdapter.ViewHolder, position: Int) {

//         holder.fName.text = title[ position ]
//         holder.fDesc.text = descriptions [ position ]

        holder.apply {
            titleTv.text = getTruncatedTitle(notes!![position].title)
//            noteFirstLineTv.text = notes!![position].note.substring(0,min(40, notes!![position].note.length))
            noteFirstLineTv.text = getTruncatedContent(notes!![position].note)
            lastModified.text =
                "Lost Modified: " + SimpleDateFormat("dd-MM-yy HH:mm").format(Date(notes!![position].updateTime))
            itemView.setOnClickListener { v: View ->
                var b: Bundle = Bundle();
                b.putLong("id", notes[position].id)

                val productIntent = Intent(itemView.context, HomeActivity::class.java)
                productIntent.putExtras(b)
                itemView.context.startActivity(productIntent)

            }
            menu.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(itemView.context, menu)
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

        if(title.length==0)
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
        return notes!!.size
    }
    private fun getTruncatedContent(content : String) : String{
        val length : Int = content.length
        var truncatedContent:String="";
        if(length==0)
            return "Empty"
        else{
            val posLineBreak: Int = content.indexOf('\n');
            var firstLine:String = content;
            var tbc:Int=0
            if(posLineBreak!=-1)
            {
                tbc=1;
                firstLine=content.substring(0,posLineBreak);
            }
            if(firstLine.length>35)
            {
                tbc=1;
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