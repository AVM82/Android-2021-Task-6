package rs.school.rs.core.utils

import android.util.Log
import org.json.JSONArray
import rs.school.rs.core.model.Song


class ParseJSON {

    fun toSongList(json: String): List<Song> {

        var list = emptyList<Song>()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val song = jsonArray.getJSONObject(i)
            Log.e("json", "$i=$song")
            val s = Song(
                title = song.getString("title"),
                artist = song.getString("artist"),
                bitmapUri = song.getString("bitmapUri"),
                trackUri = song.getString("trackUri"))
            Log.e("song", "$s")
            list = list + s
        }
        return list
    }

}
