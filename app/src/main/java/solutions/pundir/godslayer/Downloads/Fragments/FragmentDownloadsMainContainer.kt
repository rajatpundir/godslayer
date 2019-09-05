package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_downloads_main_container.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.DownloadsCoordinator
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.Downloads.StateDownloadsMainContainer
import solutions.pundir.godslayer.Downloads.StateFragmentsDownloads
import solutions.pundir.godslayer.R

class FragmentDownloadsMainContainer : Fragment(), FragmentDownloadsMainContainerCoordinator {
    internal var items = mutableListOf<GodslayerTorrent>()
    internal lateinit var callback : DownloadsCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    private val fragmentStateDownloadsMainContainer = StateFragmentsDownloads()
    private val stateDownloadsMainContainer = StateDownloadsMainContainer()

    fun callback_from_parent(callback : DownloadsCoordinator, dbHandler : GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE DOWNLOADS CONTAINER")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_main_container, container, false)
        return v
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentDownloadsTorrents) {
            stateDownloadsMainContainer.torrents_fragment = childFragment
            stateDownloadsMainContainer.torrents_fragment.callback_from_parent(this, dbHandler, items)
        }
        else if (childFragment is FragmentDownloadsTorrentStats) {
            stateDownloadsMainContainer.torrent_stats_fragment = childFragment
            stateDownloadsMainContainer.torrent_stats_fragment.callback_from_parent(this, dbHandler, items)
        }
    }

    fun set_fragment_visibility() {
        fragment_layout_downloads_torrents?.visibility = if (fragmentStateDownloadsMainContainer.visibility_torrents) View.VISIBLE else View.GONE
        fragment_layout_downloads_torrent_stats?.visibility = if (fragmentStateDownloadsMainContainer.visibility_torrent_stats) View.VISIBLE else View.GONE
    }

    fun show_torrents() {
        fragmentStateDownloadsMainContainer.show_torrents()
        set_fragment_visibility()
    }

    override fun show_torrent_stats(index : Int) {
        fragmentStateDownloadsMainContainer.show_torrent_stats()
        set_fragment_visibility()
        stateDownloadsMainContainer.torrent_stats_fragment.show_torrent_stats(index)
    }

    override fun download_source(mid: Long, rid: Long) {
        stateDownloadsMainContainer.torrents_fragment.download_source(mid, rid)
    }

}