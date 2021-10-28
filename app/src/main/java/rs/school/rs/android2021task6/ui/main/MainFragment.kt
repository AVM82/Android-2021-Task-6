package rs.school.rs.android2021task6.ui.main

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rs.school.rs.android2021task6.R
import rs.school.rs.android2021task6.databinding.MainFragmentBinding
import rs.school.rs.core.model.Song
import rs.school.rs.core.utils.Status
import rs.school.rs.exoplayer.isPlaying

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val binding get() = requireNotNull(_binding)

    private var curPlayingSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    private var _binding: MainFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainFragmentBinding.inflate(inflater).also { _binding = it }
        subscribeToObservers()
        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.mediaItems.observe(viewLifecycleOwner) {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { songs ->
//                            swipeSongAdapter.songs = songs
                            if (curPlayingSong == null && songs.isNotEmpty()) {
//                                glide.load((curPlayingSong ?: songs[0]).imageUrl)
//                                    .into(ivCurSongImage)
                                curPlayingSong = songs[0]
                                Toast.makeText(
                                    requireActivity(),
                                    "${curPlayingSong ?: "ERROR"}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
//                            switchViewPagerToCurrentSong(curPlayingSong ?: return@observe)
                        }
                    }
                    else -> Unit
                }
            }
        }
        viewModel.curPlayingSong.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            curPlayingSong = toSong(it)
//            glide.load(curPlayingSong?.imageUrl).into(ivCurSongImage)
//            switchViewPagerToCurrentSong(curPlayingSong ?: return@observe)
        }

        viewModel.playbackState.observe(viewLifecycleOwner) {
            playbackState = it
            binding.playPauseSong.setImageResource(
                if (playbackState?.let { ps -> isPlaying(ps) } == true) R.drawable.ic_pause else R.drawable.ic_play
            )
        }
        viewModel.isConnected.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.ERROR -> Toast.makeText(
                        requireActivity(),
                        result.message ?: "An unknown error occured",
                        Toast.LENGTH_LONG
                    ).show()
                    else -> Unit
                }
            }
        }
        viewModel.networkError.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.ERROR -> Toast.makeText(
                        requireActivity(),
                        result.message ?: "An unknown error occured",
                        Toast.LENGTH_LONG
                    ).show()
                    else -> Unit
                }
            }
        }
    }

    private fun toSong(it: MediaMetadataCompat): Song? =
        it.description?.let {
            Song(
                id = it.mediaId?.toInt() ?: 1,
                title = it.title.toString(),
                artist = it.subtitle.toString(),
                bitmapUri = it.mediaUri.toString(),
                trackUri = it.mediaUri.toString()
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        views {
        binding.playPauseSong.setOnClickListener {
            curPlayingSong?.let { song -> viewModel.playOrToggleSong(song, true) }
        }
//        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T) = binding.block()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
