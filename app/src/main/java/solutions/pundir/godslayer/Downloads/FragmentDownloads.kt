package solutions.pundir.godslayer.Downloads

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.masterwok.simpletorrentandroid.TorrentSession
import com.masterwok.simpletorrentandroid.TorrentSessionOptions
import org.jetbrains.anko.doAsync
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Main.Fragments.AppCoordinator
import solutions.pundir.godslayer.R
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class FragmentDownloads : Fragment() {
    internal lateinit var callback : AppCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun callback_from_parent(callback : AppCoordinator, dbHandler: GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE DOWNLOADS")
    }

    fun download_source(mid : Long, rid : Long) {
        println("DOWNLOADING")
        doAsync {
            var cursor = dbHandler.getMagnetLinkIdFromSourceId(mid, rid)
            cursor!!.moveToFirst()
            var parent_id = cursor.getString(cursor.getColumnIndex("PARENT_ID")).toLong()
            var magnet_link_id = cursor.getString(cursor.getColumnIndex("MAGNET_LINK_ID")).toLong()
            cursor.close()
            cursor = dbHandler.getMagnetLink(mid, magnet_link_id)
            cursor!!.moveToFirst()
            var magnet_url = cursor.getString(cursor.getColumnIndex("MAGNET_URL"))
            cursor.close()
            uiThread {
                context?.toast("Starting download...")
            }
            val torrentUri = Uri.parse(magnet_url)
            val torrentSessionOptions = TorrentSessionOptions(
                downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , onlyDownloadLargestFile = true
                , enableLogging = false
                , shouldStream = true
            )
            val torrentSession = TorrentSession(torrentSessionOptions)
            context?.let { torrentSession.start(it, torrentUri) }
            uiThread {
                context?.toast("Downloading... Please check your Downlaods diretory.")
            }
        }
    }

}