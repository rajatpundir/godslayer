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
import solutions.pundir.godslayer.R

class FargmentHomeModules(val dbHandler: GodslayerDBOpenHelper) : Fragment() {
    internal lateinit var callback : HomeMainContainerFragmentsCoordinator
    internal var items = mutableListOf<Pair<Long, String>>()
    internal lateinit var adapter : RecycleViewAdapterModules

    fun callback_from_parent(callback : HomeMainContainerFragmentsCoordinator) {
        this.callback = callback
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
        adapter = RecycleViewAdapterModules(context, items, this)
        recycler_view_home_modules.adapter = adapter
        update_recycler_view()
        recycler_view_home_modules_header.attachTo(recycler_view_home_modules)
    }

    fun update_recycler_view() {
        doAsync {
            items.clear()
            uiThread {
                adapter.notifyDataSetChanged()
                recycler_view_home_modules_header_text_view.text = "Bitchain"
            }
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
                if (items.size == 1) {
                    callback.update_languages(rid)
                }
            }
        }
    }

    fun update_languages_via_parent(parent_id : Long) {
        callback.update_languages(parent_id)
    }


}