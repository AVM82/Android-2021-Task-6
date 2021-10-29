package rs.school.rs.exoplayer.service

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import rs.school.rs.exoplayer.NotificationManager
import rs.school.rs.exoplayer.SongsSource
import rs.school.rs.exoplayer.callback.PlayerEventListener
import rs.school.rs.exoplayer.callback.PlayerNotificationListener
import rs.school.rs.exoplayer.callback.PlayerPlaybackPreparer
import javax.inject.Inject

@AndroidEntryPoint
class MusicService : MediaBrowserServiceCompat() {

    @Inject
    lateinit var dataSourceFactory: DefaultDataSourceFactory

    @Inject
    lateinit var exoPlayer: SimpleExoPlayer

    @Inject
    lateinit var songsSource: SongsSource

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private var playingSong: MediaMetadataCompat? = null
    private var isPlayerInit = false
    private var playerEventListener: PlayerEventListener? = null
    private var mediaSession: MediaSessionCompat? = null
    private var mediaSessionConnector: MediaSessionConnector? = null
    private var notificationManager: NotificationManager? = null

    var isForegroundService = false


    override fun onCreate() {
        super.onCreate()
        //I know it’s bad to do this, but I expect it to be fast, and I don’t have to do various checks.
        runBlocking { songsSource.fetchSongs() }
        val intent = packageManager?.getLaunchIntentForPackage(packageName)
            ?.let { PendingIntent.getActivity(this, 0, it, 0) }
        mediaSession = MediaSessionCompat(this, SERVICE_TAG).apply {
            setSessionActivity(intent)
            isActive = true
        }
        sessionToken = mediaSession?.sessionToken
        val playbackPreparer = PlayerPlaybackPreparer(songsSource) {
            playingSong = it
            preparePlayer(songsSource.songs, it, true)
        }
        mediaSession?.let {
            notificationManager = NotificationManager(
                sessionToken = it.sessionToken,
                notificationListener = PlayerNotificationListener(this),
                context = this
            ) { songDuration = exoPlayer.duration }
            mediaSessionConnector = MediaSessionConnector(it)
            mediaSessionConnector?.setPlaybackPreparer(playbackPreparer)
            mediaSessionConnector?.setPlayer(exoPlayer)
            mediaSessionConnector?.setQueueNavigator(object : TimelineQueueNavigator(it) {
                override fun getMediaDescription(
                    player: Player,
                    windowIndex: Int
                ): MediaDescriptionCompat {
                    return songsSource.songs[windowIndex].description
                }
            })
        }
        playerEventListener = PlayerEventListener(this)
        playerEventListener?.let { exoPlayer.addListener(it) }
        notificationManager?.showNotification(exoPlayer)
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

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        playerEventListener?.let { exoPlayer.removeListener(it) }
        exoPlayer.release()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        if (parentId == ROOT_ID) {
            result.sendResult(songsSource.asMediaItems())
            if (isPlayerInit && songsSource.songs.isNotEmpty()) {
                preparePlayer(songsSource.songs, songsSource.songs[0], false)
                isPlayerInit = true
            }
        }
    }

    companion object {
        private const val SERVICE_TAG = "PlayService"
        private var songDuration = 0L
        const val ROOT_ID = "MediaRoot"
    }
}
