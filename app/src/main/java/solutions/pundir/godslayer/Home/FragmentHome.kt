package solutions.pundir.godslayer.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.Fragments.FragmentHomeMainContainer
import solutions.pundir.godslayer.Main.Fragments.AppCoordinator
import solutions.pundir.godslayer.R

class FragmentHome : Fragment(), HomeCoordinator {
    internal lateinit var callback : AppCoordinator
    internal lateinit var dbHandler : GodslayerDBOpenHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentHomeMainContainer) {
            childFragment.callback_from_parent(this, dbHandler)
        }
    }

    fun callback_from_parent(callback : AppCoordinator, dbHandler: GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE HOME")
    }

    override fun download_source(mid: Long, rid: Long) {
        callback.download_source(mid, rid)
    }

}