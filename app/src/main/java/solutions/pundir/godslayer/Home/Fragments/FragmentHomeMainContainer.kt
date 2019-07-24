package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.Home.StateHomeMainContainer
import solutions.pundir.godslayer.Main.Fragments.AppCoordinator
import solutions.pundir.godslayer.R

class FragmentHomeMainContainer : Fragment(), HomeCoordinator {
    internal lateinit var callback : AppCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var appStateHome : StateAppHome
    internal val stateHomeMainContainer = StateHomeMainContainer()

    fun callback_from_parent(callback : AppCoordinator, dbHandler : GodslayerDBOpenHelper, appStateHome : StateAppHome) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.appStateHome = appStateHome
        println("INSIDE HOME CONTAINER")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_main_container, container, false)
        return v
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FargmentHomeModules) {
            childFragment.callback_from_parent(this, dbHandler, appStateHome)
            stateHomeMainContainer.modules_fragment = childFragment
        }
        if (childFragment is FargmentHomeLanguages) {
            childFragment.callback_from_parent(this, dbHandler, appStateHome)
            stateHomeMainContainer.languages_fragment = childFragment
        }
        if (childFragment is FargmentHomePlatforms) {
            childFragment.callback_from_parent(this, dbHandler, appStateHome)
            stateHomeMainContainer.platforms_fragment = childFragment
        }
        if (childFragment is FargmentHomePublishers) {
            childFragment.callback_from_parent(this, dbHandler, appStateHome)
            stateHomeMainContainer.publishers_fragment = childFragment
        }
        if (childFragment is FargmentHomePlaylists) {
            childFragment.callback_from_parent(this, dbHandler, appStateHome)
            stateHomeMainContainer.playlists_fragment = childFragment
        }
        if (childFragment is FargmentHomeEpisodes) {
            childFragment.callback_from_parent(this, dbHandler, appStateHome)
            stateHomeMainContainer.episodes_fragment = childFragment
        }
        if (childFragment is FargmentHomeSources) {
            childFragment.callback_from_parent(this, dbHandler, appStateHome)
            stateHomeMainContainer.sources_fragment = childFragment
        }
    }

    override fun update_modules() {
        println("UPDATE MODULES")
    }

    override fun update_languages(mid: Long) {
        println("UPDATE LANGUAGES")
        stateHomeMainContainer.languages_fragment.update_recycler_view(mid)
    }

    override fun update_platforms(mid: Long, parent_id: Long) {
        println("UPDATE PLATFORMS")
        stateHomeMainContainer.platforms_fragment.update_recycler_view(mid, parent_id)
    }

    override fun update_publishers(mid: Long, parent_id: Long) {
        println("UPDATE PUBLISHERS")
        stateHomeMainContainer.publishers_fragment.update_recycler_view(mid, parent_id)
    }

    override fun update_playlists(mid: Long, parent_id: Long) {
        println("UPDATE PLAYLISTS")
        stateHomeMainContainer.playlists_fragment.update_recycler_view(mid, parent_id)
    }

    override fun update_episodes(mid: Long, parent_id: Long) {
        println("UPDATE EPISODES")
        stateHomeMainContainer.episodes_fragment.update_recycler_view(mid, parent_id)
    }

    override fun update_sources(mid: Long, parent_id: Long) {
        println("UPDATE SOURCES")
        stateHomeMainContainer.sources_fragment.update_recycler_view(mid, parent_id)
    }

}