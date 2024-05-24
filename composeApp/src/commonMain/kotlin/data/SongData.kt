package data

import kotlinx.serialization.Serializable

@Serializable
data class SongData(
    val data: List<DataItem>? = null,
    val success: Boolean? = null,
)

@Serializable
data class AllItem(
    val image: List<ImageItem?>? = null,
    val role: String? = null,
    val name: String? = null,
    val id: String? = null,
    val type: String? = null,
    val url: String? = null,
)

@Serializable
data class DownloadUrlItem(
    val url: String,
    val quality: String? = null,
)

@Serializable
data class ImageItem(
    val url: String? = null,
    val quality: String? = null,
)

@Serializable
data class Artists(
    val all: List<AllItem?>? = null,
    val featured: List<String?>? = null,
    val primary: List<PrimaryItem?>? = null,
)

@Serializable
data class DataItem(
    val lyricsId: String? = null,
    val image: List<ImageItem?>? = null,
    val copyright: String? = null,
    val year: String? = null,
    val releaseDate: String? = null,
    val album: Album? = null,
    val downloadUrl: List<DownloadUrlItem?>? = null,
    val language: String? = null,
    val label: String? = null,
    val type: String? = null,
    val explicitContent: Boolean? = null,
    val url: String? = null,
    val duration: Int? = null,
    val playCount: Int? = null,
    val hasLyrics: Boolean? = null,
    val artists: Artists? = null,
    val name: String? = null,
    val id: String? = null,
)

@Serializable
data class Album(
    val name: String? = null,
    val id: String? = null,
    val url: String? = null,
)

@Serializable
data class PrimaryItem(
    val image: List<ImageItem?>? = null,
    val role: String? = null,
    val name: String? = null,
    val id: String? = null,
    val type: String? = null,
    val url: String? = null,
)
