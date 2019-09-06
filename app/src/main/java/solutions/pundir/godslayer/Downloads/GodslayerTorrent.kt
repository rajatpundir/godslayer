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
import solutions.pundir.godslayer.Downloads.Fragments.FragmentDownloadsTorrentsQueue
import solutions.pundir.godslayer.Downloads.GodslayerTorrentInfo.GodslayerTorrentInfo

class GodslayerTorrent internal constructor(val context: Context, val dbHandler: GodslayerDBOpenHelper, val mid: Long, val rid: Long, val parent_fragment : FragmentDownloadsTorrentsQueue) : TorrentSessionListener{
    internal var module_id = mid
    internal var source_id = rid
    internal var parent_id : Long = -1
    internal var source_name = ""
    internal var episode_name = ""
    internal var magnet_link_id : Long = -1
    internal var magnet_url = ""
    internal var torrent_state = "Downloading metadata..."
    internal var filename = ""
    internal var torrent_progress = 0
    internal var isPaused = false
    internal var metadata_ready = false
    internal val torrent_info = GodslayerTorrentInfo()
    lateinit var torrentUri : Uri
    lateinit var torrentSessionOptions : TorrentSessionOptions
    lateinit var torrentSession: TorrentSession
    var position  = 0

    init {
        get_magnet_link()
    }

    fun callback_from_parent_fragment(position : Int) {
        this.position = position
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
//        magnet_url = "magnet:?xt=urn:btih:TG6PYIMOXOCSLKVLHLHJYWBMYDSAMLJY&tr=udp://tracker.coppersurfer.tk:6969/announce&tr=udp://tracker.internetwarriors.net:1337/announce&tr=udp://tracker.leechersparadise.org:6969/announce&tr=udp://tracker.opentrackr.org:1337/announce&tr=udp://open.stealth.si:80/announce&tr=udp://p4p.arenabg.com:1337/announce&tr=udp://mgtracker.org:6969/announce&tr=udp://tracker.tiny-vps.com:6969/announce&tr=udp://peerfect.org:6969/announce&tr=http://share.camoe.cn:8080/announce&tr=http://t.nyaatracker.com:80/announce&tr=https://open.kickasstracker.com:443/announce"
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
            uiThread {
                context?.toast("Downloading")
            }
        }
    }


    fun pause_or_resume_session() {
        if (torrent_state != "Downloading metadata...") {
            doAsync {
                if (isPaused == false) {
                    isPaused = true
                    torrentSession.pause()
                    torrent_state = "Paused"
                    parent_fragment.refresh_torrent_in_adapter(position)
                    uiThread {
                        context?.toast("Paused")
                    }
                }
                else if (isPaused == true) {
                    isPaused = false
                    torrentSession.resume()
                    torrent_state = "Downloading..."
                    parent_fragment.refresh_torrent_in_adapter(position)
                    uiThread {
                        context?.toast("Resumed")
                    }
                }
            }
        }
    }

    fun stop_session() {
        parent_fragment.remove_torrent_and_update_adapter(module_id, source_id)
        doAsync {
            torrentSession.stop()
            uiThread {
                context?.toast("Stopped")
            }
        }
    }

    override fun onAddTorrent(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onAddTorrent")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Downloading..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onBlockUploaded(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onBlockUploaded")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Block Uploaded..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onMetadataFailed(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onMetadataFailed")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Failed to get metadata..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onMetadataReceived(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        metadata_ready = true
        torrent_info.set_stats(torrentHandle, torrentSessionStatus)
        println(torrentSessionStatus.progress.toString())
        println("onMetadataReceived")
        println("---------------------------------------------------------------------------------------")
        println("torrentHandle.swig().max_connections()")
        println(torrentHandle.swig().max_connections())
        println("torrentHandle.swig().max_uploads()")
        println(torrentHandle.swig().max_uploads())
        // Maybe use progress PPM to calculate ETA.
        println("torrentHandle.swig().status().progress_ppm")
        println(torrentHandle.swig().status().progress_ppm)
        println("torrentHandle.swig().status().seed_rank")
        println(torrentHandle.swig().status().seed_rank)
        println("###########################################################")
        var count = 0
        for (i in 0..(torrentHandle.torrentFile().numFiles() - 1)) {
            println("Index: " + i.toString() + " " + torrentHandle.torrentFile().files().filePath(i))
        }
        println("###########################################################")
        doAsync {
            filename = torrentHandle.savePath() + "/" + torrentHandle.name()
            torrent_state = "Metadata received..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onPieceFinished(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        torrent_info.set_stats(torrentHandle, torrentSessionStatus)
        println(torrentSessionStatus.progress.toString())
        println("onPieceFinished")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Downloading..."
            torrent_progress = (torrentSessionStatus.progress * 100).toInt()
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
                torrent_info.refresh_torrent_stats()
            }
        }
    }

    override fun onTorrentDeleteFailed(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentDeleteFailed")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Torrent Delete Failed..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onTorrentDeleted(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentDeleted")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Torrent Deleted..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
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
    }

    override fun onTorrentPaused(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentPaused")
        println("---------------------------------------------------------------------------------------")
        torrentHandle.saveResumeData()
        doAsync {
            torrent_state = "Paused"
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onTorrentRemoved(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentRemoved")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Torrent Removed..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onTorrentResumed(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onTorrentResumed")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Downloading..."
            uiThread {
                parent_fragment.refresh_torrent_in_adapter(position)
            }
        }
    }

}