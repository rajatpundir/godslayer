package solutions.pundir.godslayer.Downloads.RecycleViewAdapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrentsQueue
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.R

class RecycleViewAdapterTorrentsQueue internal constructor(val context: Context?, val items: MutableList<GodslayerTorrent>, val parent_fragment : FragmentDownloadsTorrentsQueue) : RecyclerView.Adapter<RecycleViewAdapterTorrentsQueue.DownloadsItemViewHolder>() {
    private val inflater : LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_downloads_torrent, parent, false)
        return DownloadsItemViewHolder(itemView).listen { pos, _ ->
            val item = items.get(pos)
            // call to parent fragment here, if any.
            println(item.filename)
            // launch the intent to open file here.
            if (item.filename != "") {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.filename))
                intent.setDataAndType(Uri.parse(item.filename), "video/x-matroska")
                context?.startActivity(intent)
            }
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
        holder.initialize_view_holder(items, position)
        holder.deatilsButton.setOnClickListener { parent_fragment.show_torrent_stats(position)}
    }

    class DownloadsItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewDownloadsTorrentEpisodeItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_episode_item)
        val recyclerViewDownloadsTorrentSourceItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_source_item)
        val recyclerViewDownloadsTorrentStateItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_state)
        val recyclerViewDownloadsTorrentProgressPercentageItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_progress_percentage)
        val pauseOrResumeButton : Button = v.findViewById(R.id.button_downloads_torrent_pause_or_resume)
        val removeButton : Button = v.findViewById(R.id.button_downloads_torrent_remove)
        val deatilsButton : Button = v.findViewById(R.id.button_downloads_torrent_details)
        val downlaodProgressBar : ProgressBar = v.findViewById(R.id.progress_bar_downloads_torrent)
        lateinit var item : GodslayerTorrent
        fun initialize_view_holder(items: MutableList<GodslayerTorrent>, position: Int) {
            this.item = items[position]
            pauseOrResumeButton.setOnClickListener { item.pause_or_resume_session() }
            removeButton.setOnClickListener { item.stop_session() }
            downlaodProgressBar.max = 100
            downlaodProgressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN)
            update_view_holder()
        }
        fun update_view_holder() {
            recyclerViewDownloadsTorrentEpisodeItem.text = item.episode_name
            recyclerViewDownloadsTorrentSourceItem.text = item.source_name
            if (item.isPaused) {
                pauseOrResumeButton.text = "Resume"
            } else {
                pauseOrResumeButton.text = "Pause"
            }
            if (item.torrent_progress == 100) {
                recyclerViewDownloadsTorrentStateItem.text = "Finished"
                pauseOrResumeButton.visibility = View.GONE
            }
            recyclerViewDownloadsTorrentStateItem.text = item.torrent_state
            recyclerViewDownloadsTorrentProgressPercentageItem.text = item.torrent_progress.toString() + "%"
            downlaodProgressBar.progress = item.torrent_progress
        }
    }

}