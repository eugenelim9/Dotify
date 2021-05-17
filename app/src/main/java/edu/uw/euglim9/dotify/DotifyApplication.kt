package edu.uw.euglim9.dotify

import android.app.Application
import edu.uw.euglim9.dotify.manager.SongManager
import edu.uw.euglim9.dotify.model.Song
import edu.uw.euglim9.dotify.repository.DataRepository

class DotifyApplication: Application() {
    lateinit var songManager: SongManager
    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()

        this.songManager = SongManager()
        dataRepository = DataRepository()
    }
}