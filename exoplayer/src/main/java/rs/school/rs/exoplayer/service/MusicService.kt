package rs.school.rs.exoplayer.service

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
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
import rs.school.rs.exoplayer.callback.PlayerNotificationListener
import javax.inject.Inject

@AndroidEntryPoint
class MusicService @Inject constructor(
    val dataSourceFactory: DefaultDataSourceFactory,
    val exoPlayer: SimpleExoPlayer,
) : MediaBrowserServiceCompat() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

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
        NotificationManager(
            context = this,
            sessionToken = mediaSession.sessionToken,
            notificationListener = PlayerNotificationListener(this)
        ) {

        }

        MediaSessionConnector(mediaSession).setPlayer(exoPlayer)
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
