package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrentStatsFiles(val dbHandler: GodslayerDBOpenHelper, var items : MutableList<GodslayerTorrent>) : Fragment() {
    internal var index = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrent_stats_files, container, false)
        return v
    }

    fun show_torrent_stats(index : Int) {
        this.index = index
        refresh_torrent_stats()
    }

    fun refresh_torrent_stats() {
        val torrent_info = items[this.index].torrent_info
    }

}