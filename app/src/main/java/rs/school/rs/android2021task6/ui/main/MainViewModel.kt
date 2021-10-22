package rs.school.rs.android2021task6.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import rs.school.rs.core.utils.ParseJSON

class MainViewModel : ViewModel() {
    fun foo () =  Log.e("", ParseJSON().toSongList("").toString())
}
