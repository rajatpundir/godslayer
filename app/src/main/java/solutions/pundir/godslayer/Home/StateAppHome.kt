package solutions.pundir.godslayer.Home

class StateAppHome {
    private var seleclted_module_id = 0L
    private var seleclted_language_id = 0L
    private var seleclted_platform_id = 0L
    private var seleclted_publisher_id = 0L
    private var seleclted_playlist_id = 0L
    private var seleclted_episode_id = 0L
    private var seleclted_source_id = 0L
    fun set_module_id(mid : Long) {
        seleclted_module_id = mid
        print_state()
    }
    fun set_language_id(mid : Long, rid : Long) {
        seleclted_module_id = mid
        seleclted_language_id = rid
        print_state()
    }
    fun set_platform_id(mid : Long, rid : Long) {
        seleclted_module_id = mid
        seleclted_platform_id = rid
        print_state()
    }
    fun set_publisher_id(mid : Long, rid : Long) {
        seleclted_module_id = mid
        seleclted_publisher_id = rid
        print_state()
    }
    fun set_playlist_id(mid : Long, rid : Long) {
        seleclted_module_id = mid
        seleclted_playlist_id = rid
        print_state()
    }
    fun set_episode_id(mid : Long, rid : Long) {
        seleclted_module_id = mid
        seleclted_episode_id = rid
        print_state()
    }
    fun set_source_id(mid : Long, rid : Long) {
        seleclted_module_id = mid
        seleclted_source_id = rid
        print_state()
    }
    fun clear_all() {
        seleclted_module_id = 0L
        seleclted_language_id = 0L
        seleclted_platform_id = 0L
        seleclted_publisher_id = 0L
        seleclted_playlist_id = 0L
        seleclted_episode_id = 0L
        seleclted_source_id = 0L
    }
    fun print_state() {
        println("MODULE : " + seleclted_module_id.toString())
        println("LANG : " + seleclted_language_id.toString())
        println("PLAT : " + seleclted_platform_id.toString())
        println("PUB : " + seleclted_publisher_id.toString())
        println("PLAY : " + seleclted_playlist_id.toString())
        println("EP : " + seleclted_episode_id.toString())
        println("SRC : " + seleclted_source_id.toString())
    }
}