package solutions.pundir.godslayer.Downloads

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.masterwok.simpletorrentandroid.TorrentSession
import com.masterwok.simpletorrentandroid.TorrentSessionOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper

class GodslayerTorrent internal constructor(val context: Context?, val dbHandler: GodslayerDBOpenHelper, val mid: Long, val rid: Long){
    internal var module_id = mid
    internal var source_id = rid
    internal var parent_id : Long = -1
    internal var source_name = ""
    internal var episode_name = ""
    internal var magnet_link_id : Long = -1
    internal var magnet_url = ""
    internal var torrent_state = "PAUSED"
    lateinit var torrentUri : Uri
    lateinit var torrentSessionOptions : TorrentSessionOptions
    lateinit var torrentSession: TorrentSession

    init {
        get_magnet_link()
    }

    fun get_magnet_link() {
        doAsync {
            var cursor = dbHandler.getMagnetLinkIdFromSourceId(module_id, source_id)
            cursor!!.moveToFirst()
            parent_id = cursor.getString(cursor.getColumnIndex("PARENT_ID")).toLong()
            magnet_link_id = cursor.getString(cursor.getColumnIndex("MAGNET_LINK_ID")).toLong()
            source_name = cursor.getString(cursor.getColumnIndex("NAME"))
            cursor.close()
            cursor = dbHandler.getMagnetLink(mid, magnet_link_id)
            cursor!!.moveToFirst()
            magnet_url = cursor.getString(cursor.getColumnIndex("MAGNET_URL"))
            cursor.close()
            cursor = dbHandler.getEpisode(module_id, parent_id)
            cursor!!.moveToFirst()
            episode_name = cursor.getString(cursor.getColumnIndex("NAME"))
            cursor.close()
            uiThread {
                context?.toast("Starting " + episode_name + " - " + source_name)
            }
        }
    }

    fun set_torrent_uri_and_session_options() {
        torrentUri = Uri.parse(magnet_url)
        torrentSessionOptions = TorrentSessionOptions(
            downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            , onlyDownloadLargestFile = true
            , enableLogging = false
            , shouldStream = false
        )
    }

    fun start_session() {
        doAsync {
            torrentSession = TorrentSession(torrentSessionOptions)
            context?.let { torrentSession.start(it, torrentUri) }
            uiThread {
                context?.toast("Downloading...")
            }
        }
    }

    fun toast_magnet_url() {
        context?.toast(magnet_url)
    }

}