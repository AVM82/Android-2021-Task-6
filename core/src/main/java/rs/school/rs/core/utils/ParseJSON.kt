package rs.school.rs.core.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import rs.school.rs.core.model.Song
import java.util.Collections

const val SONG_FIELD_TITLE = "title"
const val SONG_FIELD_ARTIST = "artist"
const val SONG_FIELD_IMG = "bitmapUri"
const val SONG_FIELD_TRACK_URI = "trackUri"


class ParseJSON {
    @Throws(JSONException::class)
    fun toSongList(json: String): List<Song> {
        val mutableSongList: MutableList<Song> = arrayListOf()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            mutableSongList.add(toSong(jsonArray.getJSONObject(i)))
        }
        return Collections.unmodifiableList(mutableSongList)
    }

    private fun toSong(jsonObj: JSONObject) = Song(
        title = jsonObj.getString(SONG_FIELD_TITLE),
        artist = jsonObj.getString(SONG_FIELD_ARTIST),
        bitmapUri = jsonObj.getString(SONG_FIELD_IMG),
        trackUri = jsonObj.getString(SONG_FIELD_TRACK_URI)
    )
}