package edu.uw.euglim9.dotify.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.euglim9.dotify.NavGraphDirections
import edu.uw.euglim9.dotify.fragment.SettingsFragmentArgs
import edu.uw.euglim9.dotify.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentSettingsBinding.inflate(inflater)
        val receivedSong = safeArgs.song
        val numberPlays = safeArgs.numberPlays

        binding.btnStatistics.setOnClickListener {
            navController.navigate(
                NavGraphDirections.actionGlobalStatisticsFragment(
                    receivedSong,
                    numberPlays
                )
            )
        }

        binding.btnProfile.setOnClickListener {
            navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
        }

        binding.btnAbout.setOnClickListener {
            navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
        }

        return binding.root
    }
}