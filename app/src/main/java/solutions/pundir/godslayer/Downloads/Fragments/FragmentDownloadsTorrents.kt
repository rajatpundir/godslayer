package solutions.pundir.godslayer.Downloads.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_downloads_torrents.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.R
import solutions.pundir.godslayer.Downloads.ViewPagerAdapters.ViewPagerAdapterDownloadsTorrents

class FragmentDownloadsTorrents : Fragment() {
    internal lateinit var callback : FragmentDownloadsMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var items : MutableList<GodslayerTorrent>

    fun callback_from_parent(callback : FragmentDownloadsMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper, items : MutableList<GodslayerTorrent>) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.items = items
        println("INSIDE DOWNLOADS TORRENTS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrents, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentDownloadsTorrentsViewPager != null) {
            val adapter = fragmentManager?.let { ViewPagerAdapterDownloadsTorrents(it, this, dbHandler, items) }
            fragmentDownloadsTorrentsViewPager.adapter = adapter
        }
        fragmentDownloadsTorrentsViewPager.setOffscreenPageLimit(2)
        fragmentDownloadsTorrentsViewPagerHeader.tabIndicatorColor = Color.RED
    }

    fun download_source(mid: Long, rid: Long) {
        (fragmentDownloadsTorrentsViewPager.adapter as ViewPagerAdapterDownloadsTorrents).download_source(mid, rid)
    }

    fun show_torrent_stats(index : Int) {
        callback.show_torrent_stats(index)
    }

}