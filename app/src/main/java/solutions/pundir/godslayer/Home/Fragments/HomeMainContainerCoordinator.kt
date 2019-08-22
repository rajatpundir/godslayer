package solutions.pundir.godslayer.Home.Fragments

interface HomeMainContainerCoordinator {
    fun download_source(mid : Long, rid : Long)
    fun generate_click(button_name : String)
}