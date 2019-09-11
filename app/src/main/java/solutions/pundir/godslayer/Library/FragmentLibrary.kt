package solutions.pundir.godslayer.Library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solutions.pundir.godslayer.Library.Fragments.FragmentsLibraryMainContainer
import solutions.pundir.godslayer.R

class FragmentLibrary  : Fragment(), LibraryCoordinator {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_library, container, false)
        return v
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentsLibraryMainContainer) {
            childFragment.callback_from_parent(this)
        }
    }

    fun refresh_torrent_in_adapter(position : Int) {
    }

    fun remove_torrent_and_update_adapter(mid : Long, rid : Long) {
    }

}