package edu.uw.euglim9.dotify.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.uw.euglim9.dotify.databinding.ActivitySongListBinding
//import com.ericchee.songdataprovider.Song
//import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.euglim9.dotify.DotifyApplication
import edu.uw.euglim9.dotify.activity.PlayerActivity.Companion.SONG_KEY
import edu.uw.euglim9.dotify.model.Song
import edu.uw.euglim9.dotify.R
import edu.uw.euglim9.dotify.adapter.SongListAdapter
import edu.uw.euglim9.dotify.manager.SongManager
import edu.uw.euglim9.dotify.model.SongList
import kotlinx.coroutines.launch

private const val SHOW_MINI_PLAYER_KEY = "SHOW_MINI_PLAYER_KEY"

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private var showMiniPlayer: Song? = null
    private lateinit var songListAdapter: SongListAdapter

    private val dotifyApp: DotifyApplication by lazy { application as DotifyApplication }
    private val dataRepository by lazy { dotifyApp.dataRepository }
    private lateinit var songManager: SongManager

    private lateinit var listOfSongs: List<Song>
    private var refreshMix: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        title = getString(R.string.title_all_songs)

        this.songManager = (this.applicationContext as DotifyApplication).songManager

        if (savedInstanceState != null) {
            val someSong = savedInstanceState.getParcelable<Song>(SHOW_MINI_PLAYER_KEY)
            showMiniPlayer = someSong
            binding.player.visibility = View.VISIBLE
            binding.tvPlayerTitle.text = getString(R.string.player_texts, someSong?.title, someSong?.artist)
        }

        listOfSongs = listOf()
        loadSongList()
        songListAdapter = SongListAdapter(listOfSongs)
        binding.rvSongs.adapter = songListAdapter

        binding.btnShuffle.setOnClickListener {
            songListAdapter.changeSongs(listOfSongs.toMutableList().shuffled())
        }

        songListAdapter.onSongClickListener = { someSong: Song ->
            binding.player.visibility = View.VISIBLE
            showMiniPlayer = someSong
            binding.tvPlayerTitle.text = getString(R.string.player_texts, someSong.title, someSong.artist)

            songManager.onSongSelected(someSong)
        }

        binding.player.setOnClickListener {
            launchPlayerActivity(this@SongListActivity)
        }

        binding.pullDownContainer.setOnRefreshListener {
            refreshMix = true
            loadSongList()
            binding.pullDownContainer.isRefreshing = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (showMiniPlayer != null) {
            outState.putParcelable(SHOW_MINI_PLAYER_KEY, showMiniPlayer)
        }
        super.onSaveInstanceState(outState)
    }

    private fun loadSongList() {
        lifecycleScope.launch {
            runCatching {
                val songList: SongList = dataRepository.getSongList()
                listOfSongs = songList.songs
                if (refreshMix) {
                    listOfSongs = listOfSongs.toMutableList().shuffled()
                    // NOTICE
                    // on swipe down refresh, I want to see visible confirmation that it's working so I shuffled the songs
                    // but this would put the user in a weird spot at the list like in the middle so I wanted to scroll to top
                    // got code from => https://stackoverflow.com/questions/32159724/scroll-to-top-in-recyclerview-with-linearlayoutmanager
                    binding.rvSongs.smoothScrollToPosition(0)
                    refreshMix = false
                }
                songListAdapter.changeSongs(listOfSongs)
            }.onFailure {
                Toast.makeText(this@SongListActivity, "Error occurred when fetching your songs", Toast.LENGTH_SHORT).show()
            }
        }
    }
}