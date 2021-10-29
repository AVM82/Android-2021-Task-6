package rs.school.rs.exoplayer.callback

import android.widget.Toast
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import rs.school.rs.exoplayer.service.MusicService
//Listener of all changes in the Player.
class PlayerEventListener(private val musicService: MusicService) : Player.Listener {

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_READY) {
            musicService.stopForeground(false)
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Toast.makeText(musicService, "Unknown error", Toast.LENGTH_SHORT).show()
    }
}
