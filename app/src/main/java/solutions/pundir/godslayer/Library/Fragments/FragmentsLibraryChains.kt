package solutions.pundir.godslayer.Library.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_library_chains.*
import org.jetbrains.anko.doAsync
import solutions.pundir.godslayer.Library.Demonchain
import solutions.pundir.godslayer.Library.ViewPagerAdapters.ViewPagerAdapterLibraryMainContainer
import solutions.pundir.godslayer.R

class FragmentsLibraryChains(var items : MutableList<Demonchain>) : Fragment() {
    internal lateinit var callback : ViewPagerAdapterLibraryMainContainer

    fun callback_from_parent(callback : ViewPagerAdapterLibraryMainContainer) {
        this.callback = callback
        println("INSIDE LIBRARY CHAINS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_library_chains, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_add_chain.setOnClickListener { add_node() }
    }

    fun add_node() {
        val name = edit_text_chain_name.text.toString()
        val magnet_url = edit_text_chain_url.text.toString()
        doAsync {
            if (magnet_url.trim() != "" && name.trim() != "") {
                val chain = Demonchain(context, name)
                items.add(chain)
                chain.add_node(magnet_url)
            }
        }
    }

}