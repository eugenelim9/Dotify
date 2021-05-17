package edu.uw.euglim9.dotify.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
//import com.ericchee.songdataprovider.Song
import edu.uw.euglim9.dotify.R
import edu.uw.euglim9.dotify.databinding.ActivitySettingsBinding
import edu.uw.euglim9.dotify.model.Song

private const val SONG_KEY = "song"
private const val PLAYS_KEY = "numberPlays"

fun launchSettingsActivity(context: Context, song: Song, numberPlays: Int) = with(context) {
    startActivity(Intent(this, SettingsActivity::class.java).apply {
        putExtra(SONG_KEY, song)
        putExtra(PLAYS_KEY, numberPlays)
    })
}

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply { setContentView(root) }
        with (binding) {
            navController.setGraph(R.navigation.nav_graph, Bundle().apply {
                putParcelable("song", intent.extras?.getParcelable<Song>(SONG_KEY))
                putInt("numberPlays", intent.extras?.getInt(PLAYS_KEY)!!)
            })
            setupActionBarWithNavController(navController)
        }
    }
    override fun onSupportNavigateUp() = navController.navigateUp()
}