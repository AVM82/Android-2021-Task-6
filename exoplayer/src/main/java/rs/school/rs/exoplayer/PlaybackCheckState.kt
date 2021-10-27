package rs.school.rs.exoplayer

import android.support.v4.media.session.PlaybackStateCompat

fun isPrepared(playback: PlaybackStateCompat): Boolean =
    playback.state == PlaybackStateCompat.STATE_BUFFERING ||
            playback.state == PlaybackStateCompat.STATE_PLAYING ||
            playback.state == PlaybackStateCompat.STATE_PAUSED

fun isPlaying(playback: PlaybackStateCompat): Boolean =
    playback.state == PlaybackStateCompat.STATE_BUFFERING ||
            playback.state == PlaybackStateCompat.STATE_PLAYING

fun isPlayEnabled(playback: PlaybackStateCompat): Boolean =
    playback.actions and PlaybackStateCompat.ACTION_PLAY != 0L ||
            (playback.actions and PlaybackStateCompat.ACTION_PLAY_PAUSE != 0L &&
                    playback.state == PlaybackStateCompat.STATE_PAUSED)
