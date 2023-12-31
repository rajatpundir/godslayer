package solutions.pundir.godslayer.Home.RecycleViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.Home.Fragments.FargmentHomeSources
import solutions.pundir.godslayer.R

class RecycleViewAdapterSources internal constructor(context: Context?, val items: MutableList<Triple<Long, Long, String>>, val parent_fragment : FargmentHomeSources) : RecyclerView.Adapter<RecycleViewAdapterSources.HomeItemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_home_source, parent, false)
        return HomeItemViewHolder(itemView).listen { pos, _ ->
            val item = items.get(pos)
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
        holder.recyclerViewHomeSourceItemCheckbox.text = items[position].third
        holder.mid = items[position].first
        holder.rid = items[position].second
    }

    class HomeItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewHomeSourceItemCheckbox : CheckBox = v.findViewById(R.id.recycler_view_home_source_item_checkbox)
        var mid = 0L
        var rid = 0L
    }

}