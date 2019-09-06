package solutions.pundir.godslayer.Downloads.ViewPagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.Fragments.*
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.Downloads.GodslayerTorrentInfo.GodslayerTorrentInfoListener

class ViewPagerAdapterDownloadsTorrentStats internal constructor(fm: FragmentManager, val dbHandler: GodslayerDBOpenHelper, var items : MutableList<GodslayerTorrent>) : FragmentPagerAdapter(fm), GodslayerTorrentInfoListener {

    private val COUNT = 6
    val fragmentDownloadsTorrentStatsDetails = FragmentDownloadsTorrentStatsDetails(dbHandler, items)
    val fragmentDownloadsTorrentStatsStatus = FragmentDownloadsTorrentStatsStatus(dbHandler, items)
    val fragmentDownloadsTorrentStatsFiles = FragmentDownloadsTorrentStatsFiles(dbHandler, items)
    val fragmentDownloadsTorrentStatsTrackers = FragmentDownloadsTorrentStatsTrackers(dbHandler, items)
    val fragmentDownloadsTorrentStatsPeers = FragmentDownloadsTorrentStatsPeers(dbHandler, items)
    val fragmentDownloadsTorrentStatsPieces = FragmentDownloadsTorrentStatsPieces(dbHandler, items)
    var index = -1

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = fragmentDownloadsTorrentStatsDetails
            1 -> fragment = fragmentDownloadsTorrentStatsStatus
            2 -> fragment = fragmentDownloadsTorrentStatsFiles
            3 -> fragment = fragmentDownloadsTorrentStatsTrackers
            4 -> fragment = fragmentDownloadsTorrentStatsPeers
            5 -> fragment = fragmentDownloadsTorrentStatsPieces
        }
        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var name = ""
        when(position) {
            0 -> name = "Details"
            1 -> name = "Status"
            2 -> name = "Files"
            3 -> name = "Trackers"
            4 -> name = "Peers"
            5 -> name = "Pieces"
        }
        return name
    }

    fun show_torrent_stats(index : Int) {
        if (this.index != -1) {
            items[this.index].torrent_info.refresh_torrent_stats_flag = false
        }
        this.index = index
        items[this.index].torrent_info.refresh_stats_callback = this
        items[this.index].torrent_info.refresh_torrent_stats_flag = true
        fragmentDownloadsTorrentStatsDetails.show_torrent_stats(index)
        fragmentDownloadsTorrentStatsStatus.show_torrent_stats(index)
        fragmentDownloadsTorrentStatsFiles.show_torrent_stats(index)
        fragmentDownloadsTorrentStatsTrackers.show_torrent_stats(index)
        fragmentDownloadsTorrentStatsPeers.show_torrent_stats(index)
        fragmentDownloadsTorrentStatsPieces.show_torrent_stats(index)
    }

    override fun refresh_torrent_stats() {
        fragmentDownloadsTorrentStatsDetails.refresh_torrent_stats()
        fragmentDownloadsTorrentStatsStatus.refresh_torrent_stats()
        fragmentDownloadsTorrentStatsFiles.refresh_torrent_stats()
        fragmentDownloadsTorrentStatsTrackers.refresh_torrent_stats()
        fragmentDownloadsTorrentStatsPeers.refresh_torrent_stats()
        fragmentDownloadsTorrentStatsPieces.refresh_torrent_stats()
    }

}