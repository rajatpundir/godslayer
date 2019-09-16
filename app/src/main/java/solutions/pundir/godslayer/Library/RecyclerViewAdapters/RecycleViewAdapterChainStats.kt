package solutions.pundir.godslayer.Library.RecyclerViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.Library.Demonchain
import solutions.pundir.godslayer.Library.DemonslayerTorrent
import solutions.pundir.godslayer.Library.Fragments.FragmentsLibraryChains
import solutions.pundir.godslayer.R

class RecycleViewAdapterChainStats internal constructor(context: Context?, val items: MutableList<DemonslayerTorrent>, val parent_fragment : FragmentsLibraryChains) : RecyclerView.Adapter<RecycleViewAdapterChainStats.LibraryChainStatsItemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryChainStatsItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_library_chain_stats, parent, false)
        return LibraryChainStatsItemViewHolder(itemView).listen { pos, _ ->
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

    override fun onBindViewHolder(holder: LibraryChainStatsItemViewHolder, position: Int) {
        holder.recyclerViewLibraryChainNameItem.text = "Name"
        holder.recyclerViewLibraryChainMagnetUrlItem.text = "URL"
    }

    inner class LibraryChainStatsItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewLibraryChainNameItem: TextView = v.findViewById(R.id.recycler_view_item_library_chain_stats_node_name)
        val recyclerViewLibraryChainMagnetUrlItem: TextView = v.findViewById(R.id.recycler_view_item_library_chain_stats_node_magnet_url)
    }

}