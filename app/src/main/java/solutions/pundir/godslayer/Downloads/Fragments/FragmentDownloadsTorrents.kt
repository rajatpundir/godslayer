package solutions.pundir.godslayer.Downloads.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.R

class FragmentDownloadsTorrents : Fragment() {
    internal lateinit var callback : FragmentDownloadsMainContainerCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper

    fun callback_from_parent(callback : FragmentDownloadsMainContainerCoordinator, dbHandler : GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE TORRENTS")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_downloads_torrents, container, false)
        return v
    }

    fun download_source(mid: Long, rid: Long) {
        // Create a Godslayer Torrent object with whatever properties.
        // create an items array of torrent type objects, each item should have a corresponding state.
        // there should be buttons on items that can change the item state.
        println("DOWNLOADING")
        println(mid)
        println(rid)
        println("ZZZZZZZZZZZZZZ")
//        doAsync {
//            var cursor = dbHandler.getMagnetLinkIdFromSourceId(mid, rid)
//            cursor!!.moveToFirst()
//            var parent_id = cursor.getString(cursor.getColumnIndex("PARENT_ID")).toLong()
//            var magnet_link_id = cursor.getString(cursor.getColumnIndex("MAGNET_LINK_ID")).toLong()
//            cursor.close()
//            cursor = dbHandler.getMagnetLink(mid, magnet_link_id)
//            cursor!!.moveToFirst()
//            var magnet_url = cursor.getString(cursor.getColumnIndex("MAGNET_URL"))
//            cursor.close()
//            uiThread {
//                context?.toast("Starting...")
//            }
//            val torrentUri = Uri.parse(magnet_url)
//            val torrentSessionOptions = TorrentSessionOptions(
//                downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                , onlyDownloadLargestFile = true
//                , enableLogging = false
//                , shouldStream = true
//            )
//            val torrentSession = TorrentSession(torrentSessionOptions)
//            context?.let { torrentSession.start(it, torrentUri) }
//            uiThread {
//                context?.toast("Downloading... Please check your Downlaods diretory.")
//            }
//        }
    }

}