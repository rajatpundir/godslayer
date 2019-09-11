package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_downloads_torrent_stats_files.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.Downloads.RecycleViewAdapters.RecycleViewAdapterTorrentStatsFiles
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrentStatsFiles(val dbHandler: GodslayerDBOpenHelper, var items : MutableList<GodslayerTorrent>) : Fragment() {
    internal var index = -1
    internal lateinit var adapter : RecycleViewAdapterTorrentStatsFiles
    internal var files = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrent_stats_files, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_downloads_torrent_stats_files.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterTorrentStatsFiles(context, files, this)
        recycler_view_downloads_torrent_stats_files.adapter = adapter
    }

    fun show_torrent_stats(index : Int) {
        this.index = index
        var count = 0
        files.clear()
        for (i in items[index].torrent_info.files.files) {
            files.add("INDEX : " + count.toString() + "" +
                    " \n" + i)
            count += 1
        }
        adapter.notifyDataSetChanged()
        refresh_torrent_stats()
    }

    fun refresh_torrent_stats() {
        val torrent_info = items[this.index].torrent_info
    }

}