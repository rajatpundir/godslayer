package solutions.pundir.godslayer.Downloads.Fragments

interface FragmentDownloadsMainContainerCoordinator {
    fun download_source(mid : Long, rid : Long)
    fun show_torrent_stats(index : Int)
}