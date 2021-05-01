package edu.uw.euglim9.dotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.uw.euglim9.dotify.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater)
        binding.tvVersion.text = getString(R.string.tvVersionNumber, BuildConfig.VERSION_NAME)
        return binding.root
    }
}