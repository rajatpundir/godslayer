package solutions.pundir.godslayer.Home.ViewPagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.Fragments.*

class ViewPagerAdapterHomeMainContainerFragments internal constructor(fm: FragmentManager, val dbHandler: GodslayerDBOpenHelper, val callback : HomeMainContainerCoordinator) : FragmentPagerAdapter(fm),
    HomeMainContainerFragmentsCoordinator {

    private val COUNT = 7
    var fragmentModules : FargmentHomeModules
    var fragmentLanguages : FargmentHomeLanguages
    var fragmentPlatforms : FargmentHomePlatforms
    var fragmentPublishers : FargmentHomePublishers
    var fragmentPlaylists : FargmentHomePlaylists
    var fragmentEpisodes : FargmentHomeEpisodes
    var fragmentSources : FargmentHomeSources

    init {
        fragmentModules = FargmentHomeModules(dbHandler)
        fragmentLanguages = FargmentHomeLanguages(dbHandler)
        fragmentPlatforms = FargmentHomePlatforms(dbHandler)
        fragmentPublishers = FargmentHomePublishers(dbHandler)
        fragmentPlaylists = FargmentHomePlaylists(dbHandler)
        fragmentEpisodes = FargmentHomeEpisodes(dbHandler)
        fragmentSources = FargmentHomeSources(dbHandler)
        fragmentModules.callback_from_parent(this)
        fragmentLanguages.callback_from_parent(this)
        fragmentPlatforms.callback_from_parent(this)
        fragmentPublishers.callback_from_parent(this)
        fragmentPlaylists.callback_from_parent(this)
        fragmentEpisodes.callback_from_parent(this)
        fragmentSources.callback_from_parent(this)
    }

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = fragmentModules
            1 -> fragment = fragmentLanguages
            2 -> fragment = fragmentPlatforms
            3 -> fragment = fragmentPublishers
            4 -> fragment = fragmentPlaylists
            5 -> fragment = fragmentEpisodes
            6 -> fragment = fragmentSources
        }
        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var name = ""
        when(position) {
            0 -> name = "Modules"
            1 -> name = "Languages"
            2 -> name = "Platforms"
            3 -> name = "Publishers"
            4 -> name = "Playlists"
            5 -> name = "Episodes"
            6 -> name = "Sources"
        }
        return name
    }

    override fun update_modules() {
        println("UPDATE MODULES")
        fragmentModules.update_recycler_view()
        generate_click("MODULES")
    }

    override fun update_languages(mid: Long) {
        println("UPDATE LANGUAGES")
        fragmentLanguages.update_recycler_view(mid)
        generate_click("LANGUAGES")
    }

    override fun update_platforms(mid: Long, parent_id: Long) {
        println("UPDATE PLATFORMS")
        fragmentPlatforms.update_recycler_view(mid, parent_id)
        generate_click("PLATFORMS")
    }

    override fun update_publishers(mid: Long, parent_id: Long) {
        println("UPDATE PUBLISHERS")
        fragmentPublishers.update_recycler_view(mid, parent_id)
        generate_click("PUBLISHERS")
    }

    override fun update_playlists(mid: Long, parent_id: Long) {
        println("UPDATE PLAYLISTS")
        fragmentPlaylists.update_recycler_view(mid, parent_id)
        generate_click("PLAYLISTS")
    }

    override fun update_episodes(mid: Long, parent_id: Long) {
        println("UPDATE EPISODES")
        fragmentEpisodes.update_recycler_view(mid, parent_id)
        generate_click("EPISODES")
    }

    override fun update_sources(mid: Long, parent_id: Long) {
        println("UPDATE SOURCES")
        fragmentSources.update_recycler_view(mid, parent_id)
        generate_click("SOURCES")
    }

    override fun download_source(mid: Long, rid: Long) {
        println("DOWNLOAD SOURCE")
        callback.download_source(mid, rid)
    }

    override fun generate_click(button_name : String) {
        callback.generate_click(button_name)
    }


}
