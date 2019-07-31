package solutions.pundir.godslayer.Downloads

class StateFragmentsDownloads {
    var visibility_downloads_torrents = true
    var visibility_downloads_torrent_stats = false
    fun hide_all() {
        visibility_downloads_torrents = false
        visibility_downloads_torrent_stats = false
    }
    fun show_downloads_torrents() {
        hide_all()
        visibility_downloads_torrents = true
    }
    fun show_downloads_torrent_stats() {
        hide_all()
        visibility_downloads_torrent_stats = true
    }
}