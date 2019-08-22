package solutions.pundir.godslayer.Downloads.ViewPagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrents
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrentStats

class ViewPagerAdapterDownloadsMainContainer internal constructor(fm: FragmentManager, val dbHandler: GodslayerDBOpenHelper) : FragmentPagerAdapter(fm) {

    private val COUNT = 2
    val fragmentDownloadsTorrents = FragmentDownloadsTorrents(dbHandler)
    val fragmentDownloadsTorrentStats = FragmentDownloadsTorrentStats(dbHandler)

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = fragmentDownloadsTorrents
            1 -> fragment = fragmentDownloadsTorrentStats
        }
        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var name = ""
        when(position) {
            0 -> name = "Downloads"
            1 -> name = "Stats"
        }
        return name
    }

}