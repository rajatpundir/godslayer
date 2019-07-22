package solutions.pundir.godslayer.Home

class StateFragmentsHome {
    var visibility_home_modules = true
    var visibility_home_languages = false
    var visibility_home_platforms = false
    var visibility_home_publishers = false
    var visibility_home_playlists = false
    var visibility_home_episodes = false
    var visibility_home_sources = false
    fun hide_all() {
        visibility_home_modules = false
        visibility_home_languages = false
        visibility_home_platforms = false
        visibility_home_publishers = false
        visibility_home_playlists = false
        visibility_home_episodes = false
        visibility_home_sources = false
    }
    fun show_home_modules() {
        hide_all()
        visibility_home_modules = true
    }
    fun show_home_languages() {
        hide_all()
        visibility_home_languages = true
    }
    fun show_home_platforms() {
        hide_all()
        visibility_home_platforms = true
    }
    fun show_home_publishers() {
        hide_all()
        visibility_home_publishers = true
    }
    fun show_home_playlists() {
        hide_all()
        visibility_home_playlists = true
    }
    fun show_home_episodes() {
        hide_all()
        visibility_home_episodes = true
    }
    fun show_home_sources() {
        hide_all()
        visibility_home_sources = true
    }
}