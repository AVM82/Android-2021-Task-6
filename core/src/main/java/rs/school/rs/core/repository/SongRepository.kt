package rs.school.rs.core.repository

import rs.school.rs.core.model.Song

interface SongRepository {

    fun fetchSongs(): List<Song>

}
