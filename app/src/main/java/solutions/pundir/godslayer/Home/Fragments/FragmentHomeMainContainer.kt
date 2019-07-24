package solutions.pundir.godslayer.Home.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.R

class FragmentHomeMainContainer : Fragment(), HomeCoordinator {
    internal lateinit var callback : HomeCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper
    internal lateinit var appStateHome : StateAppHome

    fun callback_from_parent(callback : HomeCoordinator, dbHandler : GodslayerDBOpenHelper, appStateHome : StateAppHome) {
        this.callback = callback
        this.dbHandler = dbHandler
        this.appStateHome = appStateHome
        println("INSIDE HOME CONTAINER")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_main_container, container, false)
        return v
    }

    override fun callback_from_child_fragment() {
        println("Callback from child fragment to Fragment Home Main Container")
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FargmentHomeModules) {
            childFragment.callback_from_parent(callback, dbHandler, appStateHome)
        }
        if (childFragment is FargmentHomeLanguages) {
            childFragment.callback_from_parent(callback, dbHandler, appStateHome)
        }
        if (childFragment is FargmentHomePlatforms) {
            childFragment.callback_from_parent(callback, dbHandler, appStateHome)
        }
        if (childFragment is FargmentHomePublishers) {
            childFragment.callback_from_parent(callback, dbHandler, appStateHome)
        }
        if (childFragment is FargmentHomePlaylists) {
            childFragment.callback_from_parent(callback, dbHandler, appStateHome)
        }
        if (childFragment is FargmentHomeEpisodes) {
            childFragment.callback_from_parent(callback, dbHandler, appStateHome)
        }
        if (childFragment is FargmentHomeSources) {
            childFragment.callback_from_parent(callback, dbHandler, appStateHome)
        }
    }



}