package solutions.pundir.godslayer.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bottom_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_playlists.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterModules
import solutions.pundir.godslayer.Main.Fragments.AppCoordinator
import solutions.pundir.godslayer.Main.Fragments.FragmentMainContainer
import solutions.pundir.godslayer.R

// TODO
// Display content inside app by reading sqlite db.
val fragmentStateApp = StateFragmentsApp()

class MainActivity : AppCompatActivity(), AppCoordinator {
    val dbHandler = GodslayerDBOpenHelper(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup_bottom_bar_buttons()
        moveDatabaseFromAssetsToCache()

    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if (fragment is FragmentMainContainer) {
            fragment.callback_from_parent(this, dbHandler)
        }
    }

    override fun callback_from_child_fragment() {
        println("Callback from child fragment to Main Activity.")
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

    fun set_fragment_visibility() {
        fragment_layout_home?.visibility = if (fragmentStateApp.visibility_home) View.VISIBLE else View.GONE
        fragment_layout_subscriptions?.visibility = if (fragmentStateApp.visibility_subscriptions) View.VISIBLE else View.GONE
        fragment_layout_inbox?.visibility = if (fragmentStateApp.visibility_inbox) View.VISIBLE else View.GONE
        fragment_layout_library?.visibility = if (fragmentStateApp.visibility_library) View.VISIBLE else View.GONE
        fragment_layout_downloads?.visibility = if (fragmentStateApp.visibility_downloads) View.VISIBLE else View.GONE
    }

    fun setup_bottom_bar_buttons() {
        button_home.setOnClickListener {
            fragmentStateApp.show_home()
            set_fragment_visibility()
        }
        button_subscriptions.setOnClickListener {
            fragmentStateApp.show_subscriptions()
            set_fragment_visibility()
        }
        button_inbox.setOnClickListener {
            fragmentStateApp.show_inbox()
            set_fragment_visibility()
        }
        button_library.setOnClickListener {
            fragmentStateApp.show_library()
            set_fragment_visibility()
        }
        button_downloads.setOnClickListener {
            fragmentStateApp.show_downloads()
            set_fragment_visibility()
        }
    }
}
