package rs.school.rs.android2021task6.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import rs.school.rs.android2021task6.databinding.MainFragmentBinding
import rs.school.rs.core.repository.SongRepositoryImp

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
                viewModel.foo(SongRepositoryImp(requireContext()))
            }
        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T) = binding.block()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
