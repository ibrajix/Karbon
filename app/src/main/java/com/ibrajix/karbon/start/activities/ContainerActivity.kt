package com.ibrajix.karbon.start.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ibrajix.core.utils.Utility.whiteStatusBar
import com.ibrajix.feature.data.model.MovieList
import com.ibrajix.feature.presentation.DetailsBottomSheetFragment
import com.ibrajix.karbon.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity(), DetailsBottomSheetFragment.ItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        whiteStatusBar()
    }

    override fun onItemClick(movieDetails: MovieList) {
        //do something
    }
}