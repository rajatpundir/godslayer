package solutions.pundir.godslayer.Main

import android.Manifest
import android.content.pm.PackageManager
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
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Main.Fragments.AppCoordinator
import solutions.pundir.godslayer.Main.Fragments.FragmentMainContainer
import solutions.pundir.godslayer.R

val fragmentStateApp = StateFragmentsApp()

class MainActivity : AppCompatActivity(), AppCoordinator {

    lateinit var dbHandler : GodslayerDBOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        moveDatabaseFromAssetsToCache()
        dbHandler = GodslayerDBOpenHelper(this@MainActivity, null, "0.db")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup_bottom_bar_buttons()
        // http, https, magnet, file, and content Uri types are all supported.
        val torrentUri = Uri.parse("magnet:?xt=urn:btih:JBJTRD53AZ4U4MXGXCI4IEB5LXHZDT7W&tr=http://nyaa.tracker.wf:7777/announce&tr=udp://tracker.coppersurfer.tk:6969/announce&tr=udp://tracker.internetwarriors.net:1337/announce&tr=udp://tracker.leechersparadise.org:6969/announce&tr=udp://tracker.opentrackr.org:1337/announce&tr=udp://open.stealth.si:80/announce&tr=udp://p4p.arenabg.com:1337/announce&tr=udp://mgtracker.org:6969/announce&tr=udp://tracker.tiny-vps.com:6969/announce&tr=udp://peerfect.org:6969/announce&tr=http://share.camoe.cn:8080/announce&tr=http://t.nyaatracker.com:80/announce&tr=https://open.kickasstracker.com:443/announce")
        val torrentSessionOptions = TorrentSessionOptions(
            downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            , onlyDownloadLargestFile = true
            , enableLogging = false
            , shouldStream = true
        )
        val torrentSession = TorrentSession(torrentSessionOptions)
        println("XXXXX")
        doAsync {
            torrentSession.start(this@MainActivity, torrentUri)
        }
        println("XXXXX")
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
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
