package solutions.pundir.godslayer.Downloads.GodslayerTorrentInfo

import com.frostwire.jlibtorrent.TorrentHandle
import com.masterwok.simpletorrentandroid.models.TorrentSessionStatus

class GodslayerTorrentInfo {
    val details = GodslayerTorrentInfoDetails()
    val status = GodslayerTorrentInfoStatus()
    val files = GodslayerTorrentInfoFiles()
    val trackers = GodslayerTorrentInfoTrackers()
    val peers = GodslayerTorrentInfoPeers()
    val pieces = GodslayerTorrentInfoPieces()
    var refresh_torrent_stats_flag = false
    lateinit var refresh_stats_callback : GodslayerTorrentInfoListener

    fun set_stats(torrentHandle: TorrentHandle, torrentSessionStatus : TorrentSessionStatus) {
        this.details.torrent_storage_path = torrentHandle.swig().status().save_path.toString()
        this.details.torrent_number_of_files = torrentHandle.torrentFile().numFiles().toString()
        this.details.torrent_total_size = torrentHandle.torrentFile().totalSize().toString()
        this.details.torrent_speed_limit_download = torrentHandle.downloadLimit.toString()
        this.details.torrent_speed_limit_upload = torrentHandle.uploadLimit.toString()
        this.details.torrent_hash = torrentHandle.infoHash().toString()
        this.status.torrent_name = torrentHandle.torrentFile().name().toString()
        this.status.torrent_download_speed = (torrentHandle.status().downloadRate() / 1024).toString() + " KB/s"
        this.status.torrent_upload_speed = (torrentHandle.status().uploadRate() / 1024).toString() + " KB/s"
        this.status.torrent_progress = (torrentSessionStatus.progress * 100).toInt()
        this.status.torrent_downloaded = (torrentHandle.swig().status().all_time_download.toInt() / 1024).toString() + " KB"
        this.status.torrent_leechers = torrentHandle.swig().status().list_peers.toString()
        this.status.torrent_seeders = torrentHandle.swig().status().list_seeds.toString()
        this.status.torrent_uploaded = (torrentHandle.swig().status().all_time_upload.toInt() / 1024).toString() + " KB"
        this.status.torrent_active_time = torrentHandle.swig().status()._active_duration.toString()
        this.status.torrent_seeding_time = torrentHandle.swig().status()._seeding_duration.toString()
        this.status.pieces = torrentHandle.swig().status().verified_pieces.count().toString() + " / " + torrentHandle.torrentFile().numPieces().toString() + " (" + (torrentHandle.torrentFile().pieceLength() / 1024).toString() + " KB)"
        this.status.torrent_download_speed = (torrentHandle.status().downloadRate() / 1024).toString() + " KB/s"
        this.status.torrent_upload_speed = (torrentHandle.status().uploadRate() / 1024).toString() + " KB/s"
        this.status.torrent_progress = (torrentSessionStatus.progress * 100).toInt()
        this.trackers.announcing_to_dht = torrentHandle.swig().status().announcing_to_dht
        this.trackers.announcing_to_lsd = torrentHandle.swig().status().announcing_to_lsd
        this.trackers.announcing_to_trackers = torrentHandle.swig().status().announcing_to_trackers
        this.pieces.torrent_number_of_pieces = torrentHandle.torrentFile().numPieces().toString()
        this.pieces.torrent_piece_size = torrentHandle.torrentFile().pieceLength().toString()
        this.pieces.torrent_pieces_downloaded = torrentHandle.swig().status().verified_pieces.toString()
        if (refresh_torrent_stats_flag && ::refresh_stats_callback.isInitialized) {
            this.refresh_stats_callback.refresh_torrent_stats()
        }
    }

    fun refresh_torrent_stats() {
        if (refresh_torrent_stats_flag && ::refresh_stats_callback.isInitialized) {
            this.refresh_stats_callback.refresh_torrent_stats()
        }
    }

}