package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_sources.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterSources
import solutions.pundir.godslayer.R

class FargmentHomeSources(val dbHandler: GodslayerDBOpenHelper) : Fragment() {
    internal lateinit var callback : HomeMainContainerFragmentsCoordinator
    internal var items = mutableListOf<Triple<Long, Long, String>>()
    internal lateinit var adapter : RecycleViewAdapterSources

    fun callback_from_parent(callback : HomeMainContainerFragmentsCoordinator) {
        this.callback = callback
        println("INSIDE SOURCES")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_sources, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_home_sources.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterSources(context, items, this)
        recycler_view_home_sources.adapter = adapter
        recycler_view_home_sources_header.attachTo(recycler_view_home_sources)
        recycler_view_home_sources_download_button.setOnClickListener { download_source_via_parent() }
    }

    fun update_recycler_view(mid : Long, parent_id : Long) {
        doAsync {
            items.clear()
            uiThread {
                adapter.notifyDataSetChanged()
                recycler_view_home_sources_download_button.visibility = View.GONE
            }
            var cursor = dbHandler.getEpisode(mid, parent_id)
            cursor!!.moveToFirst()
            var title = cursor.getString(cursor.getColumnIndex("NAME"))
            uiThread {
                recycler_view_home_sources_header_text_view.text = title
            }
            cursor = dbHandler.getSourcesByParent(mid, parent_id)
            cursor!!.moveToFirst()
            var module_id = cursor.getString(cursor.getColumnIndex("MODULE_ID")).toLong()
            var rid = cursor.getString(cursor.getColumnIndex("ID")).toLong()
            var name = cursor.getString(cursor.getColumnIndex("NAME"))
            items.add(Triple(module_id, rid, name))
            while (cursor.moveToNext()) {
                module_id = cursor.getString(cursor.getColumnIndex("MODULE_ID")).toLong()
                rid = cursor.getString(cursor.getColumnIndex("ID")).toLong()
                name = cursor.getString(cursor.getColumnIndex("NAME"))
                items.add(Triple(module_id, rid, name))
            }
            cursor.close()
            uiThread {
                adapter.notifyDataSetChanged()
                if (items.size > 0) {
                    recycler_view_home_sources_download_button.visibility = View.VISIBLE
                }
            }
        }
    }

    fun download_source_via_parent() {
        for (i in 0..(recycler_view_home_sources.childCount - 1)) {
            var holder = recycler_view_home_sources.findViewHolderForAdapterPosition(i) as RecycleViewAdapterSources.HomeItemViewHolder
            if (holder.recyclerViewHomeSourceItemCheckbox.isChecked) {
                callback.download_source(holder.mid, holder.rid)
            }
        }
    }

}