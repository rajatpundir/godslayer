package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_languages.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterLanguages
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.R

class FargmentHomeLanguages : Fragment() {
    internal lateinit var callback : HomeMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var appStateHome : StateAppHome
    internal var items = mutableListOf<Triple<Long, Long, String>>()
    internal lateinit var adapter : RecycleViewAdapterLanguages

    fun callback_from_parent(callback : HomeMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper, appStateHome : StateAppHome) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.appStateHome = appStateHome
        println("INSIDE LANGUAGES")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_languages, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_home_languages.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterLanguages(context, items, appStateHome, this)
        recycler_view_home_languages.adapter = adapter
    }

    fun update_recycler_view(mid : Long) {
        doAsync {
            items.clear()
            uiThread {
                adapter.notifyDataSetChanged()
            }
            val cursor = dbHandler.getLanguagesByParent(mid)
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
            }
        }
    }

    fun update_platforms_via_parent(mid : Long, parent_id : Long) {
        callback.update_platforms(mid, parent_id)
    }

}