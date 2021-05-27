package edu.uw.euglim9.dotify.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.euglim9.dotify.DotifyApplication
import edu.uw.euglim9.dotify.NavGraphDirections
import edu.uw.euglim9.dotify.databinding.FragmentSettingsBinding
import edu.uw.euglim9.dotify.manager.DotifyNotificationManager
import edu.uw.euglim9.dotify.manager.DotifySyncManager


const val DOTIFY_APP_PREFS_KEY = "Dotify App Prefs"
const val NOTIFICATIONS_ENABLED_PREF_KEY = "notifications_enabled"

class SettingsFragment : Fragment() {
    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }
    private val dotApp by lazy { context?.applicationContext as DotifyApplication }
    private val dotifyNotificationManager: DotifyNotificationManager by lazy { dotApp.notificationManager }
    private val dotifySyncManager: DotifySyncManager by lazy { dotApp.dotifySyncManager }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsBinding.inflate(inflater)
        val receivedSong = safeArgs.song
        val numberPlays = safeArgs.numberPlays

        with(binding) {
            btnStatistics.setOnClickListener {
                navController.navigate(
                    NavGraphDirections.actionGlobalStatisticsFragment(
                        receivedSong,
                        numberPlays
                    )
                )
            }

            btnProfile.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
            }

            btnAbout.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
            }

            // Lecture goes over SharedPreferences in Activity but not for fragments
            // https://stackoverflow.com/questions/11741270/android-sharedpreferences-in-fragment/24550399
            // translated Java to Kotlin to make SharedPreferences work
            val preferences: SharedPreferences? = getActivity()?.getSharedPreferences(DOTIFY_APP_PREFS_KEY, Context.MODE_PRIVATE)

//            dotifyNotificationManager.isNotificationsEnabled = switchNotificationsEnabled.isChecked
            if (preferences != null) {
                switchNotificationsEnabled.isChecked = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)
                dotifyNotificationManager.isNotificationsEnabled = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)
            }

            switchNotificationsEnabled.setOnCheckedChangeListener { _, isChecked ->
                dotifyNotificationManager.isNotificationsEnabled = isChecked

                preferences?.edit {
                    putBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, isChecked)
                }

                if (isChecked) {
                    dotifySyncManager.refreshRandomSongPeriodically()
                } else {
                    dotifySyncManager.stopPeriodicallyRefreshing()
                }
            }
        }

        return binding.root
    }
}