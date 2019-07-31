package solutions.pundir.godslayer.Main

import solutions.pundir.godslayer.Downloads.FragmentDownloads
import solutions.pundir.godslayer.Home.FragmentHome
import solutions.pundir.godslayer.Inbox.FragmentInbox
import solutions.pundir.godslayer.Library.FragmentLibrary
import solutions.pundir.godslayer.Subscriptions.FragmentSubscriptions

class StateMainContainer {
    lateinit var inbox_fragment : FragmentInbox
    lateinit var home_fragment : FragmentHome
    lateinit var subscriptions_fragment : FragmentSubscriptions
    lateinit var downloads_fragment : FragmentDownloads
    lateinit var library_fragment : FragmentLibrary
}
