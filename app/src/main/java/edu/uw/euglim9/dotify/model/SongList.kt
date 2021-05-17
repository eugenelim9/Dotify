package edu.uw.euglim9.dotify.model

data class SongList(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)