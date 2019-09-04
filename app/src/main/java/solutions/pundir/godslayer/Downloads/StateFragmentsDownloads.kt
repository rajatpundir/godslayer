package solutions.pundir.godslayer.Downloads

class StateFragmentsDownloads {
    var visibility_torrents = true
    var visibility_torrent_stats = false
    fun hide_all() {
        visibility_torrents = false
        visibility_torrent_stats = false
    }
    fun show_torrents() {
        hide_all()
        visibility_torrents = true
    }
    fun show_torrent_stats() {
        hide_all()
        visibility_torrent_stats = true
    }
}