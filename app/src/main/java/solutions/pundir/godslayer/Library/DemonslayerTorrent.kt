package solutions.pundir.godslayer.Library

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
import solutions.pundir.godslayer.Downloads.GodslayerTorrentInfo.GodslayerTorrentInfo

class DemonslayerTorrent internal constructor(val context: Context, val magnet_url : String, val parent_chain : Demonchain) : TorrentSessionListener{
    internal var previous_node_url = null
    internal var torrent_state = "Downloading metadata..."
    internal var torrent_progress = 0
    internal var isPaused = false
    internal var metadata_ready = false
    internal val torrent_info = GodslayerTorrentInfo()
    lateinit var torrentUri : Uri
    lateinit var torrentSessionOptions : TorrentSessionOptions
    lateinit var torrentSession: TorrentSession
    var position  = 0

    init {
        set_torrent_uri_options_and_session()
    }

    fun callback_from_parent(position : Int) {
        this.position = position
    }

    fun set_torrent_uri_options_and_session() {
        torrentUri = Uri.parse(magnet_url)
        torrentSessionOptions = TorrentSessionOptions(
            downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            , onlyDownloadLargestFile = false
            , enableLogging = false
            , shouldStream = false
        )
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath)
        torrentSession = TorrentSession(torrentSessionOptions)
        torrentSession.listener = this@DemonslayerTorrent
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
                    parent_chain.refresh_torrent_in_adapter(position)
                    uiThread {
                        context?.toast("Paused")
                    }
                }
                else if (isPaused == true) {
                    isPaused = false
                    torrentSession.resume()
                    torrent_state = "Downloading..."
                    parent_chain.refresh_torrent_in_adapter(position)
                    uiThread {
                        context?.toast("Resumed")
                    }
                }
            }
        }
    }

    fun stop_session() {
//        parent_chain.remove_torrent_and_update_adapter(module_id, source_id)
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
                parent_chain.refresh_torrent_in_adapter(position)
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
                parent_chain.refresh_torrent_in_adapter(position)
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
                parent_chain.refresh_torrent_in_adapter(position)
            }
        }
    }

    override fun onMetadataReceived(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println("onMetadataReceived")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Metadata received..."
            uiThread {
                parent_chain.refresh_torrent_in_adapter(position)
                metadata_ready = true
                for (i in 0..(torrentHandle.torrentFile().numFiles() - 1)) {
                    torrent_info.files.files.add(torrentHandle.torrentFile().files().filePath(i))
                }
                torrent_info.set_stats(torrentHandle, torrentSessionStatus)
            }
        }
    }

    override fun onPieceFinished(torrentHandle: TorrentHandle, torrentSessionStatus: TorrentSessionStatus) {
        println(torrentSessionStatus.progress.toString())
        println("onPieceFinished")
        println("---------------------------------------------------------------------------------------")
        doAsync {
            torrent_state = "Downloading..."
            torrent_progress = (torrentSessionStatus.progress * 100).toInt()
            uiThread {
                parent_chain.refresh_torrent_in_adapter(position)
                torrent_info.set_stats(torrentHandle, torrentSessionStatus)
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
                parent_chain.refresh_torrent_in_adapter(position)
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
                parent_chain.refresh_torrent_in_adapter(position)
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
                parent_chain.refresh_torrent_in_adapter(position)
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
                parent_chain.refresh_torrent_in_adapter(position)
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
                parent_chain.refresh_torrent_in_adapter(position)
            }
        }
    }

}