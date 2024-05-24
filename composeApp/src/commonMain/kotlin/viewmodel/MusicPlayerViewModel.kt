package viewmodel

import Resources
import data.DataItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.SongsRepository

class MusicPlayerViewModel : ViewModel() {
    private val _song = MutableStateFlow<DataItem?>(null)
    private val _songUrl = MutableStateFlow<String?>(null)
    val song: StateFlow<DataItem?> get() = _song
    val songUrl= _songUrl.asStateFlow()

    fun getSong() {
        viewModelScope.launch {
            SongsRepository.getSong().let { result ->
                when (result) {
                    is Resources.Empty -> println("Loading...")
                    is Resources.Failure -> println("Something went wrong ${result.exception}")
                    is Resources.Success -> {
                        _song.update { result.value.data?.get(0) }
                        _songUrl.value = result.value.data?.get(0)?.downloadUrl?.get(3)?.url
                    }
                }
            }
        }
    }

}
