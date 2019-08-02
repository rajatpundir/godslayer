package solutions.pundir.godslayer.Main.Fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.masterwok.simpletorrentandroid.TorrentSession
import com.masterwok.simpletorrentandroid.TorrentSessionOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Downloads.FragmentDownloads
import solutions.pundir.godslayer.Home.FragmentHome
import solutions.pundir.godslayer.Inbox.FragmentInbox
import solutions.pundir.godslayer.Library.FragmentLibrary
import solutions.pundir.godslayer.Main.MasterCoordinator
import solutions.pundir.godslayer.Main.StateMainContainer
import solutions.pundir.godslayer.R
import solutions.pundir.godslayer.Subscriptions.FragmentSubscriptions

class FragmentMainContainer : Fragment(), AppCoordinator {
    internal lateinit var callback : MasterCoordinator
    internal lateinit var dbHandler: GodslayerDBOpenHelper
    internal val stateMainContainer = StateMainContainer()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main_container, container, false)
        return v
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentInbox) {
            stateMainContainer.inbox_fragment = childFragment
        }
        if (childFragment is FragmentHome) {
            childFragment.callback_from_parent(this, dbHandler)
            stateMainContainer.home_fragment = childFragment
        }
        if (childFragment is FragmentSubscriptions) {
            stateMainContainer.subscriptions_fragment = childFragment
        }
        if (childFragment is FragmentDownloads) {
            childFragment.callback_from_parent(this, dbHandler)
            stateMainContainer.downloads_fragment = childFragment
        }
        if (childFragment is FragmentLibrary) {
            stateMainContainer.library_fragment = childFragment
        }
    }

    fun callback_from_parent(callback : MasterCoordinator, dbHandler: GodslayerDBOpenHelper) {
        this.callback = callback
        this.dbHandler = dbHandler
        println("INSIDE MAIN CONTAINER")
    }

    override fun callback_from_child_fragment() {
        println("Callback from Child Fragment to Fragment Main Conatianer.")
    }

    override fun download_source(mid: Long, rid: Long) {
        stateMainContainer.downloads_fragment.download_source(mid, rid)
    }

}