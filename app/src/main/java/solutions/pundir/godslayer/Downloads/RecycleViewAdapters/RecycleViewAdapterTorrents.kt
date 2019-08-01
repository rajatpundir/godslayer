package solutions.pundir.godslayer.Downloads.RecycleViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrents
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.R

class RecycleViewAdapterTorrents internal constructor(context: Context?, val items: MutableList<GodslayerTorrent>, val parent_fragment : FragmentDownloadsTorrents) : RecyclerView.Adapter<RecycleViewAdapterTorrents.HomeItemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_downloads_torrent, parent, false)
        return HomeItemViewHolder(itemView).listen { pos, _ ->
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

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.recyclerViewDownloadsTorrentEpisodeItem.text = items[position].episode_name
        holder.recyclerViewDownloadsTorrentSourceItem.text = items[position].source_name
        holder.recyclerViewDownloadsTorrentStateItem.text = items[position].torrent_state
        holder.startButton.setOnClickListener { items[position].toast_magnet_url() }
    }

    inner class HomeItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewDownloadsTorrentEpisodeItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_episode_item)
        val recyclerViewDownloadsTorrentSourceItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_source_item)
        val recyclerViewDownloadsTorrentStateItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_state)
        val startButton : Button = v.findViewById(R.id.button_downloads_torrent_start)
    }

}