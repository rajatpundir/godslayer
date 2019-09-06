package solutions.pundir.godslayer.Downloads.RecycleViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrentStatsFiles
import solutions.pundir.godslayer.R

class RecycleViewAdapterTorrentStatsFiles internal constructor(val context: Context?, val items: MutableList<String>, val parent_fragment : FragmentDownloadsTorrentStatsFiles) : RecyclerView.Adapter<RecycleViewAdapterTorrentStatsFiles.DownloadsItemViewHolder>() {
    private val inflater : LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_downloads_torrent_stats_files, parent, false)
        return DownloadsItemViewHolder(itemView).listen { pos, _ ->
            val item = items.get(pos)
            // call to parent fragment here, if any.
        }
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DownloadsItemViewHolder, position: Int) {
        holder.recyclerViewDownloadsTorrentStatsFileItem.text = items[position]
    }

    class DownloadsItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewDownloadsTorrentStatsFileItem : TextView = v.findViewById(R.id.recycler_view_item_downloads_torrent_stats_filename)
    }

}