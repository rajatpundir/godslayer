package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_publishers.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterPublishers
import solutions.pundir.godslayer.R

class FargmentHomePublishers(val dbHandler: GodslayerDBOpenHelper) : Fragment() {
    internal lateinit var callback : HomeMainContainerFragmentsCoordinator
    internal var items = mutableListOf<Triple<Long, Long, String>>()
    internal lateinit var adapter : RecycleViewAdapterPublishers

    fun callback_from_parent(callback : HomeMainContainerFragmentsCoordinator) {
        this.callback = callback
        println("INSIDE PUBLISHERS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_publishers, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_home_publishers.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterPublishers(context, items, this)
        recycler_view_home_publishers.adapter = adapter
        recycler_view_home_publishers_header.attachTo(recycler_view_home_publishers)
    }

    fun update_recycler_view(mid : Long, parent_id: Long) {
        doAsync {
            items.clear()
            uiThread {
                adapter.notifyDataSetChanged()
            }
            var cursor = dbHandler.getPlatform(mid, parent_id)
            cursor!!.moveToFirst()
            var title = cursor.getString(cursor.getColumnIndex("NAME"))
            uiThread {
                recycler_view_home_publishers_header_text_view.text = title
            }
            cursor.close()
            cursor = dbHandler.getPublishersByParent(mid, parent_id)
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
                if (items.size == 1) {
                    callback.update_playlists(module_id, rid)
                }
            }
        }
    }

    fun update_playlists_via_parent(mid : Long, parent_id : Long) {
        callback.update_playlists(mid, parent_id)
    }

}