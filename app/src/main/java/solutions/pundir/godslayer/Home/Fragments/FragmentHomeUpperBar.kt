package solutions.pundir.godslayer.Home.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home_upper_bar.*
import solutions.pundir.godslayer.Home.HomeCoordinator
import solutions.pundir.godslayer.R

class FragmentHomeUpperBar : Fragment() {
    internal lateinit var callback : HomeCoordinator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_upper_bar, container, false)
        return v
    }

    fun generate_click(button_name : String) {
        when(button_name) {
            "MODULES" -> button_home_modules.performClick()
            "LANGUAGES" -> button_home_languages.performClick()
            "PLATFORMS" -> button_home_platforms.performClick()
            "PUBLISHERS" -> button_home_publishers.performClick()
            "PLAYLISTS" -> button_home_playlists.performClick()
            "EPISODES" -> button_home_episodes.performClick()
            "SOURCES" -> button_home_sources.performClick()
        }
    }

}