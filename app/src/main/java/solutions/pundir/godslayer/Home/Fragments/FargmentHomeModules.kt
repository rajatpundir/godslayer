package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_modules.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterModules
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.R

class FargmentHomeModules : Fragment() {
    internal lateinit var callback : HomeMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var appStateHome : StateAppHome
    internal var items = mutableListOf<Pair<Long, String>>()
    internal lateinit var adapter : RecycleViewAdapterModules

    fun callback_from_parent(callback : HomeMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper, appStateHome : StateAppHome) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.appStateHome = appStateHome
        println("INSIDE MODULES")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_modules, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context)
        recycler_view_home_modules.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapterModules(context, items, appStateHome, this)
        recycler_view_home_modules.adapter = adapter
        update_recycler_view()
    }

    fun update_recycler_view() {
        doAsync {
            items.clear()
            val cursor = dbHandler.getModules()
            cursor!!.moveToFirst()
            var rid = cursor.getString(cursor.getColumnIndex("ID")).toLong()
            var name = cursor.getString(cursor.getColumnIndex("NAME"))
            items.add(Pair(rid, name))
            while (cursor.moveToNext()) {
                rid = cursor.getString(cursor.getColumnIndex("ID")).toLong()
                name = cursor.getString(cursor.getColumnIndex("NAME"))
                items.add(Pair(rid, name))
            }
            cursor.close()
            uiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun update_languages_via_parent(parent_id : Long) {
        callback.update_languages(parent_id)
    }


}