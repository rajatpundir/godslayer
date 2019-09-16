package solutions.pundir.godslayer.Library.ViewPagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import solutions.pundir.godslayer.Library.Demonchain
import solutions.pundir.godslayer.Library.Fragments.FragmentLibraryChainStats
import solutions.pundir.godslayer.Library.Fragments.FragmentsLibraryChains
import solutions.pundir.godslayer.Library.Fragments.FragmentsLibraryMainContainer

class ViewPagerAdapterLibraryMainContainer internal constructor(fm: FragmentManager, val callback : FragmentsLibraryMainContainer, var items : MutableList<Demonchain>) : FragmentPagerAdapter(fm) {

    private val COUNT = 2
    val fragmentLibraryChains = FragmentsLibraryChains(items)
    val fragmentLibraryChainStats = FragmentLibraryChainStats(items)

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = fragmentLibraryChains
                fragment.callback_from_parent(this)
            }
            1 -> {
                fragment = fragmentLibraryChainStats
                fragment.callback_from_parent(this)
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
            0 -> name = "Chains"
            1 -> name = "Stats"
        }
        return name
    }

    fun show_chain_stats(index : Int) {
//        callback.show_torrent_stats(index)
    }

}