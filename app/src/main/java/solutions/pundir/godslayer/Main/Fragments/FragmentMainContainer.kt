package solutions.pundir.godslayer.Main.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.FragmentHome
import solutions.pundir.godslayer.R

class FragmentMainContainer : Fragment(), AppCoordinator {
    internal lateinit var callback : AppCoordinator
    internal lateinit var dbHandler: GodslayerDBOpenHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main_container, container, false)
        return v
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentHome) {
            childFragment.callback_from_parent(callback, dbHandler)
        }
    }

    fun callback_from_parent(callback : AppCoordinator, dbHandler: GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE MAIN CONTAINER")
    }

    override fun callback_from_child_fragment() {
        println("Callback from Child Fragment to Fragment Main Conatianer.")
    }

}