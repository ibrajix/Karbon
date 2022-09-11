package com.ibrajix.feature.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ibrajix.feature.R
import com.ibrajix.feature.databinding.FragmentMoviesBinding
import com.ibrajix.feature.utils.Resource
import com.ibrajix.feature.utils.Utility.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val moviesViewModel: MoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){




        moviesAdapter = MoviesAdapter(onClickListener = MoviesAdapter.OnRelatedMoviesClickListener{ clickedMovie->

            //do something when clicked
            val bundle = Bundle()
            bundle.putParcelable("movie", clickedMovie)

            activity?.supportFragmentManager.let {
                DetailsBottomSheetFragment.newInstance(bundle).apply {
                    if (it != null) {
                        show(it, tag)
                    }
                }
            }

        })

        binding.rcvPopularMovies.adapter = moviesAdapter

        handleSwipeRefresh()
        observeData()
    }

    private fun handleSwipeRefresh(){

        binding.root.setOnRefreshListener {
            moviesViewModel.onManualRefresh()
            binding.root.isRefreshing = false
        }

    }

    private fun observeData(){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
               moviesViewModel.getMovies.collect{
                   val result = it ?: return@collect
                   binding.root.isRefreshing = result is Resource.Loading
                   moviesAdapter.submitList(result.data)
                   if (result is Resource.Success){
                       binding.loadingMovies.visibility = View.GONE
                   }
                   if (moviesViewModel.scrollToTopAfterRefresh){
                       binding.rcvPopularMovies.scrollToPosition(0)
                       moviesViewModel.scrollToTopAfterRefresh = false
                   }
               }
            }
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                moviesViewModel.event.collect{ event->
                    when(event){
                        is MoviesViewModel.Event.ShowErrorMessage -> {
                           showSnackBar(
                               message = getString(R.string.something_went_wrong)
                           )
                        }
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        moviesViewModel.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}