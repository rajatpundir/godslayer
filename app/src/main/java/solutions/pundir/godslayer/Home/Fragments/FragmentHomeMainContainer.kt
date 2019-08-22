package solutions.pundir.godslayer.Home.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home_main_container.*
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.HomeCoordinator
import solutions.pundir.godslayer.Home.ViewPagerAdapters.ViewPagerAdapterHomeMainContainerFragments
import solutions.pundir.godslayer.R

class FragmentHomeMainContainer : Fragment(), HomeMainContainerCoordinator {
    internal lateinit var callback : HomeCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper

    fun callback_from_parent(callback : HomeCoordinator, dbHandler : GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE HOME CONTAINER")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_main_container, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = fragmentHomeMainContainerViewPager
        if (viewPager != null) {
            val adapter = fragmentManager?.let { ViewPagerAdapterHomeMainContainerFragments(it, dbHandler, this) }
            viewPager.adapter = adapter
        }
        viewPager.setOffscreenPageLimit(7)
        fragmentHomeMainContainerViewPagerHeader.tabIndicatorColor = Color.RED
    }

    override fun download_source(mid: Long, rid: Long) {
        println("DOWNLOAD SOURCE")
        callback.download_source(mid, rid)
    }

    override fun generate_click(button_name : String) {
        when(button_name) {
            "MODULES" -> fragmentHomeMainContainerViewPager.currentItem = 0
            "LANGUAGES" -> fragmentHomeMainContainerViewPager.currentItem = 1
            "PLATFORMS" -> fragmentHomeMainContainerViewPager.currentItem = 2
            "PUBLISHERS" -> fragmentHomeMainContainerViewPager.currentItem = 3
            "PLAYLISTS" -> fragmentHomeMainContainerViewPager.currentItem = 4
            "EPISODES" -> fragmentHomeMainContainerViewPager.currentItem = 5
            "SOURCES" -> fragmentHomeMainContainerViewPager.currentItem = 6
        }
    }
}