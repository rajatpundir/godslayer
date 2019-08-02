package solutions.pundir.godslayer.Downloads.RecycleViewAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.toast
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrents
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.R

class RecycleViewAdapterTorrents internal constructor(context: Context?, val items: MutableList<GodslayerTorrent>, val parent_fragment : FragmentDownloadsTorrents) : RecyclerView.Adapter<RecycleViewAdapterTorrents.DownloadsItemViewHolder>() {
    private val inflater : LayoutInflater = LayoutInflater.from(context)
    lateinit var holder : DownloadsItemViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_downloads_torrent, parent, false)
        return DownloadsItemViewHolder(itemView).listen { pos, _ ->
            val item = items.get(pos)
            // call to parent fragment here, if any.
            println(item.toString())
            // launch the intent open file here.
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
        this.holder = holder
        items[position].callback_from_recycler_view(this@RecycleViewAdapterTorrents)
        holder.recyclerViewDownloadsTorrentEpisodeItem.text = items[position].episode_name
        holder.recyclerViewDownloadsTorrentSourceItem.text = items[position].source_name
        holder.recyclerViewDownloadsTorrentStateItem.text = items[position].torrent_state
        println("_@__@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2")
        println(position)
        holder.pauseOrResumeButton.setOnClickListener { items[position].pause_or_resume_session() }
        holder.removeButton.setOnClickListener { items[position].stop_session() }
        holder.downlaodProgressBar.max = 100
        holder.downlaodProgressBar.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    fun update_torrent_state(torrent_state : String) {
        holder.recyclerViewDownloadsTorrentStateItem.text = torrent_state
    }

    fun update_torrent_progress(progress : Float) {
        holder.downlaodProgressBar.progress = (progress * 100).toInt()
        holder.recyclerViewDownloadsTorrentProgressPercentageItem.text = (progress * 100).toInt().toString() + "%"
        if ((progress * 100).toInt() == 100) {
            holder.recyclerViewDownloadsTorrentStateItem.text = "Finished"
            holder.pauseOrResumeButton.visibility = View.GONE
        }
    }

    fun call_parent_to_remove_item(module_id : Long, source_id : Long) {
        parent_fragment.remove_torrent_and_update_adapter(module_id, source_id)
    }

    inner class DownloadsItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewDownloadsTorrentEpisodeItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_episode_item)
        val recyclerViewDownloadsTorrentSourceItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_source_item)
        val recyclerViewDownloadsTorrentStateItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_state)
        val recyclerViewDownloadsTorrentProgressPercentageItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_progress_percentage)
        val pauseOrResumeButton : Button = v.findViewById(R.id.button_downloads_torrent_pause_or_resume)
        val removeButton : Button = v.findViewById(R.id.button_downloads_torrent_remove)
        val downlaodProgressBar : ProgressBar = v.findViewById(R.id.progress_bar_downloads_torrent)
    }

}