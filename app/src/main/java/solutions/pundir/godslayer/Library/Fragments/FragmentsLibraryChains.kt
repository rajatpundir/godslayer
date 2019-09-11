package solutions.pundir.godslayer.Library.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Library.DemonslayerTorrent
import solutions.pundir.godslayer.Library.ViewPagerAdapters.ViewPagerAdapterLibraryMainContainer
import solutions.pundir.godslayer.R

class FragmentsLibraryChains(var items : MutableList<DemonslayerTorrent>) : Fragment() {
    internal lateinit var callback : ViewPagerAdapterLibraryMainContainer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_library_chains, container, false)
        return v
    }

    fun callback_from_parent(callback : ViewPagerAdapterLibraryMainContainer) {
        this.callback = callback
    }

}