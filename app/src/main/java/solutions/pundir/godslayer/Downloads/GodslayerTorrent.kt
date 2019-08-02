package solutions.pundir.godslayer.Downloads

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.frostwire.jlibtorrent.TorrentHandle
import com.masterwok.simpletorrentandroid.TorrentSession
import com.masterwok.simpletorrentandroid.TorrentSessionOptions
import com.masterwok.simpletorrentandroid.contracts.TorrentSessionListener
import com.masterwok.simpletorrentandroid.models.TorrentSessionStatus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.RecycleViewAdapters.RecycleViewAdapterTorrents

class GodslayerTorrent internal constructor(val context: Context, val dbHandler: GodslayerDBOpenHelper, val mid: Long, val rid: Long) : TorrentSessionListener{
    internal var module_id = mid
    internal var source_id = rid
    internal var parent_id : Long = -1
    internal var source_name = ""
    internal var episode_name = ""
    internal var magnet_link_id : Long = -1
    internal var magnet_url = ""
    internal var torrent_state = "Downloading metadata..."
    lateinit var torrentUri : Uri
    lateinit var torrentSessionOptions : TorrentSessionOptions
    lateinit var torrentSession: TorrentSession
    lateinit var recycler_view_callback : RecycleViewAdapterTorrents

    init {
        get_magnet_link()
    }

    fun callback_from_recycler_view(callback : RecycleViewAdapterTorrents) {
        this.recycler_view_callback = callback
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
            magnet_url = cursor.getString(cursor.getColumnIndex("MAGNET_URL")).trim()
            cursor.close()
            cursor = dbHandler.getEpisode(module_id, parent_id)
            cursor!!.moveToFirst()
            episode_name = cursor.getString(cursor.getColumnIndex("NAME"))
            cursor.close()
            uiThread {
                context?.toast("Starting " + episode_name + " - " + source_name)
            }
            set_torrent_uri_options_and_session()
        }
    }

    fun set_torrent_uri_options_and_session() {
        torrentUri = Uri.parse(magnet_url)
        torrentSessionOptions = TorrentSessionOptions(
            downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            , onlyDownloadLargestFile = true
            , enableLogging = false
            , shouldStream = true
        )
        torrentSession = TorrentSession(torrentSessionOptions)
        torrentSession.listener = this@GodslayerTorrent
        doAsync {
            uiThread {
                context?.toast("Starting")
            }
            context.let { torrentSession.start(it, torrentUri) }
            torrent_state = "Downloading..."
            if (::recycler_view_callback.isInitialized) {
                recycler_view_callback.update_torrent_state(torrent_state)
            }
            uiThread {
                context?.toast("Downloading")
            }
        }
    }


    fun pause_or_resume_session() {
        doAsync {
            if (torrent_state != "Paused") {
                torrentSession.pause()
                torrent_state = "Paused"
                if (::recycler_view_callback.isInitialized) {
                    recycler_view_callback.update_torrent_state(torrent_state)
                    recycler_view_callback.holder.pauseOrResumeButton.text = "RESUME"
                }
                uiThread {
                    context?.toast("Paused")
                }
            }
            else if (torrent_state != "Downloading...") {
                torrentSession.resume()
                torrent_state = "Downloading..."
                if (::recycler_view_callback.isInitialized) {
                    recycler_view_callback.update_torrent_state(torrent_state)
                    recycler_view_callback.holder.pauseOrResumeButton.text = "PAUSE"
                }
                uiThread {
                    context?.toast("Resumed")
                }
            }
        }
    }

    fun stop_session() {
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.call_parent_to_remove_item(module_id, source_id)
            doAsync {
                torrentSession.stop()
                uiThread {
                    context?.toast("Stopped")
                }
            }
        }
    }

    override fun onAddTorrent(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onAddTorrent")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Addded torrent.")
        }
    }

    override fun onBlockUploaded(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onBlockUploaded")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Block Uploaded.")
        }
    }

    override fun onMetadataFailed(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onMetadataFailed")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Failed to get metadata.")
        }
    }

    override fun onMetadataReceived(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onMetadataReceived")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Metadata received.")
        }
    }

    override fun onPieceFinished(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onPieceFinished")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Downloading...")
            recycler_view_callback.update_torrent_progress(torrentSessionStatus.progress)
        }
    }

    override fun onTorrentDeleteFailed(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentDeleteFailed")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Torrent Delete Failed.")
        }
    }

    override fun onTorrentDeleted(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentDeleted")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Torrent Deleted.")
        }
    }

    override fun onTorrentError(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentError")
        println("---------------------------------------------------------------------------------------")
    }

    override fun onTorrentFinished(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentFinished")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Torrent Finished.")
        }
    }

    override fun onTorrentPaused(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentPaused")
        println("---------------------------------------------------------------------------------------")
    }

    override fun onTorrentRemoved(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentRemoved")
        println("---------------------------------------------------------------------------------------")
        if (::recycler_view_callback.isInitialized) {
            recycler_view_callback.update_torrent_state("Torrent Removed.")
        }
    }

    override fun onTorrentResumed(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentResumed")
        println("---------------------------------------------------------------------------------------")
    }

}