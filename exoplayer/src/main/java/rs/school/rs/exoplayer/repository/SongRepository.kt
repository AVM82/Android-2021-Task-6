package rs.school.rs.exoplayer.repository

import android.content.Context
import rs.school.rs.core.model.Song
import rs.school.rs.core.utils.ParseJSON
import java.nio.charset.StandardCharsets.UTF_8

class SongRepository(private val context: Context) {

    fun fetchSongs(): List<Song> {
        val input = context.assets.open("play_list.json")
        val size: Int = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        return ParseJSON().toSongList(String(buffer, UTF_8))
    }
}
