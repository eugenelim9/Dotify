package edu.uw.euglim9.dotify.manager

import edu.uw.euglim9.dotify.model.Song

class SongManager {
    var selectedSong: Song? = null
        private set

    fun onSongSelected(song: Song) {
        selectedSong = song
    }
}