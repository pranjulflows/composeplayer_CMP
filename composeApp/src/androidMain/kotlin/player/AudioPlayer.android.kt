package player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.macamps.musicplayer.app.App

actual class AudioPlayer actual constructor(private val playerState: PlayerState) {
    private val mediaPlayer = ExoPlayer.Builder(App.getAppInstance().applicationContext).build()

    private val mediaItems = mutableListOf<MediaItem>()
    private var currentItemIndex = -1
    private val listener =
        object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> {
                        playerState.isPlaying = false
                    }

                    Player.STATE_BUFFERING -> {
                        playerState.isBuffering = true
                    }

                    Player.STATE_ENDED -> {
                        if (playerState.isPlaying) {
                            onNext()
                        }
                    }

                    Player.STATE_READY -> {
                        playerState.isBuffering = false
                        playerState.duration = mediaPlayer.duration / 1000
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                playerState.isPlaying = isPlaying
                if (isPlaying) {
                    scheduleUpdate()
                } else {
                    stopUpdate()
                }
            }
        }

    private var updateJob: Job? = null

    private fun stopUpdate() {
        updateJob?.cancel()
    }

    private fun scheduleUpdate() {
        stopUpdate()
        updateJob =
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    playerState.currentTime = mediaPlayer.currentPosition / 1000
                    delay(1000)
                }
            }
    }

    actual fun onPlay() {
        if (currentItemIndex == -1) {
            onPlay(0)
        } else {
            mediaPlayer.play()
        }
    }

    actual fun onPause() {
        mediaPlayer.pause()
    }

    actual fun addSongsUrls(songsUrl: List<String>) {
        mediaItems += songsUrl.map { MediaItem.fromUri(it) }
        mediaPlayer.addListener(listener)
        mediaPlayer.prepare()
    }

    actual fun onNext() {
        playerState.canNext = (currentItemIndex + 1) < mediaItems.size
        if (playerState.canNext) {
            currentItemIndex += 1
            playWithIndex(currentItemIndex)
        }
    }

    actual fun onBack() {
        when {
            playerState.currentTime > 3 -> {
                seekTo(0.0)
            }

            else -> {
                playerState.canPrev = (currentItemIndex - 1) >= 0
                if (playerState.canPrev) {
                    currentItemIndex -= 1
                    playWithIndex(currentItemIndex)
                }
            }
        }
    }

    actual fun onPlay(songIndex: Int) {
        if (songIndex < mediaItems.size) {
            currentItemIndex = songIndex
            playWithIndex(currentItemIndex)
        }
    }

    actual fun seekTo(time: Double) {
        val seekTime = time * 1000
        mediaPlayer.seekTo(seekTime.toLong())
    }

    private fun playWithIndex(index: Int) {
        playerState.currentItemIndex = index
        val playItem = mediaItems[index]
        mediaPlayer.setMediaItem(playItem)
        mediaPlayer.play()
    }

    actual fun onDispose() {
        mediaPlayer.release()
        mediaPlayer.removeListener(listener)
        stopUpdate()
    }

    actual fun onStop() {
        mediaPlayer.stop()
        mediaPlayer.seekTo(0)
    }
}
