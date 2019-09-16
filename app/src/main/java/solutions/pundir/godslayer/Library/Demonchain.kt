package solutions.pundir.godslayer.Library

import android.content.Context

class Demonchain(val context : Context?, val name : String) {
    var nodes = mutableListOf<DemonslayerTorrent>()

    fun add_node(magnet_url : String) {
        var check = true
        for (item in nodes.listIterator()) {
            if (item.magnet_url == magnet_url) {
                check = false
                break
            }
        }
        if (check) {
            context?.let { DemonslayerTorrent(it, magnet_url, this@Demonchain) }?.let { nodes.add(it) }
            nodes.last().callback_from_parent(nodes.size - 1)
        }
    }

    fun refresh_torrent_in_adapter(position : Int) {

    }

}