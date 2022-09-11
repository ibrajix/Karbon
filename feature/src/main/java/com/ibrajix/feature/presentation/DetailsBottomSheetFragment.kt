package com.ibrajix.feature.presentation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ibrajix.feature.R
import com.ibrajix.feature.data.model.MovieList
import com.ibrajix.feature.databinding.FragmentDetailsBottomSheetBinding
import com.ibrajix.feature.network.EndPoints

class DetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsBottomSheetBinding?= null
    private val binding get() = _binding!!

    private var mListener: ItemClickListener? = null
    private var bottomSheet: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){

        val bundle = this.arguments
        if (bundle != null) {
           val movie = bundle.getParcelable<MovieList>("movie")

            binding.txtMovieTitle.text = movie?.title
            binding.txtMovieDetails.text = movie?.overview

            val imageUrl = EndPoints.IMAGE_BASE_URL + movie?.poster_path

            Glide.with(requireContext())
                .load(imageUrl)
                .transform(CenterInside(), RoundedCorners(24))
                .into(binding.imgMovie)

        }

        setUpViews()

    }

    private fun setUpViews(){
        handleClicks()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            mListener = context
        } else {
            throw RuntimeException(
                "$context must implement ItemClickListener"
            )
        }
    }

    interface ItemClickListener {
        fun onItemClick(movieDetails: MovieList)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsBottomSheetFragment {
            val fragment = DetailsBottomSheetFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun handleClicks(){

        binding.icClose.setOnClickListener {
            dismissAllowingStateLoss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}