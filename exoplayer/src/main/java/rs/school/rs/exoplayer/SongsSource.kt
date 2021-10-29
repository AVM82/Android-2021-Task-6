package rs.school.rs.exoplayer

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_URI
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ARTIST
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_TITLE
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rs.school.rs.exoplayer.repository.SongRepository
import javax.inject.Inject

class SongsSource @Inject constructor(private val songRepository: SongRepository) {

    var songs = emptyList<MediaMetadataCompat>()

    suspend fun fetchSongs() = withContext(Dispatchers.IO) {
        songs = songRepository.fetchSongs().map { song ->
            MediaMetadataCompat.Builder()
            .putString(METADATA_KEY_MEDIA_ID, song.id.toString())
            .putString(METADATA_KEY_ARTIST, song.artist)
            .putString(METADATA_KEY_TITLE, song.title)
            .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
            .putString(METADATA_KEY_DISPLAY_SUBTITLE, song.artist)
            .putString(METADATA_KEY_DISPLAY_DESCRIPTION, song.artist)
            .putString(METADATA_KEY_DISPLAY_ICON_URI, song.bitmapUri)
            .putString(METADATA_KEY_MEDIA_URI, song.trackUri)
            .putString(METADATA_KEY_ALBUM_ART_URI, song.bitmapUri)
            .build()
        }
    }

    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        songs.forEach { song ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(
                        song.getString(METADATA_KEY_MEDIA_URI).toUri()
                    )
                )
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun asMediaItems() = songs.map { song ->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaUri(song.getString(METADATA_KEY_MEDIA_URI).toUri())
            .setTitle(song.description.title)
            .setSubtitle(song.description.subtitle)
            .setMediaId(song.description.mediaId)
            .setIconUri(song.description.iconUri)
            .build()
        MediaBrowserCompat.MediaItem(desc, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
    }.toMutableList()
}
