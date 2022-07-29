package com.shubham.notes.UI.activities.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shubham.notes.R
import com.shubham.notes.UI.activities.entity.Notes
import java.lang.Integer.min

class ListNotesAdapter(private var notes: Array<Notes>) : RecyclerView.Adapter<ListNotesAdapter.ViewHolder>(){
    inner  class ViewHolder( itemView : View): RecyclerView.ViewHolder(itemView){
        // 11cf  inflated memory

        val titleTv : TextView = itemView.findViewById(R.id.note_title)
        val noteFirstLineTv: TextView = itemView.findViewById(R.id.note_first_line)
//
//        init {
//
//            itemView.setOnClickListener {
//                    v: View ->
//                var b: Bundle = Bundle();
//                b.putString("product",pName.text.toString())
//
//                val productIntent = Intent(itemView.context, IngredientActivity::class.java)
//                productIntent.putExtras(b)
//                itemView.context.startActivity(productIntent)
//
//                //intent to move to ingredients page
//            }
//        }


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
            noteFirstLineTv.text = notes!![position].note.substring(0,min(40, notes!![position].note.length))
        }


    }

    override fun getItemCount(): Int {
        return notes!!.size
    }
}