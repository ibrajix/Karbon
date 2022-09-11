package com.ibrajix.karbon.start.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ibrajix.feature.databinding.FragmentMoviesBinding
import com.ibrajix.karbon.R
import com.ibrajix.karbon.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        handleClicks()
    }

    private fun handleClicks(){
        //pn click button
        binding.btnGo.setOnClickListener {
             findNavController().navigate(StartFragmentDirections.actionStartFragmentToFeaturesNavGraph())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}