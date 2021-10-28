package rs.school.rs.android2021task6.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rs.school.rs.android2021task6.databinding.MainFragmentBinding
import rs.school.rs.core.model.Song

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val binding get() = requireNotNull(_binding)

    private var _binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       views {
            playPauseSong.setOnClickListener {
                viewModel.playOrToggleSong(Song(id = 1, title = "", artist = "", bitmapUri = "", trackUri = "https://freepd.com/music/Ice and Snow.mp3"))
            }
        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T) = binding.block()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
