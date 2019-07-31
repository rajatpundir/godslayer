package solutions.pundir.godslayer.Main.Fragments

interface AppCoordinator {
    fun callback_from_child_fragment()
    fun download_source(mid : Long, rid : Long)
}