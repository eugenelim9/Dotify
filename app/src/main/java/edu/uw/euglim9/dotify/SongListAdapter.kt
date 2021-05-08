package edu.uw.euglim9.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import edu.uw.euglim9.dotify.databinding.ItemSongBinding

class SongListAdapter(private var listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener: (position: Int, song: Song) -> Unit = { position, song -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        // recyclerview item was not extending to full width of the screen and found adaptation adding ", parent, false" here: https://www.programmersought.com/article/54441042198/
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song: Song = listOfSongs[position]
        with(holder.binding) {
            tvSongTitle.text = song.title
            tvSongArtist.text = song.artist
            ivSongImage.setImageResource(song.smallImageID)

            root.setOnClickListener {
                onSongClickListener(position, song)
            }
        }
    }

    override fun getItemCount(): Int = listOfSongs.size

    fun updateSongs(newListOfSongs: List<Song>) {
        val callback = SongDiffCallback(newListOfSongs, listOfSongs)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        this.listOfSongs = newListOfSongs
    }

    class SongViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)
}