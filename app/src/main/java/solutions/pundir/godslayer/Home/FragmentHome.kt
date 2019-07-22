package solutions.pundir.godslayer.Home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_episodes.*
import kotlinx.android.synthetic.main.fragment_home_main_container.*
import kotlinx.android.synthetic.main.fragment_home_playlists.*
import kotlinx.android.synthetic.main.fragment_home_upper_bar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import solutions.pundir.godslayer.Database.GodslayerDBOpenHelper
import solutions.pundir.godslayer.Home.RecycleViewAdapters.RecycleViewAdapterModules
import solutions.pundir.godslayer.R

val fragmentStateHome = StateFragmentsHome()

class FragmentHome : Fragment() {
    companion object {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup__home_upper_bar_buttons()
    }

    fun set_fragment_visibility() {
        fragment_layout_home_modules?.visibility = if (fragmentStateHome.visibility_home_modules) View.VISIBLE else View.GONE
        fragment_layout_home_languages?.visibility = if (fragmentStateHome.visibility_home_languages) View.VISIBLE else View.GONE
        fragment_layout_home_platforms?.visibility = if (fragmentStateHome.visibility_home_platforms) View.VISIBLE else View.GONE
        fragment_layout_home_publishers?.visibility = if (fragmentStateHome.visibility_home_publishers) View.VISIBLE else View.GONE
        fragment_layout_home_playlists?.visibility = if (fragmentStateHome.visibility_home_playlists) View.VISIBLE else View.GONE
        fragment_layout_home_episodes?.visibility = if (fragmentStateHome.visibility_home_episodes) View.VISIBLE else View.GONE
        fragment_layout_home_sources?.visibility = if (fragmentStateHome.visibility_home_sources) View.VISIBLE else View.GONE
    }

    fun setup__home_upper_bar_buttons() {
        button_home_modules.setOnClickListener {
            fragmentStateHome.show_home_modules()
            set_fragment_visibility()
        }
        button_home_languages.setOnClickListener {
            fragmentStateHome.show_home_languages()
            set_fragment_visibility()
        }
        button_home_platforms.setOnClickListener {
            fragmentStateHome.show_home_platforms()
            set_fragment_visibility()
        }
        button_home_publishers.setOnClickListener {
            fragmentStateHome.show_home_publishers()
            set_fragment_visibility()
        }
        button_home_playlists.setOnClickListener {
            fragmentStateHome.show_home_playlists()
            set_fragment_visibility()
        }
        button_home_episodes.setOnClickListener {
            fragmentStateHome.show_home_episodes()
            set_fragment_visibility()
        }
        button_home_sources.setOnClickListener {
            fragmentStateHome.show_home_sources()
            set_fragment_visibility()
        }
    }

}