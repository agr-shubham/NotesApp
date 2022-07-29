package com.shubham.notes.UI.activities.adapters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shubham.notes.R
import com.shubham.notes.UI.activities.HomeActivity
import com.shubham.notes.UI.activities.entity.Notes
import java.lang.Integer.min

class ListNotesAdapter() : RecyclerView.Adapter<ListNotesAdapter.ViewHolder>(){
    private val notes = ArrayList<Notes>();
    inner  class ViewHolder( itemView : View): RecyclerView.ViewHolder(itemView){
        val titleTv : TextView = itemView.findViewById(R.id.note_title)
        // 11cf  inflated memory

        val noteFirstLineTv: TextView = itemView.findViewById(R.id.note_first_line)


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
        val v = LayoutInflater.from( parent.context  ).inflate(R.layout.note_title_card,parent,false)
        return ViewHolder( v )
    }

    override fun onBindViewHolder(holder: ListNotesAdapter.ViewHolder, position: Int) {

//         holder.fName.text = title[ position ]
//         holder.fDesc.text = descriptions [ position ]

        holder.apply {
            titleTv.text = notes!![position].title
            titleTv.text = getTruncatedTitle(notes!![position].title)
//            noteFirstLineTv.text = notes!![position].note.substring(0,min(40, notes!![position].note.length))
            noteFirstLineTv.text = getTruncatedContent(notes!![position].note)
            itemView.setOnClickListener {
                    v: View ->
                var b: Bundle = Bundle();
                b.putLong("id", notes[position].id)

                val productIntent = Intent(itemView.context, HomeActivity::class.java)
                productIntent.putExtras(b)
                itemView.context.startActivity(productIntent)

            }
        }


    }

    private fun getTruncatedTitle(title: String): String {

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
            if(posLineBreak!=-1)
            {
                firstLine=content.substring(0,posLineBreak);
            }
            if(firstLine.length>35)
            {
                return firstLine.substring(0,35)+"..."
            }
            else
            {
                return firstLine.substring(0,  firstLine.length)
            }
        }
    }
}