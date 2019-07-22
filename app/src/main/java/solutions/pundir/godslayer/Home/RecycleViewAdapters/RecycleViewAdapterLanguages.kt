package solutions.pundir.godslayer.Home.RecycleViewAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.R

class RecycleViewAdapterLanguages internal constructor(context: Context, val items: MutableList<String>) : RecyclerView.Adapter<RecycleViewAdapterLanguages.HomeItemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_home, parent, false)
        return HomeItemViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.recyclerViewHomeItem.text = items[position]
    }

    inner class HomeItemViewHolder(v : View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val recyclerViewHomeItem: TextView = v.findViewById(R.id.recycler_view_home_item)
        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }
    }

}