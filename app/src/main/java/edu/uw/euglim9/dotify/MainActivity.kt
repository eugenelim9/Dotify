package edu.uw.euglim9.dotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.uw.euglim9.dotify.databinding.ActivityMainBinding
import kotlin.random.Random
import android.widget.Toast
import com.ericchee.songdataprovider.Song

private const val SONG_KEY = "SONG_KEY"

fun navigateToMainActivity(context: Context, song: Song) = with(context) {
    val intent = Intent(this, MainActivity::class.java).apply {
        val bundle = Bundle().apply {
            putParcelable(SONG_KEY, song)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class MainActivity : AppCompatActivity() {
    private var randomNumber = Random.nextInt(1000, 10000)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            tvPlays.text = "$randomNumber plays"
            val song: Song? = intent.getParcelableExtra<Song>(SONG_KEY)

            if (song != null) {
                tvTitle.text = song.title
                tvArtist.text = song.artist
                imgCover.setImageResource(song.largeImageID)
            }
        }
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
}