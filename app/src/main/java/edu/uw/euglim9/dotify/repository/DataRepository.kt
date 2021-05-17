package edu.uw.euglim9.dotify.repository

import edu.uw.euglim9.dotify.model.Profile
import edu.uw.euglim9.dotify.model.SongList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class DataRepository {
    private val dotifyService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DotifyService::class.java)

    suspend fun getProfile(): Profile = dotifyService.getProfile()

    suspend fun getSongList() = dotifyService.getSongList()
}

interface DotifyService {
    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getProfile(): Profile

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    suspend fun getSongList(): SongList
}