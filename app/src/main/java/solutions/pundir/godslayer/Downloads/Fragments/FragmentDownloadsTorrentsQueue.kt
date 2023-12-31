package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_downloads_torrents_queue.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.Downloads.RecycleViewAdapters.RecycleViewAdapterTorrentsQueue
import solutions.pundir.godslayer.Downloads.ViewPagerAdapters.ViewPagerAdapterDownloadsTorrents
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrentsQueue(val dbHandler: GodslayerDBOpenHelper, var items : MutableList<GodslayerTorrent>) : Fragment() {
    internal lateinit var callback : ViewPagerAdapterDownloadsTorrents
    internal lateinit var adapter : RecycleViewAdapterTorrentsQueue

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrents_queue, container, false)
        return v
    }

    fun callback_from_parent(callback : ViewPagerAdapterDownloadsTorrents) {
        this.callback = callback
        println("INSIDE TORRENTS QUEUE")
    }

    fun show_torrent_stats(index : Int) {
        callback.show_torrent_stats(index)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_downloads_torrents.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterTorrentsQueue(context, items, this)
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
            doAsync {
                context?.let { GodslayerTorrent(it, dbHandler, mid, rid, this@FragmentDownloadsTorrentsQueue) }?.let { items.add(it) }
                items.last().callback_from_parent_fragment(items.size - 1)
                uiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        } else {
            context?.toast("Already present in the Downloads Queue.")
        }
    }

    fun refresh_torrent_in_adapter(position : Int) {
        val x = (recycler_view_downloads_torrents.findViewHolderForLayoutPosition(position)) as RecycleViewAdapterTorrentsQueue.DownloadsItemViewHolder?
        x?.update_view_holder()
    }

    fun remove_torrent_and_update_adapter(mid : Long, rid : Long) {
        var new_items = items.toMutableList()
        items.clear()
        for (item in new_items.listIterator()) {
            if (item.source_id == rid && item.module_id == mid) {
                continue
            } else {
                items.add(item)
                item.callback_from_parent_fragment(items.size - 1)
            }
        }
        adapter.notifyDataSetChanged()
        context?.toast("Removed")
    }


}