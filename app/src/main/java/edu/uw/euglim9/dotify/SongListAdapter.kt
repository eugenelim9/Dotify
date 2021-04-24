package edu.uw.euglim9.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(initialListOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    private var listOfSongs: List<Song> = initialListOfSongs.toList()  //to create a duplicate of the list passed in
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int): Unit {
        val song = listOfSongs[position]
        holder.bind(song)
    }

    override fun getItemCount(): Int = listOfSongs.size

    fun changeSongs(newSongs: List<Song>) {
        val callback = SongDiffCallBack(listOfSongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        listOfSongs = newSongs
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongTitle = itemView.findViewById<TextView>(R.id.tvSongTitle)
        private val tvSongArtist = itemView.findViewById<TextView>(R.id.tvSongArtist)
        private val ivSongPicture = itemView.findViewById<ImageView>(R.id.ivSongPicture)
        fun bind(song: Song) {
            tvSongTitle.text = song.title
            tvSongArtist.text = song.artist
            ivSongPicture.setImageResource(song.smallImageID)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}