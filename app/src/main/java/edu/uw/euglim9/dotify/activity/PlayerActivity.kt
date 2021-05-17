package edu.uw.euglim9.dotify.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import coil.load
import edu.uw.euglim9.dotify.DotifyApplication
//import com.ericchee.songdataprovider.Song
import edu.uw.euglim9.dotify.R
import kotlin.random.Random
import edu.uw.euglim9.dotify.databinding.ActivityPlayerBinding
import edu.uw.euglim9.dotify.manager.SongManager
import edu.uw.euglim9.dotify.model.Song

private const val COUNT_VALUE_KEY = "COUNT_VALUE_KEY"

fun launchPlayerActivity(context: Context) = with(context) {
    startActivity(Intent(this, PlayerActivity::class.java))
}

class PlayerActivity : AppCompatActivity() {
    private var randomNumber = Random.nextInt(1000, 10000)
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var song: Song
    private lateinit var songManager: SongManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }

        this.songManager = (this.applicationContext as DotifyApplication).songManager

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState != null) {
            randomNumber = savedInstanceState.getInt(COUNT_VALUE_KEY, 0)
        }

        binding.tvPlays.text = "$randomNumber plays"

        song = songManager.selectedSong!!

        if (song != null) {
            binding.tvTitle.text = song.title
            binding.tvArtist.text = song.artist
            binding.imgCover.load(song.largeImageURL)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(COUNT_VALUE_KEY, randomNumber)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.player_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_settings -> launchSettingsActivity(this@PlayerActivity, song, randomNumber)
        }
        return super.onOptionsItemSelected(item)
    }

    fun incrementPlays(view: View) {
        randomNumber += 1;
        binding.tvPlays.text = "$randomNumber plays"
    }

    fun previousClicked(view: View) {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    fun nextClicked(view: View) {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        // Keys for intents
        const val SONG_KEY = "SONG_KEY"
    }
}