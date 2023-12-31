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
    internal var index = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrent_stats_status, container, false)
        return v
    }

    fun show_torrent_stats(index : Int) {
        this.index = index
        refresh_torrent_stats()
    }

    fun refresh_torrent_stats() {
        val torrent_info = items[this.index].torrent_info
        torrentName.text = torrent_info.status.torrent_name
        torrentProgress.progress = torrent_info.status.torrent_progress
        torrentProgressNum.text = torrent_info.status.torrent_progress.toString() + "%"
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