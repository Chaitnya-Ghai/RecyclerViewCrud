package cg.tutorials.recyclerviewcrud

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(context: Context,var infoList:ArrayList<Info>,var recyclerInterface: RecyclerInterface) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var tvName = view.findViewById<TextView>(R.id.tvName)
        var tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        var updateBtn = view.findViewById<Button>(R.id.updateBtn)
        var deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return infoList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text=infoList[position].title
        holder.tvDescription.text=infoList[position].description
        holder.updateBtn.setOnClickListener {
            recyclerInterface.update(position,holder.tvName.text.trim().toString(),holder.tvDescription.text.trim().toString())
        }
        holder.deleteBtn.setOnClickListener {
            recyclerInterface.delete(position)
        }

    }
    fun addValue(addValue:ArrayList<Info>){
        infoList=addValue
        notifyDataSetChanged()
    }
}