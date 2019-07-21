package solutions.pundir.godslayer.todos.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solutions.pundir.godslayer.R
import solutions.pundir.godslayer.todos.Entity.EntityModules


class AdapterModules internal constructor(context: Context) : RecyclerView.Adapter<AdapterModules.ModulesViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var modules = emptyList<EntityModules>() // Cached copy of words

    inner class ModulesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ModulesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) {
        val current = modules[position]
        holder.wordItemView.text = current.name
    }

    internal fun setModules(modules: List<EntityModules>) {
        this.modules = modules
        notifyDataSetChanged()
    }

    override fun getItemCount() = modules.size
}