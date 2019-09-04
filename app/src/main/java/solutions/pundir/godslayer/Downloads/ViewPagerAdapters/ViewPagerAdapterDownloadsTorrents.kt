package solutions.pundir.godslayer.Downloads.ViewPagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrents
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrentsQueue
import solutions.pundir.godslayer.Downloads.GodslayerTorrent

class ViewPagerAdapterDownloadsTorrents internal constructor(fm: FragmentManager, val callback : FragmentDownloadsTorrents, val dbHandler: GodslayerDBOpenHelper, var items : MutableList<GodslayerTorrent>) : FragmentPagerAdapter(fm) {

    private val COUNT = 1
    val fragmentDownloadsTorrentsQueue = FragmentDownloadsTorrentsQueue(dbHandler, items)

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = fragmentDownloadsTorrentsQueue
                fragmentDownloadsTorrentsQueue.callback_from_parent(this)
            }
        }
        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var name = ""
        when(position) {
            0 -> name = "Queue"
        }
        return name
    }

    fun show_torrent_stats(index : Int) {
        callback.show_torrent_stats(index)
    }

    fun download_source(mid: Long, rid: Long) {
        fragmentDownloadsTorrentsQueue.download_source(mid, rid)
    }

}