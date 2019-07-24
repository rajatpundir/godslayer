package solutions.pundir.godslayer.Home

import solutions.pundir.godslayer.Home.Fragments.FargmentHomeModules
import solutions.pundir.godslayer.Home.Fragments.FargmentHomeLanguages
import solutions.pundir.godslayer.Home.Fragments.FargmentHomePlatforms
import solutions.pundir.godslayer.Home.Fragments.FargmentHomePublishers
import solutions.pundir.godslayer.Home.Fragments.FargmentHomePlaylists
import solutions.pundir.godslayer.Home.Fragments.FargmentHomeEpisodes
import solutions.pundir.godslayer.Home.Fragments.FargmentHomeSources

class StateHomeMainContainer {
    lateinit var modules_fragment : FargmentHomeModules
    lateinit var languages_fragment : FargmentHomeLanguages
    lateinit var platforms_fragment : FargmentHomePlatforms
    lateinit var publishers_fragment : FargmentHomePublishers
    lateinit var playlists_fragment : FargmentHomePlaylists
    lateinit var episodes_fragment : FargmentHomeEpisodes
    lateinit var sources_fragment : FargmentHomeSources
}