package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_downloads_torrent_stats_status.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrentStatsStatus(val dbHandler: GodslayerDBOpenHelper, var items : MutableList<GodslayerTorrent>) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrent_stats_status, container, false)
        return v
    }

    fun show_torrent_stats(index : Int) {
        println("SOMETHING")
        println(items[index].metadata_ready)
        if (items[index].metadata_ready) {
            val torrent_info = items[index].torrent_info
            torrentName.text = torrent_info.status.torrent_name
            torrentProgress.max = 100
            torrentProgress.progress = torrent_info.status.torrent_progress
            torrentDownloadSpeed.text = torrent_info.status.torrent_download_speed
            torrentUploadSpeed.text = torrent_info.status.torrent_upload_speed
            torrentDownloaded.text = torrent_info.status.torrent_downloaded
            torrentLeechers.text = torrent_info.status.torrent_leechers
            torrentSeeders.text = torrent_info.status.torrent_seeders
            torrentUploaded.text = torrent_info.status.torrent_uploaded
            torrentActiveTime.text = torrent_info.status.torrent_active_time
            torrentSeedingTime.text = torrent_info.status.torrent_seeding_time
            torrentPieces.text = torrent_info.status.pieces
        }
    }

}