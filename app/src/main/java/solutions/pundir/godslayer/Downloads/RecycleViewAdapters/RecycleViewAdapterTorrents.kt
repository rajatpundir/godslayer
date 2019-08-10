package solutions.pundir.godslayer.Downloads.RecycleViewAdapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.toast
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrents
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.R
import java.io.File

class RecycleViewAdapterTorrents internal constructor(val context: Context?, val items: MutableList<GodslayerTorrent>, val parent_fragment : FragmentDownloadsTorrents) : RecyclerView.Adapter<RecycleViewAdapterTorrents.DownloadsItemViewHolder>() {
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
    }

    class DownloadsItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewDownloadsTorrentEpisodeItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_episode_item)
        val recyclerViewDownloadsTorrentSourceItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_source_item)
        val recyclerViewDownloadsTorrentStateItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_state)
        val recyclerViewDownloadsTorrentProgressPercentageItem: TextView = v.findViewById(R.id.recycler_view_downloads_torrent_progress_percentage)
        val pauseOrResumeButton : Button = v.findViewById(R.id.button_downloads_torrent_pause_or_resume)
        val removeButton : Button = v.findViewById(R.id.button_downloads_torrent_remove)
        val downlaodProgressBar : ProgressBar = v.findViewById(R.id.progress_bar_downloads_torrent)
        lateinit var item : GodslayerTorrent
        fun initialize_view_holder(items: MutableList<GodslayerTorrent>, position: Int) {
            this.item = items[position]
            pauseOrResumeButton.setOnClickListener { item.pause_or_resume_session() }
            removeButton.setOnClickListener { item.stop_session() }
            downlaodProgressBar.max = 100
            downlaodProgressBar.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN)
            update_view_holder()
        }
        fun update_view_holder() {
            recyclerViewDownloadsTorrentEpisodeItem.text = item.episode_name
            recyclerViewDownloadsTorrentSourceItem.text = item.source_name
            if (item.isPaused) {
                pauseOrResumeButton.text = "RESUME"
            } else {
                pauseOrResumeButton.text = "PAUSE"
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