package data

data class SongData(
	val data: List<DataItem?>? = null,
	val success: Boolean? = null
)

data class AllItem(
	val image: List<Any?>? = null,
	val role: String? = null,
	val name: String? = null,
	val id: String? = null,
	val type: String? = null,
	val url: String? = null
)

data class DownloadUrlItem(
	val url: String? = null,
	val quality: String? = null
)

data class ImageItem(
	val url: String? = null,
	val quality: String? = null
)

data class Artists(
	val all: List<AllItem?>? = null,
	val featured: List<Any?>? = null,
	val primary: List<PrimaryItem?>? = null
)

data class DataItem(
	val lyricsId: Any? = null,
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
	val id: String? = null
)

data class Album(
	val name: String? = null,
	val id: String? = null,
	val url: String? = null
)

data class PrimaryItem(
	val image: List<ImageItem?>? = null,
	val role: String? = null,
	val name: String? = null,
	val id: String? = null,
	val type: String? = null,
	val url: String? = null
)

