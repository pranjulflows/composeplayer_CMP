package player

expect class AudioPlayer(playerState: PlayerState) {
    fun onPlay()

    fun onPause()

    fun onNext()

    fun onBack()

    fun onPlay(songIndex: Int)

    fun seekTo(time: Double)

    fun addSongsUrls(songsUrl: List<String>)

    fun onClear()

    fun onStop()
}
