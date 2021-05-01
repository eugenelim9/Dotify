package edu.uw.euglim9.dotify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.uw.euglim9.dotify.databinding.ActivitySongListBinding
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.euglim9.dotify.PlayerActivity.Companion.SONG_KEY

private const val SHOW_MINI_PLAYER_KEY = "SHOW_MINI_PLAYER_KEY"

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private var showMiniPlayer: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        title = getString(R.string.title_all_songs)

        if (savedInstanceState != null) {
            val someSong = savedInstanceState.getParcelable<Song>(SHOW_MINI_PLAYER_KEY)
            showMiniPlayer = someSong
            binding.player.visibility = View.VISIBLE
            binding.tvPlayerTitle.text = getString(R.string.player_texts, someSong?.title, someSong?.artist)

            binding.player.setOnClickListener {
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtra(SONG_KEY, someSong)
                startActivity(intent)
            }
        }

        val listOfSongs = SongDataProvider.getAllSongs()
        val songListAdapter = SongListAdapter(listOfSongs)
        binding.rvSongs.adapter = songListAdapter

        binding.btnShuffle.setOnClickListener {
            songListAdapter.changeSongs(listOfSongs.toMutableList().shuffled())
        }

        songListAdapter.onSongClickListener = { someSong: Song ->
            binding.player.visibility = View.VISIBLE
            showMiniPlayer = someSong
            binding.tvPlayerTitle.text = getString(R.string.player_texts, someSong.title, someSong.artist)

            binding.player.setOnClickListener {
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtra(SONG_KEY, someSong)
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (showMiniPlayer != null) {
            outState.putParcelable(SHOW_MINI_PLAYER_KEY, showMiniPlayer)
        }
        super.onSaveInstanceState(outState)
    }
}