package solutions.pundir.godslayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bottom_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Fragment.FragmentCoordinator
import java.lang.StringBuilder

// TODO
// Display content inside app by reading sqlite db.

class MainActivity : AppCompatActivity(), FragmentCoordinator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup_bottom_bar_buttons()
        moveDatabaseFromAssetsToCache()
        val dbHandler = GodslayerDBOpenHelper(this, null)
        doAsync {
            var items = mutableListOf<String>()
            val cursor = dbHandler.getPlaylists()
            cursor!!.moveToFirst()
            items.add(cursor.getString(cursor.getColumnIndex("NAME")))
            while (cursor.moveToNext()) {
                items.add(cursor.getString(cursor.getColumnIndex("NAME")))
            }
            print(items.take(10))
            cursor.close()
            uiThread {
                var linearLayoutManager = LinearLayoutManager(this@MainActivity)
                recycler_view_home.layoutManager = linearLayoutManager
                val adapter = RecycleViewAdapterModules(this@MainActivity, items)
                recycler_view_home.adapter = adapter
            }
        }
    }

    fun moveDatabaseFromAssetsToCache() {
        // Move Database from Assets to Cache of the App.
        val tmpPath = cacheDir.resolve("0.db")
        println(tmpPath)
        if( !tmpPath.exists() ) {
            assets.open("0.db").use { inStream ->
                tmpPath.outputStream().use{ outStream ->
                    inStream.copyTo(outStream)
                }
            }
        }
    }

    fun setup_bottom_bar_buttons() {
        button_home.setOnClickListener {
            fragment_layout_home?.visibility = View.VISIBLE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_subscriptions.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.VISIBLE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_inbox.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.VISIBLE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_library.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.VISIBLE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_downloads.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.VISIBLE
        }
    }
}
