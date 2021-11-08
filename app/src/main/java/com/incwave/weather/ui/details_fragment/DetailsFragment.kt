package com.incwave.weather.ui.details_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.incwave.weather.core.base.BaseFragment
import com.incwave.weather.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailsViewModel>() {
    private lateinit var binding: FragmentDetailsBinding
    override val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        Log.d("QQQ", "2")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.eventsFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            when(it){
                is DetailsViewModel.Event.SetTimeAndWeatherOnUi -> {
                    binding.tvWeather.text = it.weather
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setDataFromBackend()
    }
}