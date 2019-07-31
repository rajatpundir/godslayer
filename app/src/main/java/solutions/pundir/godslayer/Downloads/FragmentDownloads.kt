package solutions.pundir.godslayer.Downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_downloads_main_container.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Main.Fragments.AppCoordinator
import solutions.pundir.godslayer.R
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsMainContainer

class FragmentDownloads : Fragment(), DownloadsCoordinator {
    private val fragmentStateDownloads = StateFragmentsDownloads()
    internal lateinit var callback : AppCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var fragmentDownloadsMainContainer: FragmentDownloadsMainContainer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentDownloadsMainContainer) {
            childFragment.callback_from_parent(this, dbHandler)
            fragmentDownloadsMainContainer = childFragment
        }
    }

    fun callback_from_parent(callback : AppCoordinator, dbHandler: GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE DOWNLOADS")
    }

    fun download_source(mid : Long, rid : Long) {
        fragmentDownloadsMainContainer.download_source(mid, rid)
    }

    fun set_fragment_visibility() {
        fragment_layout_downloads_torrents?.visibility = if (fragmentStateDownloads.visibility_downloads_torrents) View.VISIBLE else View.GONE
        fragment_layout_downloads_torrent_stats?.visibility = if (fragmentStateDownloads.visibility_downloads_torrent_stats) View.VISIBLE else View.GONE
    }

    fun show_fragment_torrents() {
        fragmentStateDownloads.show_downloads_torrents()
        set_fragment_visibility()
    }

    fun show_fragment_torrent_stats() {
        fragmentStateDownloads.show_downloads_torrent_stats()
        set_fragment_visibility()
    }

}