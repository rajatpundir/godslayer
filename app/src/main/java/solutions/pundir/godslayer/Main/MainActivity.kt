package solutions.pundir.godslayer.Main

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.masterwok.simpletorrentandroid.TorrentSession
import com.masterwok.simpletorrentandroid.TorrentSessionOptions
import kotlinx.android.synthetic.main.fragment_bottom_bar.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.GodslayerTorrent
import solutions.pundir.godslayer.Main.Fragments.FragmentMainContainer
import solutions.pundir.godslayer.R

class MainActivity : AppCompatActivity(), MasterCoordinator {
    private val fragmentStateApp = StateFragmentsApp()
    lateinit var dbHandler : GodslayerDBOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        moveDatabaseFromAssetsToCache()
        dbHandler = GodslayerDBOpenHelper(this@MainActivity, null, "0.db")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup_bottom_bar_buttons()
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
        //var t = GodslayerTorrent(this, dbHandler, 4962685108190249448, 62321)


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

    private fun moveDatabaseFromAssetsToCache() {
        val tmpPath = cacheDir.resolve("0.db")
        println(tmpPath)
        if( !tmpPath.exists() ) {
            assets.open("0.db").use { inStream ->
                tmpPath.outputStream().use{ outStream ->
                    inStream.copyTo(outStream)
                }
            }
        }
        println("PASS")
    }

    fun set_fragment_visibility() {
        button_home.setTextColor(Color.WHITE)
        button_subscriptions.setTextColor(Color.WHITE)
        button_inbox.setTextColor(Color.WHITE)
        button_library.setTextColor(Color.WHITE)
        button_downloads.setTextColor(Color.WHITE)
        button_home.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_subscriptions.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_inbox.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_library.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_downloads.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        fragment_layout_home?.visibility = if (fragmentStateApp.visibility_home) View.VISIBLE else View.GONE
        fragment_layout_subscriptions?.visibility = if (fragmentStateApp.visibility_subscriptions) View.VISIBLE else View.GONE
        fragment_layout_inbox?.visibility = if (fragmentStateApp.visibility_inbox) View.VISIBLE else View.GONE
        fragment_layout_library?.visibility = if (fragmentStateApp.visibility_library) View.VISIBLE else View.GONE
        fragment_layout_downloads?.visibility = if (fragmentStateApp.visibility_downloads) View.VISIBLE else View.GONE
    }

    fun setup_bottom_bar_buttons() {
        button_home.setTextColor(Color.RED)
        button_home.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        button_subscriptions.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_inbox.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_library.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_downloads.setCompoundDrawableTintList(ColorStateList.valueOf(Color.LTGRAY))
        button_home.setOnClickListener {
            fragmentStateApp.show_home()
            set_fragment_visibility()
            button_home.setTextColor(Color.RED)
            button_home.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        }
        button_subscriptions.setOnClickListener {
            fragmentStateApp.show_subscriptions()
            set_fragment_visibility()
            button_subscriptions.setTextColor(Color.RED)
            button_subscriptions.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        }
        button_inbox.setOnClickListener {
            fragmentStateApp.show_inbox()
            set_fragment_visibility()
            button_inbox.setTextColor(Color.RED)
            button_inbox.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        }
        button_library.setOnClickListener {
            fragmentStateApp.show_library()
            set_fragment_visibility()
            button_library.setTextColor(Color.RED)
            button_library.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        }
        button_downloads.setOnClickListener {
            fragmentStateApp.show_downloads()
            set_fragment_visibility()
            button_downloads.setTextColor(Color.RED)
            button_downloads.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        }
    }
}
