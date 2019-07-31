package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrentStats : Fragment() {
    internal lateinit var callback : FragmentDownloadsMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper

    fun callback_from_parent(callback : FragmentDownloadsMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE TORRENT STATS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrent_stats, container, false)
        return v
    }

}