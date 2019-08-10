package solutions.pundir.godslayer.Subscriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_subscriptions.*
import org.jetbrains.anko.doAsync
import solutions.pundir.godslayer.R

class FragmentSubscriptions  : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_subscriptions, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "https://horriblesubs.info/wp-content/uploads/2019/04/kimetsunoyaiba.jpg"
        Picasso.get().load(url).into(imageViewSubscriptions)
    }

}