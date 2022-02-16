package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.EventObserver
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.PictureOfDay

class MainFragment : Fragment() {
    private val TAG: String = javaClass.simpleName

    lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(
            this,
            MainViewModelFactory(AsteroidRadarDatabase.getInstance(requireContext()))
        )[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        // initialize observers
        viewModel.pictureOfDay.observe(viewLifecycleOwner) { setPictureOfDay(it) }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun setPictureOfDay(pictureOfDay: PictureOfDay) {
        if (pictureOfDay.mediaType == "video") {
            Log.i(TAG, "${pictureOfDay.url} is a video. Not showing picture of the day")
        } else {
            Picasso.with(requireContext())
                .load(pictureOfDay.url)
                .into(binding.activityMainImageOfTheDay)
        }
    }
}
