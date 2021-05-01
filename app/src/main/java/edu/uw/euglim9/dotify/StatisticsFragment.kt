package edu.uw.euglim9.dotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import edu.uw.euglim9.dotify.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {
    private val safeArgs: StatisticsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater)
        val song = safeArgs.song
        val numberPlays = safeArgs.numberPlays

        binding.imgCover.setImageResource(song.largeImageID)
        binding.tvPlayCount.text = getString(R.string.tvPlayCount, numberPlays)

        return binding.root
    }
}