package rs.school.rs.android2021task6.ui.main

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rs.school.rs.core.model.Song
import rs.school.rs.core.utils.Resource
import rs.school.rs.exoplayer.isPlayEnabled
import rs.school.rs.exoplayer.isPlaying
import rs.school.rs.exoplayer.isPrepared
import rs.school.rs.exoplayer.service.MusicService
import rs.school.rs.exoplayer.service.ServiceConnection
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val serviceConnection: ServiceConnection) :
    ViewModel() {

    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    val isConnected = serviceConnection.isConnected
    val networkError = serviceConnection.networkError
    val curPlayingSong = serviceConnection.playingSong
    val playbackState = serviceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        serviceConnection.subscribe(
            MusicService.ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val items = children.map {
                        Song(
                            id = it.mediaId?.toInt() ?: 1,
                            title = it.description.title.toString(),
                            artist = it.description.subtitle.toString(),
                            bitmapUri = it.description.iconUri.toString(),
                            trackUri = it.description.mediaUri.toString()
                        )
                    }
                    _mediaItems.postValue(Resource.success(items))
                }
            })
    }

    fun skipSong() {
        serviceConnection.transportControls?.skipToNext()
    }

    fun nextSong() {
        serviceConnection.transportControls?.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        serviceConnection.transportControls?.seekTo(pos)
    }

    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.let { isPrepared(it) } ?: false
        if (isPrepared && mediaItem.id.toString() ==
            curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
        ) {
            playbackState.value?.let { playbackState ->
                when {
                    isPlaying(playbackState) -> if (toggle) serviceConnection.transportControls?.pause() else Unit
                    isPlayEnabled(playbackState) -> serviceConnection.transportControls?.play()
                    else -> Unit
                }
            }
        } else {
            serviceConnection.transportControls?.playFromMediaId(mediaItem.id.toString(), null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unsubscribe(
            MusicService.ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}
