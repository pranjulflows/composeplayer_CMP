sealed class Resources<out T> {
    data class Success<out T>(val value: T) : Resources<T>()
    data class Failure(val exception: String) : Resources<Nothing>()
    data object Empty : Resources<Nothing>()
}