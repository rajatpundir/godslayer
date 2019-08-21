package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle

import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.StayConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import kotlinx.android.synthetic.main.fragment_home_playlists.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterPlaylists
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.R

class FargmentHomePlaylists : Fragment() {
    internal lateinit var callback : HomeMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var appStateHome : StateAppHome
    internal var items = mutableListOf<DatabaseRecord>()
    internal lateinit var adapter : RecycleViewAdapterPlaylists

    fun callback_from_parent(callback : HomeMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper, appStateHome : StateAppHome) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.appStateHome = appStateHome
        println("INSIDE PLAYLISTS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_playlists, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var gridLayoutManager = GridLayoutManager(context, 3)
        recycler_view_home_playlists.layoutManager = gridLayoutManager
        adapter = RecycleViewAdapterPlaylists(context, items, appStateHome, this)
        recycler_view_home_playlists.adapter = adapter
        SmartSwipe.wrap(recycler_view_home_playlists)
            .addConsumer(StayConsumer())
            .enableHorizontal()
            .addListener(object : SimpleSwipeListener() {
                override fun onSwipeOpened(wrapper: SmartSwipeWrapper?, consumer: SwipeConsumer?, direction: Int) {
                    when (direction) {
                        1 -> callback.generate_click("PUBLISHERS")
                        2 -> callback.generate_click("EPISODES")
                    }
                }
            } )
    }

    fun update_recycler_view(mid : Long, parent_id: Long) {
        doAsync {
            items.clear()
            uiThread {
                adapter.notifyDataSetChanged()
            }
            val cursor = dbHandler.getPlaylistsByParent(mid, parent_id)
            cursor!!.moveToFirst()
            var record = DatabaseRecord()
            record.module_id = cursor.getString(cursor.getColumnIndex("MODULE_ID")).toLong()
            record.rid = cursor.getString(cursor.getColumnIndex("ID")).toLong()
            record.name = cursor.getString(cursor.getColumnIndex("NAME"))
            record.image_url = cursor.getString(cursor.getColumnIndex("IMAGE_URL"))
            items.add(record)
            while (cursor.moveToNext()) {
                record = DatabaseRecord()
                record.module_id = cursor.getString(cursor.getColumnIndex("MODULE_ID")).toLong()
                record.rid = cursor.getString(cursor.getColumnIndex("ID")).toLong()
                record.name = cursor.getString(cursor.getColumnIndex("NAME"))
                record.image_url = cursor.getString(cursor.getColumnIndex("IMAGE_URL"))
                items.add(record)
            }
            cursor.close()
            uiThread {
                adapter.notifyDataSetChanged()
                if (items.size == 1) {
                    callback.update_episodes(record.module_id, record.rid)
                }
            }
        }
    }

    fun update_episodes_via_parent(mid : Long, parent_id : Long) {
        callback.update_episodes(mid, parent_id)
    }

}