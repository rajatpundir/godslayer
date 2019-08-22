package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_platforms.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterPlatforms
import solutions.pundir.godslayer.R

class FargmentHomePlatforms(val dbHandler: GodslayerDBOpenHelper) : Fragment() {
    internal lateinit var callback : HomeMainContainerFragmentsCoordinator
    internal var items = mutableListOf<Triple<Long, Long, String>>()
    internal lateinit var adapter : RecycleViewAdapterPlatforms

    fun callback_from_parent(callback : HomeMainContainerFragmentsCoordinator) {
        this.callback = callback
        println("INSIDE PLATFORMS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_platforms, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_home_platforms.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterPlatforms(context, items, this)
        recycler_view_home_platforms.adapter = adapter
        recycler_view_home_platforms_header.attachTo(recycler_view_home_platforms)
    }

    fun update_recycler_view(mid : Long, parent_id: Long) {
        doAsync {
            items.clear()
            uiThread {
                adapter.notifyDataSetChanged()
            }
            var cursor = dbHandler.getLanguage(mid, parent_id)
            cursor!!.moveToFirst()
            var title = cursor.getString(cursor.getColumnIndex("NAME"))
            uiThread {
                recycler_view_home_platforms_header_text_view.text = title
            }
            cursor.close()
            cursor = dbHandler.getPlatformsByParent(mid, parent_id)
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
                    callback.update_publishers(module_id, rid)
                }
            }
        }
    }

    fun update_publishers_via_parent(mid : Long, parent_id : Long) {
        callback.update_publishers(mid, parent_id)
    }

}