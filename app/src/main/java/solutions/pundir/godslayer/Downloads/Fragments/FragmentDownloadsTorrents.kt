package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_downloads_torrents.*
import org.jetbrains.anko.toast
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.Downloads.RecycleViewAdapters.RecycleViewAdapterTorrents
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrents : Fragment() {
    internal lateinit var callback : FragmentDownloadsMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal var items = mutableListOf<GodslayerTorrent>()
    internal lateinit var adapter : RecycleViewAdapterTorrents

    fun callback_from_parent(callback : FragmentDownloadsMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE TORRENTS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrents, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_downloads_torrents.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterTorrents(context, items, this)
        recycler_view_downloads_torrents.adapter = adapter
    }

    fun download_source(mid: Long, rid: Long) {
        var check = true
        for (item in items.listIterator()) {
            if (item.module_id == mid && item.source_id == rid) {
                check = false
                break
            }
        }
        if (check) {
            items.add(GodslayerTorrent(context, dbHandler, mid, rid))
            adapter.notifyDataSetChanged()
            println(items.size)
        } else {
            context?.toast("Already present in the Downloads Queue.")
        }

    }

}