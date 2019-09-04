package solutions.pundir.godslayer.Downloads.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_downloads_torrent_stats.*
import kotlinx.android.synthetic.main.fragment_downloads_torrents.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.DownloadsCoordinator
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.Downloads.ViewPagerAdapters.ViewPagerAdapterDownloadsTorrentStats
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrentStats : Fragment() {
    internal lateinit var callback : FragmentDownloadsMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var items : MutableList<GodslayerTorrent>

    fun callback_from_parent(callback : FragmentDownloadsMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper, items : MutableList<GodslayerTorrent>) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.items = items
        println("INSIDE DOWNLOADS TORRENT STATS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrent_stats, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentDownloadsTorrentStatsViewPager != null) {
            val adapter = fragmentManager?.let { ViewPagerAdapterDownloadsTorrentStats(it, dbHandler, items) }
            fragmentDownloadsTorrentStatsViewPager.adapter = adapter
        }
        fragmentDownloadsTorrentStatsViewPager.setOffscreenPageLimit(6)
        fragmentDownloadsTorrentStatsViewPagerHeader.tabIndicatorColor = Color.RED
    }

}