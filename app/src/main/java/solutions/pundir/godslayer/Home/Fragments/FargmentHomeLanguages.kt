package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.StayConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import kotlinx.android.synthetic.main.fragment_home_languages.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterLanguages
import solutions.pundir.godslayer.R

class FargmentHomeLanguages(val dbHandler: GodslayerDBOpenHelper) : Fragment() {
    internal lateinit var callback : HomeMainContainerFragmentsCoordinator
    internal var items = mutableListOf<Triple<Long, Long, String>>()
    internal lateinit var adapter : RecycleViewAdapterLanguages

    fun callback_from_parent(callback : HomeMainContainerFragmentsCoordinator) {
        this.callback = callback
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
        adapter = RecycleViewAdapterLanguages(context, items, this)
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
                if (items.size == 1) {
                    callback.update_platforms(module_id, rid)
                }
            }
        }
    }

    fun update_platforms_via_parent(mid : Long, parent_id : Long) {
        callback.update_platforms(mid, parent_id)
    }

}