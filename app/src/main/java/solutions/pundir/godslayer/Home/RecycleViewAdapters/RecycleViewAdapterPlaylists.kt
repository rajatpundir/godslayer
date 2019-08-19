package solutions.pundir.godslayer.Home.RecycleViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import solutions.pundir.godslayer.Home.Fragments.DatabaseRecord
import solutions.pundir.godslayer.Home.StateAppHome
import solutions.pundir.godslayer.R
import solutions.pundir.godslayer.Home.Fragments.FargmentHomePlaylists

class RecycleViewAdapterPlaylists internal constructor(context: Context?, val items: MutableList<DatabaseRecord>, val appStateHome : StateAppHome, val parent_fragment : FargmentHomePlaylists) : RecyclerView.Adapter<RecycleViewAdapterPlaylists.HomeItemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_home, parent, false)
        return HomeItemViewHolder(itemView).listen { pos, _ ->
            val item = items.get(pos)
            appStateHome.set_playlist_id(item.module_id, item.rid)
            parent_fragment.update_episodes_via_parent(item.module_id, item.rid)
        }
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.recyclerViewHomeItem.text = items[position].name
        val url = items[position].image_url
        if (url != "") {
            Picasso.get().load(url).into(holder.recyclerViewHomeItemImage)
            holder.recyclerViewHomeItemImage.visibility = View.VISIBLE
        }
    }

    inner class HomeItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val recyclerViewHomeItem : TextView = v.findViewById(R.id.recycler_view_home_item)
        val recyclerViewHomeItemImage: ImageView = v.findViewById(R.id.recycler_view_home_item_image)
    }

}