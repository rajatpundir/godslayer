package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_episodes.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterEpisodes
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.R

class FargmentHomeEpisodes : Fragment() {
    internal lateinit var callback : HomeCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var appStateHome : StateAppHome

    fun callback_from_parent(callback : HomeCoordinator, dbHandler : GodslayerDBOpenHelper, appStateHome : StateAppHome) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.appStateHome = appStateHome
        println("INSIDE EPISODES")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_episodes, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("-------")
        doAsync {
            val cursor = dbHandler.getEpisodes()
            var items = mutableListOf<Triple<Long, Long, String>>()
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
                var linearLayoutManager = LinearLayoutManager(context)
                recycler_view_home_episodes.layoutManager = linearLayoutManager
                val adapter = RecycleViewAdapterEpisodes(context, items, appStateHome, this@FargmentHomeEpisodes)
                recycler_view_home_episodes.adapter = adapter
            }
        }
    }

    fun update_sources_via_parent(mid : Long, parent_id : Long) {
        callback.update_sources(mid, parent_id)
    }

}