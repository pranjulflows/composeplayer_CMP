package player

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerTimeControlStatusPlaying
import platform.AVFoundation.currentItem
import platform.AVFoundation.duration
import platform.AVFoundation.isPlaybackLikelyToKeepUp
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.seekToTime
import platform.AVFoundation.timeControlStatus
import platform.CoreMedia.CMTime
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.darwin.Float64
import platform.darwin.NSEC_PER_SEC
import kotlin.time.DurationUnit
import kotlin.time.toDuration

actual class AudioPlayer actual constructor(playerState: PlayerState) {
    private val playerItems = mutableListOf<AVPlayerItem>()
    private val avPlayer = AVPlayer()

    init {
        setUpAudioSession()
        playerState.isPlaying = avPlayer.timeControlStatus == AVPlayerTimeControlStatusPlaying
    }

    private var currentItemIndex = -1
    private lateinit var timeObserver: Any

    @OptIn(ExperimentalForeignApi::class)
    private val observer: (CValue<CMTime>) -> Unit = { time: CValue<CMTime> ->
        playerState.isBuffering = avPlayer.currentItem?.isPlaybackLikelyToKeepUp() != true
        playerState.isPlaying = avPlayer.timeControlStatus == AVPlayerTimeControlStatusPlaying
        val rawTime: Float64 = CMTimeGetSeconds(time)
        val parsedTime = rawTime.toDuration(DurationUnit.SECONDS).inWholeSeconds
        playerState.currentTime = parsedTime
        if (avPlayer.currentItem != null) {
            val cmTime = CMTimeGetSeconds(avPlayer.currentItem!!.duration)
            playerState.duration =
                if (cmTime.isNaN()) 0 else cmTime.toDuration(DurationUnit.SECONDS).inWholeSeconds
        }
    }

    private fun setUpAudioSession() {
        if (currentItemIndex == -1){
            onPlay(0)
        }else{
            avPlayer.play()
            playerState.isPlaying = true
        }
    }

    actual fun onPlay() {

        avPlayer.play()
    }

    actual fun onPlay(songIndex: Int) {
        playerState.isBuffering = true
        avPlayer.play()
    }

    actual fun onPause() {
    }

    actual fun onNext() {
    }

    actual fun onBack() {
    }

    actual fun seekTo(time: Double) {
    }

    actual fun addSongsUrls(songsUrl: List<String>) {
    }

    actual fun onClear() {
        stop()
    }

    actual fun onStop() {
        stop()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun stop() {
        if (::timeObserver.isInitialized) avPlayer.removeTimeObserver(timeObserver)
        avPlayer.pause()
        avPlayer.currentItem?.seekToTime(CMTimeMakeWithSeconds(0.0, NSEC_PER_SEC.toInt()))

    }
}