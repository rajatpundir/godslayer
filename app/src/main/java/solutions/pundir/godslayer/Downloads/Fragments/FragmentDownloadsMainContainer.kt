package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.DownloadsCoordinator
import solutions.pundir.godslayer.Downloads.StateDownloadsMainContainer
import solutions.pundir.godslayer.R

class FragmentDownloadsMainContainer : Fragment(), FragmentDownloadsMainContainerCoordinator {
    internal lateinit var callback : DownloadsCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
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
            childFragment.callback_from_parent(this, dbHandler)
            stateDownloadsMainContainer.torrents_fragmentDownloads = childFragment
        }
        if (childFragment is FragmentDownloadsTorrentStats) {
            childFragment.callback_from_parent(this, dbHandler)
            stateDownloadsMainContainer.torrent_stats_fragmentDownloads = childFragment
        }
    }

    override fun download_source(mid: Long, rid: Long) {
        stateDownloadsMainContainer.torrents_fragmentDownloads.download_source(mid, rid)
    }

}