package rs.school.rs.android2021task6.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.school.rs.core.repository.SongRepositoryImp
import rs.school.rs.core.utils.ParseJSON

class MainViewModel : ViewModel() {

    fun foo(repo: SongRepositoryImp) {
        viewModelScope.launch {
            val fetchSongs = repo.fetchSongs()
            Log.e("", fetchSongs.toString())
        }
    }

}
