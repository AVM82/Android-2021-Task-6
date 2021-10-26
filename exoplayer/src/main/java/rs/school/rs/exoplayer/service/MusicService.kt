package rs.school.rs.exoplayer.service

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import rs.school.rs.exoplayer.NotificationManager
import rs.school.rs.exoplayer.SongsSource
import rs.school.rs.exoplayer.callback.PlayerEventListener
import rs.school.rs.exoplayer.callback.PlayerNotificationListener
import rs.school.rs.exoplayer.callback.PlayerPlaybackPreparer
import javax.inject.Inject

@AndroidEntryPoint
class MusicService @Inject constructor(
    val dataSourceFactory: DefaultDataSourceFactory,
    val exoPlayer: SimpleExoPlayer,
    var songsSource: SongsSource
) : MediaBrowserServiceCompat() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private var playingSong: MediaMetadataCompat? = null

    var isForegroundService = false


    override fun onCreate() {
        super.onCreate()
        val intent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, 0)
        }
        val mediaSession = MediaSessionCompat(this, SERVICE_TAG).apply {
            setSessionActivity(intent)
            isActive = true
        }
        sessionToken = mediaSession.sessionToken
        val notificationManager = NotificationManager(
            context = this,
            sessionToken = mediaSession.sessionToken,
            notificationListener = PlayerNotificationListener(this)
        ) {


        }

        val playbackPreparer = PlayerPlaybackPreparer(songsSource) {
            playingSong = it
            preparePlayer(songsSource.songs, it, true)
        }

        val mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlaybackPreparer(playbackPreparer)
        mediaSessionConnector.setPlayer(exoPlayer)
        exoPlayer.addListener(PlayerEventListener(this))
        notificationManager.showNotification(exoPlayer)
    }

    private fun preparePlayer(
        songs: List<MediaMetadataCompat>,
        item: MediaMetadataCompat?,
        isPlaying: Boolean
    ) {
        val songIndex = if (playingSong == null) 0 else songs.indexOf(item)
        exoPlayer.setMediaSource(songsSource.asMediaSource(dataSourceFactory))
        exoPlayer.seekTo(songIndex, 0L)
        exoPlayer.playWhenReady = isPlaying
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val SERVICE_TAG = "PlayService"
    }
}
