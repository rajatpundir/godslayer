package solutions.pundir.godslayer.Home.Fragments

interface HomeMainContainerCoordinator {
    fun update_modules()
    fun update_languages(mid : Long)
    fun update_platforms(mid : Long, parent_id : Long)
    fun update_publishers(mid : Long, parent_id : Long)
    fun update_playlists(mid : Long, parent_id : Long)
    fun update_episodes(mid : Long, parent_id : Long)
    fun update_sources(mid : Long, parent_id : Long)
    fun download_source(mid : Long, rid : Long)
}