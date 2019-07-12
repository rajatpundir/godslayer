package solutions.pundir.godslayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bottom_bar.*
import kotlinx.android.synthetic.main.fragment_main_container.*

class MainActivity : AppCompatActivity(), FragmentCoordinator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup_bottom_bar_buttons()
    }

    // TODO
    // Create basic structure of folders and a python program to convert it into a sqlite db.
    // Display content inside app by reading sqlite db.


    fun setup_bottom_bar_buttons() {
        button_home.setOnClickListener {
            fragment_layout_home?.visibility = View.VISIBLE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_subscriptions.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.VISIBLE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_inbox.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.VISIBLE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_library.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.VISIBLE
            fragment_layout_downloads?.visibility = View.GONE
        }
        button_downloads.setOnClickListener {
            fragment_layout_home?.visibility = View.GONE
            fragment_layout_subscriptions?.visibility = View.GONE
            fragment_layout_inbox?.visibility = View.GONE
            fragment_layout_library?.visibility = View.GONE
            fragment_layout_downloads?.visibility = View.VISIBLE
        }
    }
}
