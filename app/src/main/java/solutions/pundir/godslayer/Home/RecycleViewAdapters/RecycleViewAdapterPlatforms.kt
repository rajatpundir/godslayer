package solutions.pundir.godslayer.Home.RecycleViewAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.R

class RecycleViewAdapterPlatforms internal constructor(context: Context?, val items: MutableList<Triple<Long, Long, String>>, val appStateHome : StateAppHome) : RecyclerView.Adapter<RecycleViewAdapterPlatforms.HomeItemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_home, parent, false)
        return HomeItemViewHolder(itemView).listen { pos, _ ->
            val item = items.get(pos)
            appStateHome.set_platform_id(item.first, item.second)
        }
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.recyclerViewHomeItem.text = items[position].third
        println(items[position])
    }

    inner class HomeItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewHomeItem: TextView = v.findViewById(R.id.recycler_view_home_item)
    }

}