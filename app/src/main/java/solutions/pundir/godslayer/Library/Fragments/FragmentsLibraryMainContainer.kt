package solutions.pundir.godslayer.Library.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_library_main_container.*
import solutions.pundir.godslayer.Library.DemonslayerTorrent
import solutions.pundir.godslayer.Library.LibraryCoordinator
import solutions.pundir.godslayer.Library.ViewPagerAdapters.ViewPagerAdapterLibraryMainContainer
import solutions.pundir.godslayer.R

class FragmentsLibraryMainContainer : Fragment() {
    internal lateinit var callback : LibraryCoordinator
    internal var items = mutableListOf<DemonslayerTorrent>()

    fun callback_from_parent(callback : LibraryCoordinator) {
        this.callback = callback
        println("INSIDE LIBRARY MAIN CONTAINER")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_library_main_container, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentLibraryMainContainerViewPager != null) {
            val adapter = fragmentManager?.let { ViewPagerAdapterLibraryMainContainer(it, this, items) }
            fragmentLibraryMainContainerViewPager.adapter = adapter
        }
        fragmentLibraryMainContainerViewPager.setOffscreenPageLimit(2)
        fragmentLibraryMainContainerViewPagerHeader.tabIndicatorColor = Color.RED
    }

}