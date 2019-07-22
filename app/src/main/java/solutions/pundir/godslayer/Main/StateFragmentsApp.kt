package solutions.pundir.godslayer.Main

class StateFragmentsApp {
    var visibility_home = true
    var visibility_subscriptions = false
    var visibility_inbox = false
    var visibility_library = false
    var visibility_downloads = false
    fun hide_all() {
        visibility_home = false
        visibility_subscriptions = false
        visibility_inbox = false
        visibility_library = false
        visibility_downloads = false
    }
    fun show_home() {
        hide_all()
        visibility_home = true
    }
    fun show_subscriptions() {
        hide_all()
        visibility_subscriptions = true
    }
    fun show_inbox() {
        hide_all()
        visibility_inbox = true
    }
    fun show_library() {
        hide_all()
        visibility_library = true
    }
    fun show_downloads() {
        hide_all()
        visibility_downloads = true
    }
}