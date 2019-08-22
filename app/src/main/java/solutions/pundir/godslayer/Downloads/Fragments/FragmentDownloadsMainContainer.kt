package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_downloads_main_container.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.DownloadsCoordinator
import solutions.pundir.godslayer.Downloads.ViewPagerAdapters.ViewPagerAdapterDownloadsMainContainer
import solutions.pundir.godslayer.R

class FragmentDownloadsMainContainer : Fragment(), FragmentDownloadsMainContainerCoordinator {
    internal lateinit var callback : DownloadsCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper

    fun callback_from_parent(callback : DownloadsCoordinator, dbHandler : GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE DOWNLOADS CONTAINER")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_main_container, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = fragmentDownloadsMainContainerViewPager
        if (viewPager != null) {
            val adapter = fragmentManager?.let { ViewPagerAdapterDownloadsMainContainer(it, dbHandler) }
            viewPager.adapter = adapter
        }
        viewPager.setOffscreenPageLimit(2)
    }

    override fun download_source(mid: Long, rid: Long) {
        (fragmentDownloadsMainContainerViewPager.adapter as ViewPagerAdapterDownloadsMainContainer).fragmentDownloadsTorrents.download_source(mid, rid)
    }

}